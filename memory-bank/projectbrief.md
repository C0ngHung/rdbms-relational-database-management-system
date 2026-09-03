# Project Brief — Relational Database Management System (RDBMS) Course

## 1. Tổng quan / Overview

Đây là project khóa học **RDBMS (Relational Database Management System)** — một khóa học tự học (**self-learning course**) chuyên sâu về cơ sở dữ liệu quan hệ, SQL chuẩn, và tối ưu hóa hệ quản trị cơ sở dữ liệu từ nền tảng gốc rễ.

Project được thiết kế theo phương pháp **Level-by-Level Growth** (phát triển từng bước có kiểm soát, từ cơ bản đến nâng cao), tập trung hoàn toàn vào SQL thuần (`.sql` files), thực thi và kiểm thử trực tiếp qua `psql CLI` và `DBeaver`.

This is the **RDBMS Course** repository — a self-learning course focused on deep-dive relational database principles, standard SQL, and database performance engineering, built using the **Level-by-Level Growth** methodology.

---

## 2. Mục tiêu cốt lõi / Core Goals

1. **Học từ gốc rễ (Root-First Learning)**:
   - Hiểu rõ bản chất lưu trữ đĩa (page, block, heap file, tuple).
   - Nắm vững lý thuyết quan hệ (Relational Algebra, Functional Dependencies, Normalization 1NF → 5NF).
   - Hiểu sâu cơ chế Transaction (ACID, WAL, Isolation Levels, MVCC, Lock Contention, Deadlock).
2. **Kiến trúc bền vững (Zero-Loss Schema Design)**:
   - Coi schema là hợp đồng công khai (Public Contract) có tính ổn định cao.
   - Ưu tiên ràng buộc dữ liệu tại tầng DB (NOT NULL, CHECK, UNIQUE, FOREIGN KEY) thay vì phó mặc cho ứng dụng.
   - Quản lý tiến hóa schema (Schema Evolution) bằng migration có tính lũy kế (append-only) và kịch bản rollback an toàn.
3. **Thực nghiệm dựa trên bằng chứng (Evidence-Based SQL)**:
   - Không tối ưu hóa mù quáng; mọi quyết định thêm index hay tái cấu trúc query đều phải có bằng chứng từ `EXPLAIN (ANALYZE, BUFFERS)`.
4. **Tài liệu chuẩn mực (Production-Ready Documentation)**:
   - Tài liệu học tập song ngữ (Việt / Anh), ghi chú inline rõ lý do **WHY** (tại sao chọn giải pháp này, đánh đổi điều gì).

---

## 3. Phạm vi học tập / Scope of Topics

- **Chương 1: Data Modeling & Schema Design**:
  - Entity-Relationship Modeling (ERD).
  - Chuẩn hóa cơ sở dữ liệu (Normalization 1NF, 2NF, 3NF, BCNF) và kỹ thuật phi chuẩn hóa có kiểm soát (Controlled Denormalization).
  - Kiểu dữ liệu chuẩn (Numeric, Varchar/Text, Timestamp, UUID, JSONB).
- **Chương 2: Constraints & Data Integrity**:
  - Primary Key, Foreign Key (Cascade, Set Null, Restrict).
  - Unique Constraint, Partial Unique Constraint.
  - Complex CHECK constraints & Domain types.
- **Chương 3: Advanced DQL & Querying**:
  - Joins (Inner, Left, Right, Full Outer, Cross, Lateral Join).
  - Aggregation, Grouping Sets, Rollup, Cube.
  - Subqueries (Correlated, CTE - Common Table Expressions, Recursive CTE).
  - Window Functions (Row Number, Rank, Dense Rank, Lead, Lag, Ntile).
- **Chương 4: Indexing & Query Optimization**:
  - Cấu trúc B-tree, Hash, GiST, GIN, BRIN.
  - Composite Index, Covering Index (Index-Only Scan), Partial Index, Expression Index.
  - Đọc và phân tích `EXPLAIN ANALYZE` (Seq Scan, Index Scan, Bitmap Scan, Nested Loop, Hash Join, Merge Join).
- **Chương 5: Transactions & Concurrency Control**:
  - ACID properties và Write-Ahead Logging (WAL).
  - 4 mức cô lập giao dịch (Read Uncommitted, Read Committed, Repeatable Read, Serializable).
  - Các hiện tượng tranh chấp (Dirty Read, Non-Repeatable Read, Phantom Read, Serialization Anomaly).
  - Cơ chế Locking (Row-level lock, Table lock, Advisory locks) và xử lý Deadlock.
  - MVCC (Multi-Version Concurrency Control) trong PostgreSQL (Vacuum, Bloat).
- **Chương 6: Stored Procedures, Functions & Triggers**:
  - PL/pgSQL functions vs SQL functions.
  - Trigger cho audit trail, historical data versioning, và invariant enforcement.
- **Chương 7: Database Administration & Maintenance**:
  - Backup & Restore (`pg_dump`, `pg_restore`, point-in-time recovery).
  - Connection pooling (PgBouncer) và phân tích tài nguyên (pg_stat_statements).

---

## 4. Nguyên tắc phát triển / Principles

- **Mỗi chủ đề = 1 Level / 1 Ticket**: Định dạng commit chuẩn `feat[RDBMS-NNN]:[<scope>]:<message>`.
- **An toàn là trên hết (Safety First)**: Tuân thủ tuyệt đối quy tắc cấm kỵ tại `AGENTS.md §8` (không `DELETE`/`UPDATE` không `WHERE`, không `DROP TABLE` không backup).
- **Bảo toàn hành vi (Behavior Preservation)**: Khi refactor query hoặc schema, kết quả dữ liệu đầu ra phải được bảo toàn 100%.
