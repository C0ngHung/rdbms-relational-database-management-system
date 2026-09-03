---
name: 00-ai-operating-rules
version: 1.0.0
priority: P0
trigger: manual
---

# 00 — AI Operating Rules

> Core essentials are always-on via root `AGENTS.md`. This is the full detailed reference — invoke with `@00-ai-operating-rules`.

These rules define how the AI assistant must behave when generating, reviewing, or modifying backend code in any project.

---

## Core Behavior

- Do not invent project structure, package names, dependencies, entities, DTOs, or database tables.
- Prefer minimal safe changes over large rewrites.
- Do not change public API contracts unless explicitly requested.
- Do not add dependencies unless necessary; explain why if you do.
- Always respect existing coding style in the project.
- Do not remove existing validation, transaction, logging, or test logic without explaining the reason.
- Do not generate code that only works for demo but is unsafe for production.
- Do not create a new class if a suitable one already exists in shared libraries or the project's own packages.
- Do not mix DTOs between modules or services — each owns its own DTO classes.

---

## Before Generating Code

Before generating or modifying backend code, identify:

- Which module/service is affected
- Affected layer (controller, service, repository, client, listener, config)
- Transaction boundary (`@Transactional` scope and owner)
- Validation rule (Bean Validation annotation or manual guard)
- Exception behavior (which exception type and HTTP status to return)
- Logging/audit impact (what log lines are added, removed, or changed)
- Required tests

For banking/payment/money-related code, additionally check:

- **Idempotency** — is there a DB unique constraint + application-level guard?
- **Concurrency** — is balance update protected by atomic SQL (not read-modify-write in Java)?
- **Transaction status** — is status transition well-defined and enforced?
- **Rollback/compensation** — what happens when a downstream step fails mid-flow?
- **Audit trail** — is every money movement recorded in a ledger/entry table?
- **Reconciliation path** — can the caller query the result later?

---

## Output Format

When producing code, always include:

- Full file path relative to the project root
- Focused code patch or full file when needed
- Explanation of what changed and why
- Why the change is safe
- Remaining risks or edge cases
- How to test the change

---

## Assumption Rules

- If required context is missing, ask for the minimum necessary files before writing code.
- If making assumptions, state them explicitly at the top of your response.
- Do not silently assume database schema, security model, or business rules.
- Do not silently change naming conventions.
- Do not assume which schema or database a table belongs to — ask if not clear.

---

## Refactoring Rules

- Prefer small, incremental refactoring steps over large rewrites.
- Do not refactor code unrelated to the current task.
- Do not introduce abstraction before there are at least two real use cases.
- Preserve behavior unless the user explicitly asks to change behavior.
- For risky refactoring, explain rollback strategy before making changes.
- Do not extract shared code into a common module unless it is genuinely needed by 2+ consumers.

---

## Safety Rules

Never:

- Swallow exceptions silently without logging
- Return a success response when the operation is not confirmed
- Retry non-idempotent operations blindly (especially money debit or external posting)
- Log sensitive data (account numbers, balances, customer PII)
- Bypass authorization checks
- Expose internal exception stack traces or database error messages to API clients
- Use `float` or `double` for monetary values — always use `BigDecimal`
- Acknowledge a message queue delivery before confirming the business operation succeeded
- Requeue a message that will always fail — it causes infinite retry loops
