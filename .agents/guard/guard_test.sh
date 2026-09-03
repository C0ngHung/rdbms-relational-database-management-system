#!/usr/bin/env bash
# Bộ test cho .agents/guard/GuardCommand.java
# Chạy: bash <file này>   (từ gốc repo)
G=".agents/guard/GuardCommand.java"
fail=0
chk() {
  out=$(printf '%s' "$2" | java "$G" 2>&1)
  if printf '%s' "$out" | grep -qE "$3"; then
    printf '  [PASS] %s\n' "$1"
  else
    printf '  [FAIL] %-38s got: %s\n' "$1" "$out"
    fail=1
  fi
}

echo "### Phai DENY"
chk "AG recursive-force delete" '{"toolCall":{"name":"run_command","args":{"CommandLine":"rm -rf build"}}}' '"decision":"deny"'
chk "AG force-push"             '{"toolCall":{"name":"run_command","args":{"CommandLine":"git push --force origin x"}}}' '"decision":"deny"'
chk "AG force-push short flag"  '{"toolCall":{"name":"run_command","args":{"CommandLine":"git push -f origin x"}}}' '"decision":"deny"'
chk "AG psql drop"              '{"toolCall":{"name":"run_command","args":{"CommandLine":"psql -c \"DROP DATABASE app\""}}}' '"decision":"deny"'
chk "AG mongosh drop"           '{"toolCall":{"name":"run_command","args":{"CommandLine":"mongosh --eval \"db.dropDatabase()\""}}}' '"decision":"deny"'
chk "AG compose down volumes"   '{"toolCall":{"name":"run_command","args":{"CommandLine":"docker compose down -v"}}}' '"decision":"deny"'
chk "CC recursive-force delete" '{"tool_name":"Bash","tool_input":{"command":"rm -fr /tmp/x"}}' '"permissionDecision":"deny"'
chk "chained after &&"          '{"tool_name":"Bash","tool_input":{"command":"cd /repo && rm -rf dist"}}' '"permissionDecision":"deny"'

echo "### Phai ASK"
chk "AG git_commit step type"   '{"toolCall":{"name":"git_commit","args":{}}}' '"decision":"ask"'
chk "AG delete_directory"       '{"toolCall":{"name":"delete_directory","args":{}}}' '"decision":"ask"'
chk "AG write protected file"   '{"toolCall":{"name":"propose_code","args":{"TargetFile":"init.sql"}}}' 'protects init.sql'
chk "AG write into migrations dir" '{"toolCall":{"name":"write_to_file","args":{"AbsolutePath":"/repo/migrations/001.sql"}}}' 'protects /migrations/'
chk "CC commit"                 '{"tool_name":"Bash","tool_input":{"command":"git commit -m wip"}}' '"permissionDecision":"ask"'
chk "CC checkout default branch" '{"tool_name":"Bash","tool_input":{"command":"git checkout main"}}' '"permissionDecision":"ask"'

# Antigravity requires a `decision` field on EVERY response. Returning {} makes
# it deny the call - that bug denied every matched tool until it was found.
# The no-opinion answer is a bare "ask", which respects its Always Allow cache.
echo "### Antigravity: khong dinh luat nao -> phai la ask tran, KHONG duoc la {}"
chk "AG harmless command"       '{"toolCall":{"name":"run_command","args":{"CommandLine":"javac -version"}}}' '^\{"decision":"ask"\}$'
chk "AG maven verify"           '{"toolCall":{"name":"run_command","args":{"CommandLine":"mvn clean verify -B"}}}' '^\{"decision":"ask"\}$'
chk "AG never emits bare {}"    '{"toolCall":{"name":"run_command","args":{"CommandLine":"ls -la"}}}' '"decision":'

echo "### Claude Code: khong dinh luat nao -> {} moi dung (da verify chay that)"
chk "maven verify"              '{"tool_name":"Bash","tool_input":{"command":"mvn clean verify -B"}}' '^\{\}$'
chk "git status"                '{"tool_name":"Bash","tool_input":{"command":"git status"}}' '^\{\}$'
chk "delete one file"           '{"tool_name":"Bash","tool_input":{"command":"rm target/foo.txt"}}' '^\{\}$'
chk "delete one file forced"    '{"tool_name":"Bash","tool_input":{"command":"rm -f /tmp/a.log"}}' '^\{\}$'
chk "grep over migrations"      '{"tool_name":"Bash","tool_input":{"command":"grep -rn \"DROP TABLE\" ."}}' '^\{\}$'
# Backtick mo Markdown inline code nhieu hon la command substitution, va repo nay
# viet tai lieu day nhung chuoi nhu do. Backtick KHONG phai vi tri dau lenh;
# $( ) thi van la. Bug that: mot lan sua tai lieu bi chan vi trich mot lenh git.
chk "markdown inline code in text"  '{"tool_name":"Bash","tool_input":{"command":"echo \"chay `git clean -fd` de reset\""}}'  '^\{\}$'
chk "markdown inline code, rm"      '{"tool_name":"Bash","tool_input":{"command":"echo \"dung `rm -rf build` nhe\""}}'  '^\{\}$'
# Nhung command substitution that su thi VAN phai chan.
chk "real command substitution"     '{"tool_name":"Bash","tool_input":{"command":"echo $(rm -rf /tmp/x)"}}'  '"permissionDecision":"deny"'
chk "echo a dangerous phrase"   '{"tool_name":"Bash","tool_input":{"command":"echo \"git push --force is banned\""}}' '^\{\}$'
chk "branch name contains -f"   '{"tool_name":"Bash","tool_input":{"command":"git log origin/my-f-branch"}}' '^\{\}$'
chk "compose down without -v"   '{"tool_name":"Bash","tool_input":{"command":"docker compose down"}}' '^\{\}$'
chk "malformed payload"         'not json at all' '^\{\}$'
chk "empty payload"             '{}' '^\{\}$'

echo
if [ $fail -eq 0 ]; then echo "KET QUA: TAT CA PASS"; else echo "KET QUA: CO CA FAIL"; exit 1; fi
