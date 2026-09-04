# Progress — Relational Database Management System (RDBMS) Course

## 1. Những phần đã hoàn thành / What Works

- [x] **Tài liệu & Bài học nền tảng**:
  - [Part 1: Các Khái Niệm Cơ Bản Trong CSDL Quan Hệ - Basic Concepts](file:///d:/learnByMyself/course/database/rdbms-relational-database-management-system/sql-server/Part%201%20-%20Basic%20Concepts/Part%201%20-%20Basic%20Concepts.md): Hoàn thành 463 dòng biên soạn chuyên sâu từ bài giảng video (Entity, Table, Row, Column, NULL, Super/Candidate/Primary/Composite Key, Foreign Key & Referential Integrity, Table Relationships 1:1, 1:N, N:M qua Junction Table, sơ đồ ERD, 4 sơ đồ hình ảnh minh họa `RDBMS.png`, `1-1.png`, `1-n.png`, `n-n.png` kèm 4 chú thích giải nghĩa bằng tiếng Anh `*Figure 1*` → `*Figure 4*`, và bộ 8 câu hỏi phỏng vấn thực chiến).
  - [Part 2: Chuẩn Hóa Cơ Sở Dữ Liệu - Database Normalization](file:///d:/learnByMyself/course/database/rdbms-relational-database-management-system/sql-server/Part%202%20-%20Standardization/Part%202%20-%20Standardization.md): Hoàn thành 835 dòng chuyên sâu kèm 12 hình ảnh sơ đồ minh họa trực quan (CSDL Good, 3 Anomalies, Principles 1-4, BCNF 3.5, Examples 1-2, Normalization flow) cùng 12 chú thích giải nghĩa sư phạm bằng tiếng Anh (`*Picture 1*` → `*Picture 12*`), phân tích chuyên sâu Phụ thuộc bắc cầu (Lookup Table vs Dynamic CASE WHEN), 4NF Multi-Valued Dependency và hiện tượng bùng nổ tích Descartes, cùng Section §7 Hands-on Lab (bài giải mẫu `RawEnrollments` và bài tập thực hành `LibraryRaw`).
- [x] **Hạ tầng AI Agent (AG Kit)**:
  - Bộ quy chuẩn cốt lõi [AGENTS.md](file:///d:/learnByMyself/course/database/rdbms-relational-database-management-system/AGENTS.md) hoàn thiện, bao hàm toolchain PostgreSQL 16, định dạng commit `RDBMS-NNN` và danh mục cấm kỵ §8.
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

| Chủ đề / Ticket | Nội dung bài học                                                                  | Trạng thái                   |
| --------------- | --------------------------------------------------------------------------------- | ---------------------------- |
| **RDBMS-001**   | Part 1: Các Khái Niệm Cơ Bản Trong CSDL Quan Hệ - Basic Concepts                  | ✅ Hoàn thành                |
| **RDBMS-002**   | Part 2: Chuẩn Hóa Cơ Sở Dữ Liệu (1NF → 5NF, BCNF, Anomalies & Hands-on Lab)       | 🔄 Đang thực hành Lab        |
| **RDBMS-003**   | Ràng buộc toàn vẹn & Kiểu dữ liệu chuyên sâu (PK, FK, CHECK, UNIQUE, DOMAIN)      | 📋 Lên kế hoạch              |
| **RDBMS-004**   | Kỹ thuật truy vấn nâng cao: Multi-table Joins, Cross Join & Lateral Join          | 📋 Lên kế hoạch              |
| **RDBMS-005**   | Common Table Expressions (CTE) & Recursive CTE (Xử lý dữ liệu phân cấp/cây)       | 📋 Lên kế hoạch              |
| **RDBMS-006**   | Window Functions (Row Number, Rank, Dense Rank, Lead, Lag, Running Totals)        | 📋 Lên kế hoạch              |
| **RDBMS-007**   | Cấu trúc Index nội bộ: B-tree, Hash, GIN, GiST, BRIN & Covering Indexes           | 📋 Lên kế hoạch              |
| **RDBMS-008**   | Đọc & Phân tích Query Execution Plans bằng `EXPLAIN (ANALYZE, BUFFERS)`           | 📋 Lên kế hoạch              |
| **RDBMS-009**   | Giao dịch (Transactions), ACID, WAL & 4 Mức độ cô lập (Isolation Levels)          | 📋 Lên kế hoạch              |
| **RDBMS-010**   | Concurrency Control: Row/Table Locks, Lock Contention, Deadlocks & Advisory Locks | 📋 Lên kế hoạch              |
| **RDBMS-011**   | PostgreSQL MVCC Internals: Heap Tuples, Vacuum, Autovacuum & Bloat Control        | 📋 Lên kế hoạch              |
| **RDBMS-012**   | Stored Functions, PL/pgSQL Procedures & Audit Triggers                            | 📋 Lên kế hoạch              |
| **RDBMS-013**   | Database Maintenance: Backup/Restore (`pg_dump`), Index Rebuild & Monitoring      | 📋 Lên kế hoạch              |

---

## 3. Vấn đề đã biết / Known Issues

_Hiện tại không có vấn đề kỹ thuật nào._
