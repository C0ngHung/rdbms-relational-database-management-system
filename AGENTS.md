# AGENTS.md — Core Operating Rules (always on)

Concise, always-on essentials consolidated from `00-ai-operating`, `share-rule`, `software-develop`.
Full detailed versions are available on demand: `@00-ai-operating-rules`, `@software-develop`, `@share-rule`.
SQL/DB rules load on demand — see `.agents/rules/`.

---

## 1. Language & Output

- Reply in **Vietnamese**; code, SQL identifiers, and file names in **English**.
  Code comments: inline teaching/explanation comments may be in Vietnamese
  (learning project — giải thích bằng tiếng Việt giúp nhớ lâu hơn).
  Commit messages and PR descriptions in English.
- For **non-trivial** changes include: full file path, what changed & why, why it is safe, remaining risks, how to verify.
  Pure "code only" applies when the user explicitly asks for just code.
- Complete, working SQL — no placeholders. TODOs only with a reference: `-- TODO(RDBMS-NNN): ...`.
- Ask for clarification when requirements are ambiguous; state assumptions explicitly at the top.

## 2. Core Behavior & Safety

- Do NOT invent schema, table names, column names, or index names not already in the project.
- Prefer minimal safe changes over rewrites. Do NOT alter existing schema contracts unless asked.
- Do NOT add new tables/columns/indexes without justifying why.
- Never: `DELETE`/`UPDATE` without `WHERE` · `DROP TABLE` without a rollback plan · swallow constraint violations silently · expose connection strings or credentials · bypass schema validation · use `FLOAT`/`REAL` for monetary values (use `NUMERIC`/`DECIMAL`).

## 3. Before Writing SQL

Identify: affected table(s) · operation type (DDL/DML/DQL) · transaction boundary (auto-commit vs explicit `BEGIN`) · constraint impact (FK, UNIQUE, NOT NULL, CHECK) · index impact (will this query hit an index?) · rollback plan for DDL.

For DML (INSERT/UPDATE/DELETE) additionally:
- **Idempotency**: is there a `ON CONFLICT DO NOTHING` or unique guard?
- **Affected rows**: `SELECT COUNT(*)` to preview before running DML
- **Rollback**: can this be undone? If DDL — write the down-migration script first.

## 4. Assumptions & Refactoring

- If context is missing, ask for the minimum files/schema needed before writing SQL.
- Do NOT silently assume data types, character sets, collations, or business rules.
- Prefer small incremental schema changes; do NOT alter multiple unrelated tables at once.
- No abstraction before 2 real use cases. Explain rollback strategy for any DDL.

## 5. Development Philosophy

Primary job: preserve **data integrity**, isolate **schema change**, prevent **data loss**, enable **future evolution at low cost**.

**Trade-off analysis (mandatory for significant decisions):** Why this? · Why not the alternative? · What do I gain? · What do I lose? · When does it break?

- **Normalization vs. denormalization**: understand the trade-off before choosing either.
- **Model data, not flags**: `status BOOLEAN` often hides a missing concept (use an enum/lookup table).
- **Enforce constraints in the DB**: NOT NULL, FK, CHECK, UNIQUE are your first line of defense — not just application code.
- **Index with evidence**: add an index only when you have an EXPLAIN ANALYZE showing a Seq Scan on a large table.
- **Migrations are append-only**: never edit an already-applied migration file.

## 6. Verification (non-negotiable)

- Verify DDL: run the script in a transaction, check result, then COMMIT (or ROLLBACK if unexpected).
- Verify DML: `SELECT ... WHERE <same condition>` trước rồi mới chạy `UPDATE`/`DELETE`.
- Verify query performance: `EXPLAIN ANALYZE <query>` sau khi thêm index mới.
- Preserve existing queries — nếu thay đổi schema làm query cũ vỡ, phải fix query kèm theo.
- Write down-migration (rollback script) cho mọi DDL change.

---

## Rule Map (load on demand)

| Area | Rule | Trigger |
|---|---|---|
| General AI behavior | `00-ai-operating-rules` | manual (`@00-ai-operating-rules`) |
| Software dev principles | `software-develop` | manual (`@software-develop`) |
| Memory bank | `04-memory-bank-documentation` | model_decision |
| Git commit | `git-commit-msg` | model_decision (khi viết commit) |
| Output format | `share-rule` | manual (`@share-rule`) |
| Lesson formatting | `format-rule` | manual (`@format-rule`) |

---

## 7. Toolchain & Structure

**Toolchain:** PostgreSQL 16 · psql CLI · DBeaver · `.sql` files

**Không có build tool** — SQL chạy trực tiếp qua psql hoặc DBeaver.

```bash
# Chạy 1 file SQL
psql -U <user> -d <database> -f <file.sql>

# Kiểm tra query plan
EXPLAIN ANALYZE <query>;

# Kiểm tra schema hiện tại
\d <table_name>
\dt        -- liệt kê tất cả tables
\di        -- liệt kê tất cả indexes
```

**Cấu trúc thư mục** (dự kiến, điều chỉnh khi project phát triển):

| Thư mục | Vai trò |
|---|---|
| `schemas/` | DDL: CREATE TABLE, ALTER TABLE, CREATE INDEX |
| `queries/` | DQL: SELECT queries theo chủ đề |
| `exercises/` | Bài tập thực hành |
| `migrations/` | Migration scripts (tương lai) |

**Ticket prefix cho commit:** `RDBMS-NNN` (3 chữ số, bắt đầu từ 001)
Mỗi ticket = 1 chủ đề học (RDBMS-001 = normalization, RDBMS-002 = indexes, ...).

## 8. Cấm (hard rules)

- Không sửa: `init.sql`, `seed.sql`, `/migrations/` — trừ khi tôi yêu cầu rõ.
- Không hardcode credentials / connection string / password trong file bất kỳ.
- Không chạy lệnh phá huỷ: `rm -rf`, `git push --force`, `git reset --hard`.
- Không `DROP TABLE` / `TRUNCATE` mà không có rollback script và backup plan rõ ràng.
- Không `DELETE`/`UPDATE` mà không có `WHERE` clause.
- Không commit / push khi tôi chưa yêu cầu. Không làm việc trực tiếp trên `main` — luôn feature branch.
- Không sửa migration file đã apply — chỉ thêm migration mới.

## 9. Bản đồ file theo AI tool

| Tool | File nó **thực sự** đọc |
|---|---|
| Antigravity IDE | `AGENTS.md` · `.agents/rules/` · `.agents/workflows/` · `.agents/hooks.json` · global `~/.gemini/GEMINI.md` |
| Antigravity CLI (`agy`) | `AGENTS.md` · `.agents/rules/` · `.agents/skills/` · `.agents/hooks.json` · global `~/.gemini/antigravity-cli/settings.json` |
| Claude Code | `CLAUDE.md` (import `@AGENTS.md`) · `.claude/rules/` · `.claude/commands/` · `.claude/settings.json` |
| GitHub Copilot | `.github/copilot-instructions.md` (import `@AGENTS.md`) |

**File này là nguồn duy nhất.** Các wrapper (`CLAUDE.md`, `.github/copilot-instructions.md`) chỉ trỏ về đây.
Rule chi tiết viết ở `.agents/rules/`; slash command viết ở `.agents/workflows/`;
`.claude/commands/*.md` là wrapper 5-dòng; `.agents/skills/*/SKILL.md` là bridge cho Antigravity CLI.
**Không sửa wrapper — sửa file nguồn ở `.agents/workflows/`.**

**Cưỡng chế §8.** Bảng cấm ở trên là văn xuôi — agent đọc rồi cố tuân theo.
Phần chặn thật nằm ở `.agents/guard/GuardCommand.java`, gọi qua `.agents/hooks.json` (Antigravity) và `.claude/settings.json` (Claude Code). Đường dẫn cấm khai ở `.agents/protected-paths.txt`.

Chỉ được tồn tại **một** customization root. Antigravity quét `.agents/` **hoặc** `.agent/`, `_agents/`, `_agent/` — nếu có hơn một thì rule và slash command sẽ bị nạp trùng.
