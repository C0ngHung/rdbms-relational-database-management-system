---
name: 04-memory-bank-documentation-rules
version: 1.0.0
priority: P1
trigger: model_decision
description: "Apply when reading or updating memory-bank/ files, writing ADRs, or at session start/end"
---

# 04 — Memory Bank & Documentation Rules

These rules define how to maintain the `memory-bank/` directory and project documentation so that AI agents and the developer can resume work accurately after a context reset.

---

## 1. Memory Bank Purpose

The `memory-bank/` directory is the **single source of truth** for project context across sessions. It must be kept accurate and current at all times.

After every memory reset, the AI reads ONLY these files. If the memory bank is stale or incomplete, the AI will produce incorrect SQL or make conflicting architectural assumptions.

**Read order (mandatory at session start):**

1. `projectbrief.md`
2. `productContext.md`
3. `systemPatterns.md`
4. `techContext.md`
5. `activeContext.md`
6. `progress.md`

---

## 2. Core File Responsibilities

| File                | Owner          | Updated When                    |
| ------------------- | -------------- | ------------------------------- |
| `projectbrief.md`   | Project lead   | Project scope changes           |
| `productContext.md` | Project lead   | Learning requirements change    |
| `systemPatterns.md` | Tech lead      | Architecture decisions change   |
| `techContext.md`    | Tech lead      | Dependencies, DB, infra changes |
| `activeContext.md`  | AI / Developer | After every significant task    |
| `progress.md`       | AI / Developer | After every significant task    |

---

## 3. `activeContext.md` — What It Must Contain

This file tracks **what is happening right now**. It must answer:

```markdown
## Current Focus

What specific learning topic, schema, or query is currently being worked on.

## Recent Changes

- What was changed in the last 1-3 sessions (file names, SQL patterns, decisions)

## Active Decisions

- Open questions or design choices made (e.g. choice of normalization vs denormalization)

## Next Steps

- Concrete next actions (ordered by learning priority)

## Known Issues

- Bugs, syntax errors, slow queries, or incomplete migrations
```

Rules:

- Update `activeContext.md` **at the end of every session** that made significant changes.
- Do NOT let `activeContext.md` grow stale — outdated context is worse than no context.
- Do NOT copy-paste full code into `activeContext.md` — reference file paths and describe the change.

---

## 4. `progress.md` — What It Must Contain

This file is a living status board. It must have three sections:

```markdown
## What Works

- Topic / Schema / Query: brief status and verification notes

## What's Left to Build

- Topic / Exercise: what remains, learning difficulty estimate

## Known Issues

- Issue description: severity, affected table/query, workaround if any
```

Rules:

- Move items from "What's Left to Build" to "What Works" when a topic is verifiably complete and executed.
- Do NOT mark something as "done" if it lacks verification queries or has unverified constraints.
- Keep entries concise — one line per item is enough.

---

## 5. `systemPatterns.md` — What It Must Contain

Document database design patterns and conventions that govern this repository:

```markdown
## Architecture Style

Schema-first learning repository — DDL, DML, DQL, Indexing, and Migration patterns.

## Key Patterns

- Naming Convention: snake_case for tables, columns, constraints, and indexes
- Constraints as Defense: NOT NULL, CHECK, UNIQUE, and FK enforced at database level
- Transaction Safety: explicit BEGIN/COMMIT with rollback scripts for all DDL
- Performance Proof: EXPLAIN ANALYZE evidence before adding any index
- Append-Only Migrations: never edit an already-applied migration script

## Commit Convention

- Format: <type>[RDBMS-NNN]:[<scope>]:<message>
- Scope: schema, index, query, migration, constraint, exercise, docs
```

---

## 6. Documentation Update Triggers

Update the memory bank when:

| Trigger                            | Files to Update                                      |
| ---------------------------------- | ---------------------------------------------------- |
| New chapter/topic added            | `systemPatterns.md`, `techContext.md`, `progress.md` |
| New database table or schema added | `systemPatterns.md`, `techContext.md`                |
| Migration written or applied       | `systemPatterns.md`, `activeContext.md`              |
| Architecture / Design decision     | `systemPatterns.md`, `activeContext.md`              |
| Topic completed                    | `progress.md`, `activeContext.md`                    |
| SQL bug / deadlock discovered      | `progress.md` (Known Issues)                         |
| Session ends with significant work | `activeContext.md`, `progress.md`                    |

---

## 7. Architecture Decision Record (ADR)

For significant, irreversible decisions, write a brief ADR inside `memory-bank/` or `docs/`:

```markdown
## ADR-001: Strict 3NF normalization for initial schemas

**Date**: 2026-09-03
**Status**: Accepted

**Context**:
Learning RDBMS requires solid foundation in Boyce-Codd / 3NF before exploring denormalization.

**Decision**:
Default all schemas to 3NF. Denormalize only when benchmarking shows measurable gain in exercises.

**Consequences**:
- More JOIN operations in basic queries.
- Clean data integrity with zero update anomalies.
```

---

## 8. Inline SQL Documentation Rules

### When to write a comment

Write a comment when the **why** or the mathematical reasoning is not obvious:

```sql
-- CHECK guard: age must be within working legal limit (18-65) to protect business invariant
ALTER TABLE employees ADD CONSTRAINT chk_emp_age CHECK (age >= 18 AND age <= 65);

-- Index justified by EXPLAIN ANALYZE on 500k rows: Seq Scan 42ms -> Index Scan 0.8ms
CREATE INDEX idx_orders_customer_status ON orders (customer_id, status) WHERE status = 'PENDING';
```

### When NOT to write a comment

Do NOT comment what the SQL already clearly states:

```sql
-- BAD: obvious from SQL
-- Select all from users
SELECT * FROM users;
```

---

## 9. README Maintenance Rules

Every project must have a `README.md` that includes:

- **Purpose**: course and topic overview
- **Prerequisites**: PostgreSQL 16, psql, DBeaver
- **How to execute scripts**: exact `psql` command
- **Directory structure**: explanation of `schemas/`, `queries/`, `exercises/`
