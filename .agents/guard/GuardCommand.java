/**
 * PreToolUse guard: blocks or asks before dangerous agent actions.
 *
 * Single-file Java program. `java guard/GuardCommand.java` is one command string
 * that behaves identically under `sh -c` (Linux/macOS) and `cmd /c` (Windows),
 * which no python/python3 spelling can promise - this repo's Ubuntu box has only
 * `python3` and a stock Windows install has only `python`. A JDK is required
 * because the single-file launcher compiles on the fly; every machine that can
 * build this project already has one (AGENTS.md section 7: no mvnw, system mvn).
 *
 * Lives in .agents/guard/ rather than .agents/scripts/ so that AG Kit, which owns
 * .agents/ through a checksum manifest, never treats it as a stray file.
 *
 * Reads a hook payload on stdin, writes a verdict on stdout. Two contracts:
 *
 *   Antigravity CLI   .agents/hooks.json
 *     in : {"toolCall":{"name":"run_command","args":{"CommandLine":"..."}}}
 *     out: {"decision":"deny"|"ask","reason":"..."}
 *     `decision` is REQUIRED here. Emitting {} to mean "no opinion" makes
 *     Antigravity deny the call - that mistake bricked the CLI once, denying
 *     every tool the matcher covered. When no rule fires we return "ask",
 *     which is Antigravity's own default path and respects its Always Allow
 *     cache, so routine commands behave as if the hook were absent.
 *
 *   Claude Code       .claude/settings.json
 *     in : {"tool_name":"Bash","tool_input":{"command":"..."}}
 *     out: {"hookSpecificOutput":{"hookEventName":"PreToolUse",
 *           "permissionDecision":"deny"|"ask","permissionDecisionReason":"..."}}
 *     Here {} genuinely means "no opinion" and is the correct silent path
 *     (verified: mvn/git status run unprompted with this guard installed).
 *
 * Always exits 0 - the verdict travels in the JSON, never in the exit code, so a
 * crash can never hard-block the agent. Messages are ASCII-only so the Windows
 * console codepage cannot mangle them.
 *
 * Project-specific protected paths live in .agents/protected-paths.txt.
 */
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuardCommand {

    private static final String DENY = "deny";
    private static final String ASK = "ask";

    private record Rule(Pattern pattern, String decision, String reason) {}

    /**
     * Anchors a rule to a command position: start of string, or right after a
     * separator (; && || |) or a $( substitution. Without this anchor the guard
     * matches its own argument text - echo "git push --force", a grep over
     * docs, or a test fixture containing the phrase all get blocked.
     *
     * Backtick substitution is deliberately NOT an anchor. It is legacy shell
     * syntax, while a backtick in a command far more often opens Markdown
     * inline code - and this repo writes teaching docs full of it. Treating it
     * as a command position blocked an ordinary documentation edit whose text
     * merely quoted a git command. $( ) still covers command substitution.
     *
     * Anchoring also means `bash -c "git push --force"` slips through. This is
     * a guardrail against accidents, not a sandbox, and AGENTS.md section 8
     * remains the primary contract.
     */
    private static final String CMD_START = "(?:^|[;&|]+|\\$\\()\\s*(?:sudo\\s+)?";

    /** Rule matched against a shell command string, anchored to a command position. */
    private static Rule rule(String regex, String decision, String reason) {
        return new Rule(Pattern.compile(CMD_START + regex, Pattern.CASE_INSENSITIVE), decision, reason);
    }

    /** Rule matched against a tool name, which is a bare identifier - no anchor. */
    private static Rule toolRule(String regex, String decision, String reason) {
        return new Rule(Pattern.compile(regex, Pattern.CASE_INSENSITIVE), decision, reason);
    }

    /**
     * Shell command rules. First match wins, so deny rules are listed first.
     *
     * The DROP rules are deliberately keyed off a database client rather than
     * matching "drop table" anywhere: an unkeyed rule denies ordinary work like
     * `grep -r "DROP TABLE" .` over migration files.
     */
    private static final List<Rule> COMMAND_RULES = List.of(
            rule("git\\s+push\\b[^;&|]*(--force\\b|(?<![\\w-])-f(?![\\w-]))", DENY,
                    "git push --force is forbidden (AGENTS.md section 8)"),
            rule("git\\s+clean\\b[^;&|]*-[a-z]*f", DENY,
                    "git clean -f deletes uncommitted files (AGENTS.md section 8)"),
            rule("(mongosh|mongo|mysql|psql|sqlite3|sqlcmd)\\b[^;&|]*\\bdrop\\s+(database|schema|table)\\b", DENY,
                    "dropping a database/table is forbidden (AGENTS.md section 8)"),
            rule("(mongosh|mongo)\\b[^;&|]*\\bdropDatabase\\s*\\(", DENY,
                    "dropping a database is forbidden (AGENTS.md section 8)"),
            rule("docker\\s+(compose\\s+)?down\\b[^;&|]*(--volumes\\b|(?<![\\w-])-v(?![\\w-]))", DENY,
                    "docker compose down -v wipes data volumes (AGENTS.md section 8: no DB reset)"),
            rule("git\\s+commit\\b", ASK,
                    "AGENTS.md section 8: never commit unless explicitly asked"),
            rule("git\\s+push\\b", ASK,
                    "AGENTS.md section 8: never push unless explicitly asked"),
            rule("git\\s+reset\\s+--hard\\b", ASK,
                    "git reset --hard discards uncommitted work"),
            rule("git\\s+rebase\\b", ASK,
                    "git rebase rewrites history"),
            rule("git\\s+(checkout|switch)\\s+(main|master)\\b", ASK,
                    "AGENTS.md section 8: work on a feature branch, never directly on main"));

    /**
     * Antigravity step types that are dangerous by name alone, so no argument
     * parsing is needed. git_commit matters most: Antigravity can commit through
     * a dedicated step that never passes through run_command, so a guard that
     * only inspects shell strings would miss it entirely.
     */
    private static final List<Rule> TOOL_NAME_RULES = List.of(
            toolRule("^git_commit$", ASK,
                    "AGENTS.md section 8: never commit unless explicitly asked"),
            toolRule("^delete_directory$", ASK,
                    "deleting a directory is not reversible from the agent loop"),
            toolRule("^cloud_sql_update_schema$", DENY,
                    "schema changes must be reviewed by a human (AGENTS.md section 8)"));

    private static final Pattern FILE_WRITING_TOOLS =
            Pattern.compile("^(propose_code|file_change|edit_notebook|write_to_file|create_file|move)$");

    /**
     * Keys that hold a file path across the tool schemas we know of. Only these
     * values are checked - scanning the whole payload would deny an edit merely
     * because the new code text happens to mention a protected path.
     */
    private static final List<String> PATH_KEYS = List.of(
            "TargetFile", "AbsolutePath", "SourcePath", "DestinationPath", "Uri",
            "file_path", "path", "Path");

    private static final List<String> DEFAULT_PROTECTED_PATHS =
            List.of("/target/", "/build/", "initdb.sql", "spotbugs-exclude.xml");

    public static void main(String[] args) {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.US_ASCII);
        String payload;
        try {
            payload = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            out.print("{}");   // never block the agent because the guard itself failed
            return;
        }

        boolean antigravity = payload.contains("\"toolCall\"");
        String toolName = antigravity ? extractString(payload, "name")
                                      : extractString(payload, "tool_name");
        String command = antigravity ? extractString(payload, "CommandLine")
                                     : extractString(payload, "command");

        Rule hit = classify(toolName, command, payload);
        if (hit != null) {
            out.print(render(hit, antigravity));
        } else if (antigravity) {
            // "ask" is the no-opinion path here, not {} - see the class comment.
            out.print("{\"decision\":\"ask\"}");
        } else {
            out.print("{}");
        }
    }

    private static Rule classify(String toolName, String command, String payload) {
        String name = toolName == null ? "" : toolName.toLowerCase(Locale.ROOT);

        for (Rule r : TOOL_NAME_RULES) {
            if (r.pattern().matcher(name).find()) {
                return r;
            }
        }
        if (FILE_WRITING_TOOLS.matcher(name).matches()) {
            Rule protectedHit = checkProtectedPaths(payload);
            if (protectedHit != null) {
                return protectedHit;
            }
        }
        if (command == null || command.isBlank()) {
            return null;
        }
        if (isRmRf(command)) {
            return new Rule(null, DENY, "rm -rf is forbidden (AGENTS.md section 8)");
        }
        for (Rule r : COMMAND_RULES) {
            if (r.pattern().matcher(command).find()) {
                return r;
            }
        }
        return null;
    }

    /**
     * ASK rather than DENY: the path-key list is a best effort against tool
     * schemas that are not publicly documented, so a miss must stay recoverable.
     * Claude Code gets an exact DENY through permissions.deny instead.
     */
    private static Rule checkProtectedPaths(String payload) {
        List<String> protectedPaths = protectedPaths();
        for (String key : PATH_KEYS) {
            String value = extractString(payload, key);
            if (value == null || value.isBlank()) {
                continue;
            }
            String normalised = value.replace('\\', '/').toLowerCase(Locale.ROOT);
            for (String candidate : protectedPaths) {
                String needle = candidate.replace('\\', '/').toLowerCase(Locale.ROOT);
                if (normalised.contains(needle)) {
                    return new Rule(null, ASK,
                            "AGENTS.md section 8 protects " + candidate + " - confirm before writing");
                }
            }
        }
        return null;
    }

    /** True when an `rm` invocation carries both a recursive and a force flag. */
    private static boolean isRmRf(String command) {
        Matcher m = Pattern.compile(CMD_START + "rm\\s+((?:-\\S+\\s+)*)").matcher(command);
        while (m.find()) {
            String flags = m.group(1);
            boolean recursive = flags.contains("--recursive") || flags.matches("(?s).*-[a-zA-Z]*[rR].*");
            boolean force = flags.contains("--force") || flags.matches("(?s).*-[a-zA-Z]*f.*");
            if (recursive && force) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads protected-paths.txt from the hook working directory (.agents/ for
     * Antigravity) or from the repo root (Claude Code); else uses the defaults.
     */
    private static List<String> protectedPaths() {
        for (Path candidate : List.of(Path.of("protected-paths.txt"),
                                      Path.of(".agents", "protected-paths.txt"))) {
            if (!Files.isReadable(candidate)) {
                continue;
            }
            try {
                List<String> entries = new ArrayList<>();
                for (String line : Files.readAllLines(candidate, StandardCharsets.UTF_8)) {
                    String trimmed = line.trim();
                    if (!trimmed.isEmpty() && !trimmed.startsWith("#")) {
                        entries.add(trimmed);
                    }
                }
                if (!entries.isEmpty()) {
                    return entries;
                }
            } catch (IOException ignored) {
                // fall through to defaults
            }
        }
        return DEFAULT_PROTECTED_PATHS;
    }

    private static String render(Rule hit, boolean antigravity) {
        String reason = hit.reason().replace("\\", "\\\\").replace("\"", "\\\"");
        if (antigravity) {
            return "{\"decision\":\"" + hit.decision() + "\",\"reason\":\"" + reason + "\"}";
        }
        return "{\"hookSpecificOutput\":{\"hookEventName\":\"PreToolUse\","
                + "\"permissionDecision\":\"" + hit.decision() + "\","
                + "\"permissionDecisionReason\":\"" + reason + "\"}}";
    }

    /** Minimal JSON string lookup - avoids pulling a JSON library into a single-file script. */
    private static String extractString(String json, String key) {
        Matcher m = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"").matcher(json);
        if (!m.find()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = m.end(); i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"') {
                break;
            }
            if (c == '\\' && i + 1 < json.length()) {
                char next = json.charAt(++i);
                switch (next) {
                    case 'n' -> sb.append('\n');
                    case 't' -> sb.append('\t');
                    case 'r' -> sb.append('\r');
                    case 'u' -> {
                        if (i + 4 < json.length()) {
                            sb.append((char) Integer.parseInt(json.substring(i + 1, i + 5), 16));
                            i += 4;
                        }
                    }
                    default -> sb.append(next);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
