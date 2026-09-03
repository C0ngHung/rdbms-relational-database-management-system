---
name: software-develop
version: 1.0.0
priority: P1
trigger: manual
---

# Software Development Rules

> Core essentials are always-on via root `AGENTS.md`. This is the full detailed reference — invoke with `@software-develop`.
> Practical engineering behavior for building schemas and queries incrementally without breaking existing structure.

---

## 0. Core Philosophy

Your primary job is **not to implement features**, but to: preserve **data integrity**, isolate **schema change**, prevent **data loss**, and enable **future evolution at low cost**.

### Trade-off Analysis (MANDATORY for significant decisions)

Before adopting ANY design, pattern, or approach:

| Question | Purpose |
|---|---|
| **Why this?** | What specific problem does it solve in THIS context? |
| **Why not the alternative?** | What other options exist and why are they worse HERE? |
| **What do I gain?** | Concrete, measurable benefits |
| **What do I lose?** | Cost, complexity, operational burden |
| **When does it break?** | At what scale/scenario does this choice fail? |

**Anti-patterns:** "I use an index because it is fast" (no context). "I denormalize because it scales" (no trade-off). "I use a UUID PK because it is distributed" (no cost analysis).

---

## 1. Level-by-Level Development

### Rule 1.1 — Every Change is a "Next Level"

Design as if Level N+1 is guaranteed. Before writing SQL, ask:
- "What will Level N+1 probably change in this schema?"
- "Where should that change live?"

If you cannot answer → **do not write DDL yet**.

### Rule 1.2 — One Level = One Migration File / One Commit

Each increment is cohesive. Never mix unrelated schema changes in one migration.

---

## 2. Schema Contract Stability

### Rule 2.1 — Published Schema Is a Contract

Once a table is in production: queries rely on it, applications rely on it, future migrations depend on it. **Breaking changes (rename column, change type) must be versioned with compatibility layer.**

### Rule 2.2 — Evolve Schema, Don't Replace It

```sql
-- Add new nullable column first (backward compatible)
ALTER TABLE orders ADD COLUMN status_v2 VARCHAR(20);

-- Backfill data
UPDATE orders SET status_v2 = ... WHERE status_v2 IS NULL;

-- Then add constraint
ALTER TABLE orders ALTER COLUMN status_v2 SET NOT NULL;

-- Only then drop old column (after all queries migrated)
-- ALTER TABLE orders DROP COLUMN status_old;
```

---

## 3. Data Modeling Principles

- **Model concepts, not flags** — `is_active BOOLEAN` often hides a missing concept (use a `status` enum/lookup table).
- **Enforce constraints at the DB layer** — NOT NULL, FK, UNIQUE, CHECK are first-line defense, not just application responsibility.
- **Prefer value objects** — if a domain value has rules (e.g. email format, positive amount), enforce with CHECK constraint.
- **Normalization is the default** — denormalize only with evidence of performance requirement and documented trade-off.

---

## 4. SOLID Applied to Database Design

| Principle | Database Equivalent |
|---|---|
| **S** — Single Responsibility | A table stores data for one entity/concept only |
| **O** — Open/Closed | Extend schema with new tables/columns, not by modifying existing FK targets |
| **L** — Substitution | Subtypes (e.g. `payment_card`, `payment_bank`) must be usable wherever `payment` is expected |
| **I** — Interface Segregation | Don't force queries to JOIN 10 tables for simple lookups — consider read views |
| **D** — Dependency Inversion | Business logic depends on schema contracts (table names, column names), not on physical storage details |

---

## 5. Query Patterns

- Prefer **simple, readable queries** before complex CTEs/window functions.
- A query must **reduce complexity**, not increase it.
- Before using any advanced SQL feature (LATERAL, recursive CTE, window function): **understand WHY it exists, not just HOW to use it.**
- Always run `EXPLAIN ANALYZE` before declaring a query "optimized".

---

## 6. Verification Rules (Non-Negotiable)

### Rule 6.1 — Verify Before Destructive Operations

```sql
-- ✅ DO: Preview affected rows first
SELECT COUNT(*) FROM orders WHERE status = 'PENDING' AND created_at < '2024-01-01';

-- Then execute
DELETE FROM orders WHERE status = 'PENDING' AND created_at < '2024-01-01';
```

### Rule 6.2 — Preserve Existing Query Behavior

When refactoring schema: all existing queries **must still return the same result**. If they don't → you broke the contract.

### Rule 6.3 — Test Round-Trip for Data Migration

Whenever you move data: verify count before = count after + any intentional filter.

```sql
-- Before migration
SELECT COUNT(*) FROM old_table;  -- e.g. 1000

-- After migration  
SELECT COUNT(*) FROM new_table;  -- must be 1000 (or explain the difference)
```

---

## 7. Index Strategy

- **Never add an index without `EXPLAIN ANALYZE` evidence** — indexes slow down writes.
- Prefer partial indexes for sparse conditions: `CREATE INDEX ON orders (status) WHERE status = 'PENDING'`.
- Document the query that necessitated each non-obvious index in a SQL comment.
- Remove unused indexes — they are write overhead with no read benefit.

---

## 8. Final Checklist

Before committing any schema change:

- [ ] Did I preserve all existing query behavior?
- [ ] Is the change isolated (one concept per migration)?
- [ ] Is there a rollback script for this DDL?
- [ ] Did I add constraints where the domain requires them?
- [ ] Would the next schema change be easy to add?
- [ ] Can I name the trade-off I made with this design choice?

If any answer is "no" → stop and revisit.
