---
name: refactor
version: 1.0.0
description: Refactor SQL an toàn — giữ nguyên behavior và kết quả query 100%
requires_agents:
  - code-archaeologist
requires_skills:
  - refactor
  - clean-code
artifact_outputs:
  - type: inline
    description: Refactored SQL with rollback script
argument-hint: "[file, table, hoặc query cần refactor]"
---

# Refactor: $ARGUMENTS

## NGUYÊN TẮC BẤT DI BẤT DỊCH

Đây là **refactor**, không phải rewrite. Kết quả query quan sát được từ bên ngoài phải giữ nguyên 100%.

## 1. TRƯỚC KHI LÀM

- Đọc SQL hiện tại và mô tả nó đang làm gì.
- Lưu lại output hiện tại của query quan trọng nhất (chụp kết quả `SELECT` để so sánh sau).
- Nếu là DDL refactor (rename column, split table): **viết rollback script TRƯỚC**, chạy thử rollback script, xác nhận nó hoạt động.
- Liệt kê các bước refactor theo thứ tự, mỗi bước là một thay đổi nhỏ độc lập.

## 2. CONSTRAINTS

- Không đổi tên table / column mà không tạo VIEW alias để backward compatible.
- Không gộp thêm logic mới vào lần refactor này.
- Sau **mỗi bước**, chạy lại query verify — kết quả phải khớp với output đã lưu ở bước trên.
- Không đụng tới file ngoài phạm vi: $ARGUMENTS
- Migration file đã apply là **bất khả xâm phạm** — chỉ thêm file mới.

## 3. SUCCESS CRITERIA

- [ ] Toàn bộ queries liên quan trả về kết quả y hệt trước refactor — KHÔNG sửa expected output cho pass
- [ ] Rollback script đã được viết và verify
- [ ] `EXPLAIN ANALYZE` sau refactor không tệ hơn trước (execution plan không có Seq Scan mới bất ngờ)
- [ ] SQL dễ đọc hơn rõ rệt — nói rõ cải thiện ở điểm nào

think harder
