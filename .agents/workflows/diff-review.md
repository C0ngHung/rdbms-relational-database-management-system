---
name: diff-review
version: 1.0.0
description: Review SQL diff, phân loại issue theo mức nghiêm trọng
requires_agents:
  - security-auditor
requires_skills:
  - diff-review
  - code-review-checklist
artifact_outputs:
  - type: inline
    description: Code review with Blocker/Should fix/Nit classification
argument-hint: "[file hoặc diff cần review]"
---

# Diff Review: $ARGUMENTS

Review SQL/schema changes và phân loại từng issue theo mức độ nghiêm trọng.

## QUY TẮC REVIEW

- Chỉ nêu vấn đề thật, không bới lông tìm vết về style.
- Tập trung vào: data integrity, correctness, performance, security.

## CHECKLIST SQL-SPECIFIC

Kiểm tra từng mục dưới đây trước khi đưa ra nhận xét:

**Data Integrity:**
- [ ] `DELETE`/`UPDATE` có `WHERE` clause không?
- [ ] FK constraint có được định nghĩa không?
- [ ] `NOT NULL` cho cột bắt buộc chưa?
- [ ] `UNIQUE` cho business key chưa?
- [ ] `CHECK` cho business rules chưa? (ví dụ: `amount > 0`)

**Migration Safety:**
- [ ] Migration file mới hay edit file cũ?
- [ ] Có rollback script không?
- [ ] Migration có idempotent không? (`CREATE TABLE IF NOT EXISTS`, etc.)

**Performance:**
- [ ] `SELECT *` thay vì columns cần thiết?
- [ ] Missing index trên FK hoặc cột hay filter?
- [ ] Cartesian product (JOIN thiếu condition)?
- [ ] Implicit type cast trong WHERE (chặn index)?

**Security:**
- [ ] Dynamic SQL có nguy cơ SQL injection không?
- [ ] Credential hay sensitive data hardcode không?

## PHÂN LOẠI KẾT QUẢ

Với mỗi issue tìm được, ghi rõ:

**🔴 Blocker** — phải sửa trước khi merge/apply:
- `DELETE`/`UPDATE` không có `WHERE`
- SQL injection qua dynamic string
- `DROP TABLE` không có rollback plan
- Data loss nguy hiểm, race condition, constraint violation

**🟡 Should fix** — nên sửa, ảnh hưởng đáng kể:
- Missing index trên FK / cột hay WHERE
- Implicit type cast chặn index
- Migration không idempotent
- `SELECT *` trên bảng lớn
- Missing rollback script

**🔵 Nit** — có thì tốt:
- Naming convention không nhất quán
- Comment thiếu hoặc lỗi thời
- Style format không đồng đều

## FORMAT BÁO CÁO

Mỗi issue: `file.sql:line → vấn đề → cách sửa đề xuất`

think hard
