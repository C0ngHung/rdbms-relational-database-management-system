# GitHub Copilot Instructions

See [AGENTS.md](../AGENTS.md) for all project rules, constraints, and conventions.
This is the single source of truth for AI behavior in this project.

Key rules summary:
- Reply in Vietnamese; SQL/code in English
- Ticket prefix: `RDBMS-NNN` for all commits
- Never DELETE/UPDATE without WHERE clause
- Never DROP TABLE without rollback plan
- Never edit applied migration files — only add new ones
- See AGENTS.md §8 for complete list of forbidden actions
