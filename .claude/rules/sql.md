---
paths:
  - "**/*.sql"
---

# SQL / RDBMS — pointer

Nội dung đầy đủ: `@AGENTS.md` · `@.agents/rules/software-develop.md` · commit convention: `@.agents/rules/git-commit-msg.md`

Non-negotiable (AGENTS.md §8):
- Tiền tệ dùng `NUMERIC`/`DECIMAL`, không dùng `FLOAT`/`REAL`.
- Tuyệt đối không `DELETE`/`UPDATE` mà không có `WHERE` clause.
- Không `DROP TABLE` / `TRUNCATE` mà không có rollback script và backup plan rõ ràng.
- Không sửa migration file đã apply — chỉ thêm migration mới.
- Không sửa `init.sql`, `seed.sql`, `/migrations/` khi chưa được yêu cầu rõ.
- Luôn verify DDL/DML trước khi commit.
