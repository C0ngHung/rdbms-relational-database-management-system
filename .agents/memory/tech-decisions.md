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
  - **Alternative (Preferred for long formulas)**: Use Markdown inline code for variables and math mode only for arrows (e.g., `course_id` $\rightarrow$ `teacher_id`) to avoid `'_' allowed only in math mode` parse errors entirely.
- **SQL Server (T-SQL) Dialect Enforcement**:
  - Educational modules under `sql-server/` must strictly use T-SQL syntax.
  - E.g., `IDENTITY(1,1)` instead of `AUTO_INCREMENT`, `BIT` instead of `BOOLEAN`, `DATETIME2(7)` for timestamps, and Computed Columns (`AS (...)`).
- **Pragmatic Database Design Principles**:
  - JSON Snapshots: Storing frozen JSON payloads (e.g., `shipping_snapshot JSONB/NVARCHAR(MAX)`) is a deliberate and valid exception to 1NF for immutable historical records.
  - Denormalization: Intentionally keeping attributes like `city` and `country` in the same table for small applications is accepted to avoid over-engineering and excessive `JOIN` operations (pragmatic vs purist 3NF).
