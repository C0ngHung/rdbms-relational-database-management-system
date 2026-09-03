---
name: git-commit-msg
version: 1.0.0
priority: P1
trigger: model_decision
description: "Apply when writing a git commit message or running git commit/push"
---

# Git Commit Message Convention

> Adapted from [Karma Runner Git Commit Msg](https://karma-runner.github.io/6.4/dev/git-commit-msg.html) and [Angular Commit Message Format](https://github.com/angular/angular/blob/master/CONTRIBUTING.md#commit), customized to this project's **bracket layout**.

---

## Why This Convention?

- **Simple navigation** through git history by topic/chapter
- **Semantic versioning** — commit type determines what changed
- **Ticket traceability** — each `RDBMS-NNN` maps to a learning chapter/topic

---

## Canonical Format (THIS PROJECT)

```
<type>[<id-ticket>]:[<scope>]:<message>

<body>

<footer>
```

**Real examples for this project:**

```
feat[RDBMS-001]:[schema]:add initial university ERD with student and course tables
docs[RDBMS-002]:[index]:explain B-tree vs Hash index trade-offs
fix[RDBMS-003]:[query]:correct JOIN condition in enrollment count query
refactor[RDBMS-004]:[schema]:normalize address into separate table (3NF)
```

> [!IMPORTANT]
> - The **header** is mandatory. Keep it ≤ **100 characters**.
> - `<type>`, `<id-ticket>`, `<scope>` and `<message>` are all **lowercase**.
> - Ticket `RDBMS-NNN` = learning chapter/topic number (3 digits, start from 001).

---

## 1. `<type>` (Required)

| Type | Description | When |
|---|---|---|
| `feat` | New schema, new query, new exercise | Adding content |
| `fix` | Fixing wrong SQL, incorrect query result | Bug fix |
| `docs` | Explanation, notes, comments in SQL files | Documentation |
| `style` | Formatting, indentation (no logic change) | Style only |
| `refactor` | Restructuring SQL without changing behavior | Refactoring |
| `test` | Adding test queries, verification scripts | Testing |
| `build` | Changes to config, tooling | Infrastructure |

## 2. `[<id-ticket>]` (Required)

Ticket id in square brackets. Format: `RDBMS-NNN` where NNN = 3-digit sequential number.
- `RDBMS-001` = chapter 1 / first topic
- `RDBMS-002` = chapter 2 / second topic
- If no specific chapter: `RDBMS-000` (project setup, general)

## 3. `[<scope>]` (Required)

Area of the codebase in square brackets, **lowercase**:

| Scope | When |
|---|---|
| `schema` | CREATE TABLE, ALTER TABLE, constraints |
| `index` | CREATE INDEX, query optimization |
| `query` | SELECT queries, DQL scripts |
| `migration` | Migration scripts (future) |
| `constraint` | FK, UNIQUE, CHECK, NOT NULL |
| `exercise` | Practice exercises |
| `docs` | README, notes, explanations |
| `config` | Project configuration |

## 4. `<message>` (Required)

- **Imperative, present tense**: "add" not "added"
- **Lowercase** first letter
- **No period** at the end
- Keep header ≤ 100 chars total

---

## 5. Body (Optional)

Use for non-obvious changes: explain WHY, contrast with previous behavior.

```
refactor[RDBMS-004]:[schema]:normalize address into separate table

Address was stored as flat columns in the student table (student_city,
student_street, student_zip), violating 3NF because city→state dependency
existed. Extract to address table with FK, enabling reuse and eliminating
update anomalies.
```

---

## 6. Quick Checklist

- [ ] Layout: `<type>[RDBMS-NNN]:[<scope>]:<message>`
- [ ] Type is one of: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `build`
- [ ] Ticket is `RDBMS-NNN` format
- [ ] Scope is one of the defined scopes above
- [ ] Message uses imperative mood, no trailing period
- [ ] Header ≤ 100 characters

---

## 7. Antigravity CLI Execution Template (Delegated Git Commands)

```bash
mkdir -p .antigravitycli/logs
{
  echo "=== Git Commit & Push Start: $(date) ==="

  git add <path/to/files>
  git commit -m "<type>[RDBMS-NNN]:[<scope>]:<message>"
  git push origin <branch-name>

  echo "=== Git Commit & Push Done: $(date) ==="
} > .antigravitycli/logs/git_push.log 2>&1

if [ $? -eq 0 ]; then
  echo '{"status": "success"}' > .antigravitycli/logs/git_push_result.json && echo "Git push thành công."
else
  echo '{"status": "error"}' > .antigravitycli/logs/git_push_result.json && echo "Git push thất bại. Xem log tại .antigravitycli/logs/git_push.log"
fi
```
