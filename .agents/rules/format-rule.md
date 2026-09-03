---
name: format-rule
version: 1.0.0
priority: P1
trigger: manual
---

# Format Lesson Rule — Database Course

This rule defines the standard formatting and structure for all database course lesson documents (Markdown files) to ensure consistency, readability, and professional presentation.

## 1. File Structure & Headers

- **Main Title**: Every file must start with exactly one `h1` header.
  - Format: `# [Lesson Title] - [English Title]`
  - Example: `# Part 1: Các Khái Niệm Cơ Bản - Basic Concepts`
- **Main Sections**: Use `h2` headers with numerical prefixes.
  - Format: `## N. Section Name`
  - Example: `## 1. SQL Server là gì?`
- **Subsections**: Use `h3` headers. Numbered prefixes are recommended for clear hierarchy.
  - Format: `### N.M Sub-section Name`
- **Spacing**: Ensure one empty line between headers and the following content.

## 2. Text Formatting & Terminology

- **Key Terms**: Highlight critical technical terms using bold text (`**...**`).
- **English Translations**: Always include the English equivalent for technical terms in bold parentheses.
  - Format: `**Vietnamese Term** (**English Term**)`
  - Example: `**hệ quản trị cơ sở dữ liệu** (**relational database management system**)`
- **In-line Code**: Use backticks (`` `...` ``) for keywords, table names, data types, and functions.
  - Example: `SELECT`, `NVARCHAR`, `dbo`.

## 3. Lists & Bullet Points

- **Style**: Use hyphen `-` for bullet points. Avoid using `.` or `*`.
- **Indentation**: Use 2 or 4 spaces consistently for nested lists.
- **Bold Start**: If a list item defines a term, start with the term in bold.
  - Example: `- **Trường dữ liệu**: Mô tả thuộc tính...`

## 4. Interview Questions & Answers

This section is critical for learner preparation. Follow this exact pattern:

- **Section Header**: Use `## N. Câu hỏi phỏng vấn [Topic]` followed by `### N.1 Câu hỏi lý thuyết`, `### N.2 Câu hỏi thực tế / tình huống`.
- **Question Format**:
  - Format: `**Câu X:** [Question Content]`
  - Example: `**Câu 1:** Phân biệt VARCHAR và NVARCHAR trong SQL Server?`
- **Answer Format**:
  - Format: `**Trả lời:**` on its own line, followed by the answer content.
- **Answer Content**: Use lists, bolding, and code blocks within answers for clarity.

## 5. Code Blocks

- **Language Identifier**: Always specify `sql` (or `tsql`).
- **Best Practices**:
  - Use uppercase for SQL keywords (`SELECT`, `FROM`, `WHERE`, `CREATE TABLE`).
  - Include brief inline comments explaining **WHY** (tại sao thiết kế như vậy).
  - Wrap the block with single newlines before and after.
- **Results**: When showing query outputs, use a bolded **Kết quả:** or **Output:** followed by a markdown table or code block.

## 6. Alerts & Callouts

Use GitHub-style alerts for critical notes:

- `> [!NOTE]` for helpful background information.
- `> [!TIP]` for performance tips and best practices.
- `> [!IMPORTANT]` for mandatory rules or critical invariants.
- `> [!WARNING]` for common pitfalls, data corruption, or locking risks.
