# Active Context — Relational Database Management System (RDBMS) Course

## 1. Trọng tâm hiện tại / Current Focus

- Đã hoàn thành biên soạn toàn diện bài học nền tảng: **Part 1: Các Khái Niệm Cơ Bản Trong CSDL Quan Hệ - Basic Concepts** (tại `sql-server/Part 1 - Basic Concepts/Part 1 - Basic Concepts.md`).
- Đã ban hành và tích hợp quy chuẩn định dạng bài học **Format Lesson Rule** (`.agents/rules/format-rule.md`) vào AG Kit registry.
- Chuẩn bị bước vào các bài học và thực hành schema tiếp theo trong lộ trình: **Chương 1 — Data Modeling, ERD & Chuẩn hóa dữ liệu (1NF → 3NF)** (Ticket `RDBMS-001`).

---

## 2. Các thay đổi gần đây / Recent Changes

- **Bài học & Tài liệu học tập**:
  - Tạo và hoàn thiện tài liệu `sql-server/Part 1 - Basic Concepts/Part 1 - Basic Concepts.md` gồm 8 phần chi tiết, 463 dòng: Thực thể, Bảng, Cột, Hàng, NULL vs NOT NULL, Các loại Khóa (Super/Candidate/Primary/Composite), Khóa ngoại & Toàn vẹn tham chiếu, Các quan hệ 1:1, 1:N, N:M với Junction Table, Sơ đồ ERD và bộ 8 câu hỏi phỏng vấn kèm code SQL, minh họa 4 sơ đồ trực quan (`RDBMS.png`, `1-1.png`, `1-n.png`, `n-n.png`).
- **Hạ tầng AI Toolkit**:
  - Tạo `AGENTS.md` (Single Source of Truth) định hình các quy chuẩn SQL, toolchain psql/PostgreSQL 16, và danh mục quy tắc cấm kỵ §8.
  - Tạo `CLAUDE.md` và `.github/copilot-instructions.md` đồng bộ tri thức cho các AI client khác.
  - Thiết lập Defense in Depth: Mở rộng `.agents/hooks.json` với cả Node.js Validator và Java `GuardCommand.java`, bảo vệ các file nhạy cảm trong `.agents/protected-paths.txt`.
  - Kiểm thử 30/30 test cases an toàn trong `.agents/guard/guard_test.sh` thành công tuyệt đối.
  - Adapt 5 workflows chuyên biệt cho SQL: `/bugfix`, `/feature`, `/refactor`, `/diff-review`, `/diagnose`.
  - Tích hợp và đồng bộ registry: `generate_manifest.py` & `validate_kit.py` đạt `0 error(s), 0 warning(s)`.
- **Hạ tầng Bộ nhớ (Memory Bank)**:
  - Khởi tạo đầy đủ 6 tài liệu chuẩn hóa trong `memory-bank/`.
  - Thêm quy chuẩn `04-memory-bank-documentation-rules.md` vào `.agents/rules/` và liên kết trong `AGENTS.md`.

---

## 3. Các quyết định kiến trúc đang hiệu lực / Active Decisions

1. **Commit Convention**:
   - Thống nhất định dạng: `<type>[RDBMS-NNN]:[<scope>]:<message>`.
   - Mỗi ticket `RDBMS-NNN` (e.g. `RDBMS-001`, `RDBMS-002`) đại diện cho 1 bài học / chủ đề học tập.
2. **PostgreSQL-First Execution**:
   - Sử dụng PostgreSQL 16 làm chuẩn mực engine thực hành.
   - Mọi quyết định thêm index hoặc tối ưu hóa query đều phải dựa trên bằng chứng đo lường từ `EXPLAIN (ANALYZE, BUFFERS)`.
3. **Rollback-First DDL**:
   - Bất kỳ thay đổi cấu trúc dữ liệu nào trong tương lai đều phải đi kèm down-migration trước khi merge/commit.

---

## 4. Các bước tiếp theo / Next Steps

1. **Tạo cấu trúc thư mục khởi đầu**:
   - Tạo các thư mục gốc: `schemas/`, `queries/`, `exercises/`, `migrations/`.
2. **Khởi tạo bài học đầu tiên (`RDBMS-001`)**:
   - Thiết kế lược đồ CSDL mẫu (ví dụ: University / E-commerce / Banking) minh họa chuẩn hóa 1NF → 3NF.
   - Viết DDL tạo bảng kèm các ràng buộc toàn vẹn cơ bản (PK, FK, NOT NULL, CHECK, UNIQUE).
   - Viết seed data mẫu để phục vụ cho các bài tập truy vấn tiếp theo.

---

## 5. Vấn đề tồn đọng / Known Issues

- Hiện tại chưa có vấn đề hay lỗi kỹ thuật nào. Toolkit và Memory Bank đang ở trạng thái sạch sẽ và hoạt động trơn tru.
