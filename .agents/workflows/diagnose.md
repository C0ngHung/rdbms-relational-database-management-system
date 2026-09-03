---
name: diagnose
version: 1.0.0
description: Điều tra SQL bug chưa rõ nguyên nhân — giả thuyết + bằng chứng trước, sửa sau
requires_agents:
  - debugger
requires_skills:
  - diagnose
  - systematic-debugging
artifact_outputs:
  - type: inline
    description: Root cause analysis with evidence and fix
argument-hint: "[triệu chứng hoặc mô tả vấn đề]"
---

# Diagnose: $ARGUMENTS

## QUY TẮC SỐ 1

**KHÔNG sửa bất kỳ dòng SQL nào cho tới khi xác định được root cause có bằng chứng.**

## BƯỚC 1 — MÔ TẢ TRIỆU CHỨNG

Trả lời các câu hỏi này trước:
- Triệu chứng cụ thể là gì? (wrong result, error message, slow query, constraint violation...)
- Xảy ra khi nào / với data nào? Không xảy ra khi nào?
- Môi trường: psql CLI, DBeaver, application, version PostgreSQL?
- Error message / log đầy đủ (nếu có)?

## BƯỚC 2 — CÁC GIẢI THUYẾT (ít nhất 3)

Liệt kê 3-5 giả thuyết, xếp theo xác suất giảm dần:

1. **[Xác suất cao]** `<Giả thuyết>` — Kiểm chứng bằng: `<lệnh psql cụ thể>`
2. **[Xác suất trung bình]** `<Giả thuyết>` — Kiểm chứng bằng: ...
3. **[Xác suất thấp]** `<Giả thuyết>` — Kiểm chứng bằng: ...

**SQL context điển hình để điều tra:**

```sql
-- Slow query
EXPLAIN ANALYZE <query>;

-- Check index usage
SELECT * FROM pg_stat_user_indexes WHERE relname = '<table>';

-- Check constraints
SELECT conname, contype, pg_get_constraintdef(oid) 
FROM pg_constraint WHERE conrelid = '<table>'::regclass;

-- Check locks
SELECT * FROM pg_locks pl JOIN pg_stat_activity pa ON pl.pid = pa.pid;

-- Data anomaly
SELECT COUNT(*), <column> FROM <table> GROUP BY <column> HAVING COUNT(*) > 1;
```

## BƯỚC 3 — CHỨNG MINH ROOT CAUSE

Chỉ kết luận khi có **bằng chứng cụ thể**: output của query điều tra, explain plan, error log.
Nói rõ: "Root cause là X, chứng minh bởi: [output cụ thể]".

## BƯỚC 4 — SỬA (chỉ sau khi có root cause)

Sau khi biết root cause, viết fix tối thiểu, kèm verify sau khi sửa.

think harder
