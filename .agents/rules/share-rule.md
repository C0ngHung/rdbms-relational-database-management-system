---
name: share-rule
version: 1.0.0
priority: P1
trigger: manual
---

# Shared Rules

> Core essentials are always-on via root `AGENTS.md`. This is the full detailed reference — invoke with `@share-rule`.

## 1. Output Format

- Trả lời bằng **tiếng Việt**.
- Code comments và variable names vẫn viết bằng **English**.

---

## 2. General Behavior

- Always generate clean, complete, production-ready solutions.
- Always provide clear explanations when required.
- Follow SOLID, DRY, KISS, Clean Architecture.
- Assume enterprise-level requirements.
- Ask for clarification when requirements are ambiguous.

---

## 3. Output Rules

- If user asks for code → return the code block. For **non-trivial** changes, still include the brief explanation / risks / how-to-test required by `00-ai-operating-rules.md` (Output Format). "Code only" applies when the user explicitly asks for just the code.
- If explanation is needed → explanation first, code after.
- Automatically refactor when beneficial.
- Code must be complete: no TODOs, no placeholders.
- Exception: TODOs allowed only with JIRA ticket reference format:
  ```
  // TODO(PROJ-123): Implement rate limiting
  ```

---

## 4. Extra Preferences

- Comments inside code should be in English (for international compatibility)
- Prioritize modularity, readability, maintainability.
- Prefer composition over inheritance.
- Write self-documenting code (clear names > comments).

---

## 5. Backend Performance Targets

Define SLOs **per service** and validate them with a load test — do not treat fixed numbers as universal truth. The values below are **example starting targets** to tune against real traffic:

- API response time: P50 < 200ms, P95 < 500ms, P99 < 1000ms
- Database query time: < 100ms (P95)
- Throughput: sized to expected peak load (measure, don't assume)
- Error rate: < 0.1%

Optimization checklist:
- [ ] No `import *`
- [ ] Database queries optimized (no N+1)
- [ ] Proper indexing on frequently queried columns
- [ ] Connection pooling configured
- [ ] Async processing for heavy tasks
