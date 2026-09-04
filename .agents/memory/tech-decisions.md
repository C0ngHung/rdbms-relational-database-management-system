---
type: project
created: 2026-07-18
updated: 2026-09-04
---

# Technical Decisions

- Component metadata uses SemVer while the toolkit release keeps CalVer.
- `manifest.json` and `manifest.lock.json` must remain synchronized with component frontmatter.
- **Course Documentation Separation**:
  - Keep Section §3 (Theory) focused strictly on relational algebra / functional dependency definitions without premature SQL.
  - Defer all concrete DDL/DQL refactoring scripts to Section §4+ (Normal Forms) to avoid duplicate explanations.
- **Pedagogical Asset Placement**:
  - Image assets must be co-located with their exact conceptual section (e.g., MVD Principle 4 placed under §4.5 4NF rather than §3).
  - Use GitHub alert `[!NOTE]` forward-references to link theoretical problems with their implementation solutions.
- **KaTeX / Math Block Attribute Notation**:
  - Always use PascalCase identifiers (e.g., `\text{StudentID} \xrightarrow{} \text{AvgScore}`) inside KaTeX math blocks `$$...$$` rather than snake_case (`student_id`).
  - Rationale: Markdown parsers consume backslashes (`\_` → `_`) before passing strings to KaTeX, which then throws `'_' allowed only in math mode` because text mode forbids unescaped underscores.

