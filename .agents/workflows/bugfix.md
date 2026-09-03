---
name: bugfix
version: 1.0.0
description: Fix lỗi SQL theo quy trình CONTEXT / ROOT CAUSE / CONSTRAINTS / SUCCESS
requires_agents:
  - debugger
requires_skills:
  - systematic-debugging
  - bugfix
artifact_outputs:
  - type: inline
    description: Bug fix analysis and corrected SQL
argument-hint: "[mô tả lỗi hoặc đường dẫn file .sql]"
---

# Bug Fix: $ARGUMENTS

Làm theo đúng quy trình dưới đây.

## 1. CONTEXT

Xác định chính xác file `.sql`, table, query, hoặc constraint liên quan tới: $ARGUMENTS
Đọc SQL trước khi kết luận. Nếu tôi chưa cung cấp repro steps hoặc error message, hỏi tôi.

## 2. ROOT CAUSE

Giải thích nguyên nhân gốc bằng 2-3 câu TRƯỚC khi sửa bất cứ dòng nào.
Nếu chưa chắc chắn, nói rõ là chưa chắc và đề xuất cách kiểm chứng (ví dụ: `EXPLAIN ANALYZE`, check constraint, `\d tablename`).

## 3. CONSTRAINTS

- Chỉ sửa trong phạm vi query/file gây lỗi. KHÔNG refactor rộng.
- Không đổi schema contract (tên table, tên column, FK) mà không có migration script kèm theo.
- Không thêm index mới khi chưa có `EXPLAIN ANALYZE` chứng minh cần thiết.
- Diff tối thiểu.

## 4. SUCCESS CRITERIA

- [ ] Lỗi không còn tái hiện theo repro steps
- [ ] Chạy lại toàn bộ queries liên quan trên psql — kết quả không đổi so với trước khi fix
- [ ] Nếu là query bug: `EXPLAIN ANALYZE` không có hành vi bất ngờ (Seq Scan không cần thiết, etc.)
- [ ] Nếu là schema bug: rollback script đã được viết và verify
- [ ] Không sửa existing behavior — chỉ sửa phần bị lỗi

## 5. BÁO CÁO

Liệt kê file đã sửa (full path) và lý do từng thay đổi.

think hard
