# System Patterns — Relational Database Management System (RDBMS) Course

## 1. Phong cách kiến trúc / Architecture Style

- **Repository Type**: SQL-First Educational Repository.
- **Tập trung**: File mã nguồn SQL thuần (`.sql`), script migration, tài liệu phân tích kỹ thuật và bài tập thực hành.
- **Không có tầng ứng dụng nặng**: Toàn bộ nghiệp vụ, logic bảo vệ dữ liệu và tối ưu đều được chứng minh trực tiếp trên database engine.

---

## 2. Cấu trúc thư mục chuẩn / Standard Directory Layout

```plaintext
rdbms-relational-database-management-system/
├── AGENTS.md                 # Bộ quy tắc cốt lõi AI (Single Source of Truth)
├── CLAUDE.md                 # Wrapper quy tắc cho Claude Code
├── memory-bank/              # Ngân hàng bộ nhớ dự án duy trì context qua các phiên
│   ├── projectbrief.md
│   ├── productContext.md
│   ├── systemPatterns.md
│   ├── techContext.md
│   ├── activeContext.md
│   └── progress.md
├── schemas/                  # DDL: Định nghĩa bảng, kiểu dữ liệu, view, constraint
│   ├── 01_tables/
│   └── 02_views/
├── queries/                  # DQL: Các mẫu truy vấn từ cơ bản đến nâng cao
│   ├── 01_joins/
│   ├── 02_window_functions/
│   └── 03_cte/
├── indexes/                  # Indexing strategies & phân tích EXPLAIN ANALYZE
├── transactions/             # Kịch bản thử nghiệm isolation levels, locking & deadlocks
├── migrations/               # Scripts tiến hóa lược đồ dữ liệu (up/down)
├── exercises/                # Bài tập thực hành, case studies thực tế
└── docs/                     # Tài liệu chuyên sâu (ADRs, notes, diagrams)
```

---

## 3. Quy chuẩn viết SQL (SQL Conventions)

### 3.1. Naming Conventions

- **Tên bảng & view**: `snake_case`, danh từ số nhiều (e.g. `users`, `order_items`, `accounts`).
- **Tên cột**: `snake_case`, mô tả rõ ràng (e.g. `user_id`, `created_at`, `total_amount`).
- **Khóa chính**: Cột đơn đặt là `id` hoặc `<table_singular>_id`. Khóa phụ đặt là `<referenced_table_singular>_id`.
- **Ràng buộc (Constraints)**:
  - Khóa chính: `pk_<table>` (e.g. `pk_orders`)
  - Khóa ngoại: `fk_<table>_<referenced_table>` (e.g. `fk_order_items_orders`)
  - Unique: `uq_<table>_<column>` (e.g. `uq_users_email`)
  - Check: `chk_<table>_<condition_name>` (e.g. `chk_employees_age_range`)
- **Index**:
  - B-tree mặc định: `idx_<table>_<column(s)>` (e.g. `idx_orders_customer_id`)
  - Unique index: `udx_<table>_<column(s)>`
  - Partial index: `idx_<table>_<column>_<filter>` (e.g. `idx_orders_status_pending`)

### 3.2. Tiêu chuẩn kiểu dữ liệu

- **Tiền tệ, số thập phân tài chính**: Bắt buộc dùng `NUMERIC(p, s)` hoặc `DECIMAL(p, s)`. Tuyệt đối **CẤM** dùng `FLOAT` hoặc `REAL` do sai số dấu phẩy động.
- **Chuỗi ký tự**: Ưu tiên `TEXT` hoặc `VARCHAR(n)` có giới hạn hợp lý. Không dùng kiểu ký tự cố định `CHAR(n)` trừ khi độ dài luôn không đổi (e.g. mã quốc gia ISO-2 `CHAR(2)`).
- **Thời gian**: Luôn dùng `TIMESTAMPTZ` (`TIMESTAMP WITH TIME ZONE`) để tránh nhầm lẫn múi giờ.

### 3.3. An toàn DDL & DML

- **Preview trước khi ghi**: Luôn chạy `SELECT COUNT(*)` với đúng điều kiện `WHERE` trước khi chạy `UPDATE` hoặc `DELETE`.
- **Không bao giờ chạy lệnh hủy diệt mà không có WHERE**: `DELETE FROM <table>` hoặc `UPDATE <table> SET ...` không có `WHERE` bị chặn nghiêm ngặt theo quy tắc bảo vệ.
- **Rollback cho DDL**: Mọi script thay đổi cấu trúc bảng trong `migrations/` đều phải đi kèm kịch bản down-migration hoàn chỉnh.

---

## 4. Quy ước Commit (Git Convention)

Mỗi thay đổi phải tuân thủ chuẩn commit đã quy định tại `.agents/rules/git-commit-msg.md`:

```
<type>[RDBMS-NNN]:[<scope>]:<message>
```

- **Ticket Prefix**: `RDBMS-NNN` (e.g. `RDBMS-001`, `RDBMS-002`), mỗi ticket đại diện cho 1 bài học hoặc 1 chủ đề.
- **Scopes cho phép**: `schema`, `index`, `query`, `migration`, `constraint`, `exercise`, `docs`, `config`.
- **Ví dụ thực tế**:
  - `feat[RDBMS-001]:[schema]:create initial university schema with students and courses`
  - `docs[RDBMS-002]:[index]:add comparison between b-tree and hash indexes`
  - `fix[RDBMS-003]:[query]:correct lateral join syntax in customer ranking query`

---

## 5. Cơ chế bảo vệ đa lớp (Defense in Depth)

- **Tầng 1 - Root Rules (`AGENTS.md`)**: Chỉ dẫn hành vi, ràng buộc an toàn, phong cách phản hồi.
- **Tầng 2 - Antigravity PreToolUse Hook**:
  - Node.js Validator (`validate-tool-call.mjs`): Chặn các lệnh hủy hoại hệ thống.
  - Java Guard (`GuardCommand.java`): Chặn lệnh cấm kỵ DB (`DROP DATABASE/TABLE`, force-push, sửa file nhạy cảm).
- **Tầng 3 - Protected Paths**: `.agents/protected-paths.txt` khóa quyền sửa đổi tự động đối với `init.sql`, `seed.sql`, `/migrations/`.
