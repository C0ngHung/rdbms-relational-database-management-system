# Progress — Relational Database Management System (RDBMS) Course

## 1. Những phần đã hoàn thành / What Works

- [x] **Hạ tầng AI Agent (AG Kit)**:
  - Bộ quy chuẩn cốt lõi [AGENTS.md](file:///home/ubuntu/Data_D/learnByMyself/course/database/rdbms-relational-database-management-system/AGENTS.md) hoàn thiện, bao hàm toolchain PostgreSQL 16, định dạng commit `RDBMS-NNN` và danh mục cấm kỵ §8.
  - Các tệp wrapper hỗ trợ công cụ ngoài: `CLAUDE.md`, `.claude/settings.json`, `.claude/rules/sql.md`, `.github/copilot-instructions.md`.
  - Lớp bảo vệ kép (Defense in Depth) gồm Node.js File Guard + Java Project Guard (`GuardCommand.java`) kèm danh mục file nhạy cảm `.agents/protected-paths.txt`.
  - Bộ 30 test cases an toàn đã pass 100% trong `guard_test.sh`.
  - 5 Workflows chuyên sâu cho SQL: `/bugfix`, `/feature`, `/refactor`, `/diff-review`, `/diagnose`.
  - Đăng ký và đồng bộ registry tự động: `validate_kit.py` xác nhận 0 lỗi, 0 cảnh báo.
- [x] **Hạ tầng Quản lý Bộ nhớ (Memory Bank)**:
  - Khởi tạo 6 tài liệu cốt lõi trong `memory-bank/`: `projectbrief.md`, `productContext.md`, `systemPatterns.md`, `techContext.md`, `activeContext.md`, `progress.md`.
  - Ban hành quy chuẩn `04-memory-bank-documentation-rules.md` trong `.agents/rules/` và Rule Map.

---

## 2. Lộ trình bài học cần xây dựng / What's Left to Build

| Chủ đề / Ticket | Nội dung bài học | Trạng thái |
|---|---|---|
| **RDBMS-001** | Data Modeling, ERD & Chuẩn hóa dữ liệu (1NF, 2NF, 3NF, BCNF) | ⏳ Sẵn sàng khởi động |
| **RDBMS-002** | Ràng buộc toàn vẹn & Kiểu dữ liệu chuyên sâu (PK, FK, CHECK, UNIQUE, DOMAIN) | 📋 Lên kế hoạch |
| **RDBMS-003** | Kỹ thuật truy vấn nâng cao: Multi-table Joins, Cross Join & Lateral Join | 📋 Lên kế hoạch |
| **RDBMS-004** | Common Table Expressions (CTE) & Recursive CTE (Xử lý dữ liệu phân cấp/cây) | 📋 Lên kế hoạch |
| **RDBMS-005** | Window Functions (Row Number, Rank, Dense Rank, Lead, Lag, Running Totals) | 📋 Lên kế hoạch |
| **RDBMS-006** | Cấu trúc Index nội bộ: B-tree, Hash, GIN, GiST, BRIN & Covering Indexes | 📋 Lên kế hoạch |
| **RDBMS-007** | Đọc & Phân tích Query Execution Plans bằng `EXPLAIN (ANALYZE, BUFFERS)` | 📋 Lên kế hoạch |
| **RDBMS-008** | Giao dịch (Transactions), ACID, WAL & 4 Mức độ cô lập (Isolation Levels) | 📋 Lên kế hoạch |
| **RDBMS-009** | Concurrency Control: Row/Table Locks, Lock Contention, Deadlocks & Advisory Locks | 📋 Lên kế hoạch |
| **RDBMS-010** | PostgreSQL MVCC Internals: Heap Tuples, Vacuum, Autovacuum & Bloat Control | 📋 Lên kế hoạch |
| **RDBMS-011** | Stored Functions, PL/pgSQL Procedures & Audit Triggers | 📋 Lên kế hoạch |
| **RDBMS-012** | Database Maintenance: Backup/Restore (`pg_dump`), Index Rebuild & Monitoring | 📋 Lên kế hoạch |

---

## 3. Vấn đề đã biết / Known Issues

*Hiện tại không có vấn đề kỹ thuật nào.*
