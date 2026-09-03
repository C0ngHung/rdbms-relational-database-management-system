# Product Context — Relational Database Management System (RDBMS) Course

## 1. Tại sao project này tồn tại? / Why does this project exist?

Nhiều kỹ sư phần mềm ngày nay tiếp cận cơ sở dữ liệu qua các tầng trừu tượng cao cấp (ORM như Hibernate, Prisma, TypeORM, hoặc các dịch vụ Database-as-a-Service tự động). Cách học này tạo ra lỗ hổng kiến thức nghiêm trọng:
- Viết query nhưng không lường trước được số I/O disk và memory mà engine phải tiêu thụ.
- Phụ thuộc vào application logic để bảo vệ ràng buộc toàn vẹn, dẫn đến sai lệch dữ liệu (data corruption/anomaly) khi có race condition.
- Gặp khó khăn khi hệ thống scale và xuất hiện bottleneck (slow queries, lock waits, deadlocks, table bloat).

Project này ra đời để cung cấp một lộ trình học tập **Bottom-Up** (từ gốc rễ lên trên):
- Bỏ qua các framework ứng dụng, tập trung 100% vào SQL thuần và hành vi nội tại của RDBMS engine (PostgreSQL 16).
- Rèn luyện tư duy thiết kế schema vững chắc, tối ưu hóa truy vấn có căn cứ và quản trị tranh chấp giao dịch ở cấp độ chuyên gia.

---

## 2. Vấn đề giải quyết / Problems It Solves

1. **Hiểu bản chất thay vì học vẹt (Understanding vs Memorization)**:
   - Thay vì học thuộc "nên tạo index cho cột hay tìm kiếm", ta tìm hiểu B-tree phân cấp ra sao, chi phí duy trì index khi INSERT/UPDATE lớn thế nào, và khi nào engine quyết định bỏ qua index để quét toàn bảng (Seq Scan).
2. **Kỹ năng giải quyết lỗi thực tế (Real-World Troubleshooting)**:
   - Nhận diện và xử lý các vấn đề kinh điển: N+1 query, missing index, Cartesian product, type cast làm mất index, lock contention giữa các tiến trình chạy đồng thời.
3. **An toàn dữ liệu tuyệt đối (Zero Data Loss Architecture)**:
   - Học cách viết migration có thể rollback, kiểm thử preview số dòng bị ảnh hưởng (`SELECT COUNT(*)`) trước khi xóa/sửa dữ liệu.
4. **Phân tích trade-off chuyên sâu (Architectural Trade-Offs)**:
   - Trả lời rõ ràng: Chuẩn hóa 3NF đem lại gì và mất gì? Khi nào được phép phi chuẩn hóa (Denormalization)? Tác động của isolation level đến throughput của hệ thống là bao nhiêu?

---

## 3. Luồng học tập / Learning Flow

```
Chương 1: Modeling & Normalization
       ↓
Chương 2: Constraints & Data Types
       ↓
Chương 3: Advanced DQL & Joins
       ↓
Chương 4: Indexing & EXPLAIN ANALYZE
       ↓
Chương 5: Transactions, ACID & MVCC
       ↓
Chương 6: Stored Procedures & Triggers
       ↓
Chương 7: Administration, Backup & Performance Tuning
```

Mỗi bài học tuân theo chu trình chuẩn:
1. **Lý thuyết & Khái niệm cốt lõi**: Giải thích cơ chế nội bộ của database engine.
2. **Triển khai DDL/DML**: Script SQL hoàn chỉnh, có comment giải thích lý do thiết kế.
3. **Thực nghiệm & Đo lường**: Chạy qua `psql` hoặc `DBeaver`, kiểm tra `EXPLAIN ANALYZE`.
4. **Bài tập thử thách & Rollback**: Tự tay sửa query chậm, xử lý deadlock hoặc viết script down-migration.

---

## 4. Đối tượng phục vụ / Target Audience

- Kỹ sư Backend muốn làm chủ hoàn toàn tầng lưu trữ dữ liệu.
- Kỹ sư chuẩn bị phỏng vấn Senior/Staff/Lead với yêu cầu sâu về Database Internals & Performance.
- Người tự học mong muốn xây dựng nền tảng khoa học máy tính và hệ thống vững chắc từ zero.
