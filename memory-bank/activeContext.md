# Active Context — Relational Database Management System (RDBMS) Course

## 1. Trọng tâm hiện tại / Current Focus

- Đã hoàn tất việc trích xuất và biên dịch phụ đề video cho bài học **Part 3: Sơ đồ thực thể - quan hệ ERD** (`sql-server/Part 3 - Entity-Relationship Diagram (ERD)/Part 3 - Entity-Relationship Diagram (ERD).md`).
- Đã cấu trúc lại nội dung thành các phần rõ ràng (ER Model là gì, Các thành phần chính, Ký pháp Chen, Ví dụ Quản lý thư viện).
- Đã chèn thành công 12 hình ảnh minh họa trải đều từ `Figure 1` đến `Figure 12` kèm chú thích tiếng Anh chuẩn hóa để diễn giải trực quan khái niệm (Entity, Attribute, Relationship, Chen Notation) và phân tích sâu bài toán Quản lý thư viện.
- Chuẩn bị tạo các commit phân tách theo từng file/tính năng, pull sync với remote main và push code.

---

## 2. Các thay đổi gần đây / Recent Changes

- **Chính tả & Chuẩn hóa (Refactoring)**:
  - Đã chuẩn hóa toàn bộ thuật ngữ `Picture` thành `Figure` (vd: `*Figure 1*`) trên toàn dự án để tuân thủ Best Practice trong tài liệu kỹ thuật.
- **Part 1 - Các Khái Niệm Cơ Bản**:
  - Bổ sung chú thích sư phạm bằng tiếng Anh (Figure captions: `*Figure 1*` → `*Figure 4*`) dưới 4 hình ảnh minh họa trong `Part 1 - Basic Concepts.md`.
- **Part 2 - Chuẩn hóa CSDL (Standardization)**:
  - Khắc phục lỗi render công thức KaTeX tại §3.3 (Transitive Dependency) bằng cách chuyển sang ký hiệu thuộc tính PascalCase `\text{StudentID} \xrightarrow{(1)} \text{AvgScore} \xrightarrow{(2)} \text{AcademicRank}`, loại bỏ hoàn toàn lỗi xung đột ký tự thoát `'_' allowed only in math mode`.
  - Thêm chú thích sư phạm súc tích, giàu ý nghĩa (`*Figure 1*` đến `*Figure 12*`) dưới tất cả 12 hình ảnh minh họa trong `Part 2 - Standardization.md`.
  - Bổ sung nội dung diễn giải chi tiết cho `Example 1.png` và `Example 2.png` tại mục §3.3 (Transitive Dependency), kèm `[!NOTE]` trỏ tới §4.3 mà không làm trùng lặp SQL.
  - Điều chuyển `Principle 4.png` về đúng vị trí tại mục §4.5 (4NF), giải thích khái niệm Phụ thuộc đa trị $X \twoheadrightarrow Y$ và hiện tượng dư thừa tổ hợp.
  - Chèn sơ đồ tổng quan `Normalization.png` ngay sau tiêu đề §4.
  - Tích hợp và liên kết toàn bộ 12 hình ảnh minh họa chất lượng cao vào bài viết.
- **Trước đó**:
  - Soạn thảo tài liệu chuẩn hóa 742 dòng bao quát từ 1NF đến 5NF, Data Anomalies, Functional Dependencies, BCNF và Section §7 Hands-on Lab (`RawEnrollments` & `LibraryRaw`).
  - Hoàn thành bài học nền tảng `Part 1 - Basic Concepts` (463 dòng).
  - Thiết lập AG Kit, memory-bank, hook defense-in-depth, và quy chuẩn cam kết git.

---

## 3. Các quyết định kiến trúc đang hiệu lực / Active Decisions

1. **Commit Convention**:
   - Thống nhất định dạng: `<type>[RDBMS-NNN]:[<scope>]:<message>`.
   - `RDBMS-001`: Part 1 Basic Concepts.
   - `RDBMS-002`: Part 2 Database Normalization, Assets & Hands-on Lab.
2. **Không viết SQL ở Phần lý thuyết (§3)**:
   - Phần §3 tập trung thuần túy vào bản chất toán học/logic của Phụ thuộc hàm (FD). Toàn bộ mã nguồn DDL/DQL và script tái cấu trúc được dồn về Phần §4 (Các Dạng Chuẩn Hóa) để giữ cấu trúc sư phạm trong sáng và tránh trùng lặp nội dung.
3. **Vị trí hình ảnh tương ứng với nội dung khái niệm**:
   - Mọi sơ đồ, hình ảnh minh họa phải đặt chính xác tại section giải thích khái niệm tương ứng (e.g. `Principle 4.png` về MVD phải nằm ở §4.5 4NF, không để ở §3).
4. **Chú thích sơ đồ sư phạm (Figure Captions)**:
   - Toàn bộ hình ảnh minh họa trong tài liệu markdown đều có chú thích in nghiêng bằng tiếng Anh ngay bên dưới theo định dạng `*Figure N: Concept — Pedagogical Rationale*`, nhằm hỗ trợ việc ôn tập trực quan và nắm chắc bản chất thiết kế.
5. **Quy chuẩn đặt tên thuộc tính trong công thức KaTeX**:
   - Sử dụng PascalCase (e.g., `StudentID`, `AvgScore`, `AcademicRank`) trong các khối toán học KaTeX `$$...$$` thay cho snake_case (`student_id`), nhằm loại bỏ triệt để lỗi Markdown parser nuốt ký tự escape `\` gây lỗi `'_' allowed only in math mode`.
6. **PostgreSQL / SQL Server Compatibility**:
   - Sử dụng cú pháp T-SQL/SQL Server chuẩn, đồng thời bảo đảm các nguyên lý quan hệ tương thích hoàn toàn với PostgreSQL 16.

---

## 4. Các bước tiếp theo / Next Steps

1. **Commit, Pull & Push**:
   - Tạo các commit phân tách độc lập theo từng tính năng/tệp tin theo chuẩn `RDBMS-002`.
   - Pull sync với remote `main` và push lên nhánh `main`.
2. **Hands-on Lab - Bài tập 2 (`LibraryRaw`)**:
   - Phân tích vi phạm 1NF, 2NF, 3NF trên bảng thô `LibraryRaw`.
   - Thiết kế schema 3NF phân rã chuẩn tắc.
   - Viết script DDL và nạp dữ liệu kiểm thử.

---

## 5. Vấn đề tồn đọng / Known Issues

- Không có lỗi kỹ thuật tồn đọng. Toàn bộ tài liệu, liên kết hình ảnh, chú thích và Memory Bank đều nhất quán, chuẩn xác.

