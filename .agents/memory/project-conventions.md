---
type: project
created: 2026-05-25
updated: 2026-09-04
---

# Project Conventions

## Git Workflow
- Commit Message Convention: `<type>[RDBMS-NNN]:[<scope>]:<message>` where scopes include `schema`, `index`, `query`, `migration`, `constraint`, `exercise`, `docs`, `config`.
- Separate commits per file/feature when managing multifaceted educational updates.
- Branch format: `feature/[task-slug]` or direct commits to `main` when explicitly requested by user.

## Educational Assets & Media
- Image Naming:
  - Principles: `Principle X.png` (e.g. `Principle 1.png`, `Principle 3.5.png`)
  - Case Examples: `Example X.png` (e.g. `Example 1.png`, `Example 2.png`)
  - Domain Concepts: Descriptive names (e.g. `CSDL Good.png`, `Insert Anomaly.png`, `Normalization.png`)
- Relative Markdown linking with percent-encoding for spaces: `./Example%201.png`.
- Image Captions: Every educational diagram in markdown must have an italicized caption directly beneath it formatted as `*Picture N: Title/Concept — Concise pedagogical explanation of what is shown and why it matters.*` in English.

## Supported AI platforms (AG Kit)
- AG Kit **only supports Gemini CLI and Google Antigravity**.
- Do not claim compatibility with Claude Code, Cursor, Copilot, Windsurf, or other assistants unless the user explicitly expands scope.
- Copy on the website, docs, FAQ, README, and marketing should describe AG Kit as a toolkit for Gemini CLI / Antigravity-style agent setups.

