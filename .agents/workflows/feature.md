---
name: feature
version: 1.0.0
description: Implement schema/query mới — plan trước, chờ duyệt, rồi mới viết SQL
requires_agents:
  - backend-specialist
requires_skills:
  - feature
  - plan-writing
artifact_outputs:
  - type: inline
    description: Implementation plan and SQL changes
argument-hint: "[mô tả feature hoặc learning topic]"
---

# Feature: $ARGUMENTS

## GIAI ĐOẠN 1 — PLAN (dừng lại chờ duyệt)

Trước khi viết bất kỳ dòng SQL nào, trình bày:

1. **Context**: table(s) bị ảnh hưởng, các file sẽ đụng tới, pattern hiện có trong codebase mà feature này nên bám theo.
2. **Danh sách file** tạo mới / chỉnh sửa, kèm mục đích từng file (DDL / DML / DQL / migration).
3. **Edge case** đã nghĩ tới:
   - **Idempotency**: `INSERT ... ON CONFLICT DO NOTHING` hay unique constraint chưa?
   - **Transaction boundary**: cần explicit `BEGIN`/`COMMIT` không? Rollback khi bước nào thất bại?
   - **Constraint impact**: FK, UNIQUE, NOT NULL, CHECK — thêm mới hay ảnh hưởng existing?
   - **Performance**: bảng lớn không? Cần index mới? Có Seq Scan không?
   - **Migration safety**: forward-only migration, không edit file đã apply.
4. **Rollback script**: nếu là DDL, viết down-migration trước.
5. **Câu hỏi** nếu yêu cầu còn mơ hồ — đừng đoán.

**DỪNG tại đây và chờ tôi duyệt.**

## GIAI ĐOẠN 2 — IMPLEMENT (chỉ chạy sau khi tôi duyệt)

### Constraints

- Bám đúng naming convention và style đã có trong codebase — không tự nghĩ ra cấu trúc mới.
- `FLOAT`/`REAL` cho monetary values là **cấm** — dùng `NUMERIC`/`DECIMAL`.
- `DELETE`/`UPDATE` mà không có `WHERE` là **cấm**.
- Migration file đã apply là **bất khả xâm phạm** — chỉ thêm file mới.
- Tuân thủ toàn bộ quy tắc trong `AGENTS.md`.

### Success criteria

- [ ] Happy path chạy đúng và verify được bằng `SELECT`
- [ ] Edge case đã nêu ở giai đoạn 1 đều được xử lý
- [ ] Không có Seq Scan bất ngờ trên bảng lớn (kiểm tra `EXPLAIN ANALYZE`)
- [ ] Rollback script đã được viết (nếu là DDL)
- [ ] Existing queries vẫn trả về đúng kết quả — không bị vỡ

think hard
