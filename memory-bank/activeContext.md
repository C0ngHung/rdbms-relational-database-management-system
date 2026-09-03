# Active Context — Relational Database Management System (RDBMS) Course

## 1. Trọng tâm hiện tại / Current Focus

- Đã hoàn thành biên soạn và rà soát chuyên sâu bài học: **Part 2: Chuẩn Hóa Cơ Sở Dữ Liệu - Database Normalization** (tại `sql-server/Part 2 - Standardization/Part 2 - Standardization.md`).
- Đã hoàn tất vá các lỗi SQL integrity (ràng buộc FK, Dependency Non-Preservation trong BCNF, phân định độc lập các ví dụ và lý do sử dụng `CASCADE`).
- Đã tích hợp thành công **Section §7: Hands-on Lab** bao gồm:
  - Bài mẫu giải chi tiết: Chuyển đổi bảng thô `RawEnrollments` (0NF) → 3NF (5 bảng chuẩn hóa theo thứ tự dependency).
  - Đề bài thực hành cho Caelus: Chuyển đổi bảng thô `LibraryRaw` (0NF) → 3NF.
- Chuẩn bị bước vào thực hành giải đề `LibraryRaw` (bước 1: phân tích vi phạm).

---

## 2. Các thay đổi gần đây / Recent Changes

- **Part 2 - Chuẩn hóa CSDL (Standardization)**:
  - Trích xuất và tổng hợp transcript phụ đề tiếng Việt từ bài giảng video (`xO1DaEtHY_g`).
  - Soạn thảo tài liệu chuẩn hóa 742 dòng bao quát từ 1NF đến 5NF, 3 dạng bất thường (Insert, Update, Delete Anomaly), phụ thuộc hàm (FD, partial, transitive), BCNF và Dependency Non-Preservation, phân rã quan hệ đa trị 4NF, và khuôn khổ đánh đổi phi chuẩn hóa (OLTP vs OLAP).
  - Rà soát Diff-review chuyên sâu: phát hiện và khắc phục các thiếu sót FK ở bảng liên kết và làm rõ trade-off khi dừng ở 3NF vs lên BCNF.
  - Bổ sung Section §7 Hands-on Lab với kịch bản di chuyển bảng thô thực tế và bài tập kiểm tra tư duy thiết kế.
- **Trước đó**:
  - Hoàn thành bài học nền tảng `Part 1 - Basic Concepts` (463 dòng).
  - Thiết lập AG Kit, memory-bank, hook defense-in-depth, và quy chuẩn cam kết git.

---

## 3. Các quyết định kiến trúc đang hiệu lực / Active Decisions

1. **Commit Convention**:
   - Thống nhất định dạng: `<type>[RDBMS-NNN]:[<scope>]:<message>`.
   - `RDBMS-001`: Part 1 Basic Concepts.
   - `RDBMS-002`: Part 2 Database Normalization & Hands-on Lab.
2. **PostgreSQL / SQL Server Compatibility**:
   - Nội dung học tập tại thư mục `sql-server/` sử dụng cú pháp T-SQL/SQL Server chuẩn, đồng thời bảo đảm các nguyên lý quan hệ tương thích với PostgreSQL 16.
3. **Rollback-First & Constraint Enforcement**:
   - Mọi thiết kế bảng đều phải áp đặt ràng buộc toàn vẹn dữ liệu ở cấp CSDL (`PRIMARY KEY`, `FOREIGN KEY`, `UNIQUE`, `CHECK`, `NOT NULL`).
   - Khai báo DDL theo đúng thứ tự phụ thuộc (bảng cha trước, bảng con sau).

---

## 4. Các bước tiếp theo / Next Steps

1. **Hands-on Lab - Bài tập 2 (`LibraryRaw`)**:
   - Caelus thực hiện Bước 1: Phân tích vi phạm 1NF, 2NF, 3NF trên bảng `LibraryRaw`.
   - Review và phê duyệt thiết kế schema Bước 2.
   - Hướng dẫn Caelus viết script DDL Bước 3 và kiểm thử.
2. **Kế hoạch tiếp theo**:
   - Chuyển sang Part 3 (các chủ đề nâng cao về ràng buộc, chỉ mục hoặc câu lệnh truy vấn).

---

## 5. Vấn đề tồn đọng / Known Issues

- Không có lỗi kỹ thuật tồn đọng. Tài liệu Part 2 và hạ tầng Memory Bank đều nhất quán, chuẩn xác.
