# Implementation status

Source: [Notion plan](https://app.notion.com/p/3eaed85e8cef8112bce8fce20dc2d748), sections 68–70.

## Account slice (2026-10-14)

Delivered the first business vertical slice on top of the foundation.

### Modules
- `identity-access`: OIDC `(issuer, subject)` → internal `UserId`, find-or-create, idempotent. Publishes `UserRegisteredV1` on first mapping.
- `user-profile`: JDBC persistence behind a port, `UserProfileFacade` (root-package contract), optimistic concurrency on `version`.
- `legal`: `PRIVACY_POLICY` / `TERMS_OF_SERVICE` versioning and per-user acceptance.
- `shared-kernel`: added `DomainException` and `DomainErrorStatus`.
- `app`: `CurrentUser` resolver (identity from verified JWT claims), `MeController`, `LegalController`, REST DTOs, `DomainException` → Problem Details mapping.

### API
- `GET /api/v1/me` — returns the authenticated user's profile, auto-registering on first sight.
- `PATCH /api/v1/me` — update display name, locale, timezone, daily minutes (validated; 400 on invalid timezone/locale, 409 on concurrent modification).
- `GET /api/v1/legal/acceptances` — current and accepted document versions.
- `PUT /api/v1/legal/acceptances/{type}` — record acceptance of the current version (409 for a stale version).

### Schema (migration 002)
- `identity.identity_mapping (provider, subject)` primary key + `user_id` index.
- `legal.document_version` (seeded v1 for both documents) and `legal.user_acceptance`.

### Conventions enforced in CI
- `./gradlew check` runs the Spring Modulith architecture test (module boundaries, cycles, internal-access rules) and 12 PostgreSQL integration tests. `bootJar` builds the runnable image.

### Decisions
- Module public contracts live in each module's root package (Modulith exposes the root package by default).
- Profile creation is synchronous (`ensureProfile`, idempotent) in the request path; the `UserRegisteredV1` event is still published durably for future consumers.
- `kotlin-allopen` (Spring) applied to all subprojects so CGLIB can proxy `@Service`/`@Repository`/`@Component` beans.
- See [ADR-006](adr/006.md).

## Phase 1 / section 69

Implemented: three-module Kotlin build, Spring Modulith boundary verification, PostgreSQL Testcontainer, Liquibase foundation migration, public health and authenticated smoke endpoint, request correlation ID, Problem Details, OpenAPI, Dockerfile, local Compose, CI and ADR-001–005.

Request correlation is implemented; distributed tracing export is not configured. Profile persistence schema exists, but account mapping/profile use cases are phase 2. Durable event storage exists; business events and replay tests arrive with business features.

Pending: dev/stage IaC, identity provider setup, staging deployment and a production-like authenticated smoke test. Phase 1 exit criteria are therefore not yet met. Phase 0 business/legal/account ownership decisions remain with the product owner.

## Next vertical slices

1. Select a managed OIDC provider and confirm audience/revocation policy; connect real login.
2. Curriculum: versioned curated skills/topics/lessons behind a port (still no on-the-fly AI content).
3. Assessment: diagnostic quiz, attempts and skill-score input.
4. Progress: skill graph and mastery derived from assessment events.
5. AI orchestration: LLM abstraction, prompt versioning, structured output, quotas and cost tracking.

## Validation of this increment

- `./gradlew check bootJar`: passed (1 architecture test, 12 PostgreSQL integration tests covering identity mapping idempotency, `/me` auto-registration, profile update/validation, legal acceptance and rejection).
- Docker Compose image build and startup: covered in the CI pipeline.
- GitHub Actions security scans are configured; local tests do not establish a green remote CI or a production release gate.