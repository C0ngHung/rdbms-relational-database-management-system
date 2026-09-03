# Tech Context — Relational Database Management System (RDBMS) Course

## 1. Môi trường công nghệ / Technology Stack

### Hệ quản trị cơ sở dữ liệu & Công cụ cốt lõi
| Thành phần | Phiên bản / Chi tiết | Vai trò |
|---|---|---|
| **PostgreSQL** | 16.x (hoặc mới nhất) | Primary RDBMS Engine |
| **psql CLI** | Theo PostgreSQL 16 | Công cụ dòng lệnh thực thi SQL, kiểm tra query plan, quản trị |
| **DBeaver Community** | Phiên bản mới nhất | GUI Client quản lý, trực quan hóa ERD và phân tích dữ liệu |
| **Git** | 2.x | Hệ thống quản lý phiên bản |
| **Bash / Linux** | Ubuntu LTS | Môi trường hệ điều hành thực thi scripts |
| **Java JDK** | 21+ | Runtime thực thi single-file guard script (`GuardCommand.java`) |
| **Python** | 3.12+ | Runtime kiểm tra tính hợp lệ của toolkit (`validate_kit.py`, `generate_manifest.py`) |

---

## 2. Thiết lập Môi trường Phát triển (Local Setup)

### 2.1. Kết nối PostgreSQL bằng psql
```bash
# Đăng nhập vào psql console
psql -U <username> -d <database_name> -h localhost -p 5432

# Thực thi một script SQL từ terminal
psql -U <username> -d <database_name> -f path/to/script.sql

# Thực thi kèm dừng ngay lập tức nếu gặp lỗi (ON_ERROR_STOP)
psql -v ON_ERROR_STOP=1 -U <username> -d <database_name> -f path/to/script.sql
```

### 2.2. Các câu lệnh meta-commands thông dụng trong psql
- `\l`: Liệt kê danh sách database.
- `\c <dbname>`: Chuyển đổi kết nối sang database khác.
- `\dt`: Liệt kê tất cả các bảng trong schema hiện tại.
- `\d <table_name>`: Xem chi tiết cấu trúc cột, khóa, index và constraint của bảng.
- `\di`: Liệt kê danh sách các indexes.
- `\df`: Liệt kê danh sách functions / stored procedures.
- `\timing on`: Bật đo lường thời gian thực thi của từng query.
- `\x on`: Bật chế độ hiển thị kết quả theo dạng mở rộng (Extended display / vertical layout).

---

## 3. Tooling tích hợp AI (AI Agent Toolchain)

Dự án hỗ trợ đồng bộ đa công cụ theo kiến trúc **AG Kit Portable**:

1. **Google Antigravity (IDE & CLI `agy`)**:
   - Runtime chính thức (Primary Production Runtime).
   - Tự động nạp các quy tắc tại `.agents/rules/` và workflows tại `.agents/workflows/`.
   - Bảo vệ an toàn tự động qua hooks tại `.agents/hooks.json`.
2. **Claude Code (CLI & Extension)**:
   - Đọc quy tắc qua `CLAUDE.md` (nhập `@AGENTS.md`) và `.claude/rules/sql.md`.
   - Cung cấp các lệnh tương đương qua `.claude/commands/`.
   - Kiểm soát quyền ghi và thực thi lệnh qua `.claude/settings.json`.
3. **GitHub Copilot / OpenAI Codex**:
   - Tự động nhận diện quy ước dự án qua `.github/copilot-instructions.md`.
   - Tuân thủ cấu trúc commit, tiêu chuẩn kiểu dữ liệu và checklist an toàn của `AGENTS.md`.

---

## 4. Ràng buộc kỹ thuật & Giới hạn (Technical Constraints)

- **Không sử dụng Maven / Gradle / npm / Node server**: Dự án này thuần túy lưu trữ script CSDL, không phải là ứng dụng web.
- **Không cài đặt các dependencies bên ngoài không cần thiết**: Giữ kho mã nguồn tinh gọn, tập trung và portable.
- **File migration có tính chất Append-Only**: Khi một migration đã chạy vào database, không được phép sửa nội dung file cũ mà phải tạo migration mới để cập nhật hoặc sửa lỗi.
