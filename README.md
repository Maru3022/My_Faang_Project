# DevPath AI

AI learning app for Java/Kotlin backend developers. Implementation follows the [Notion product plan](https://app.notion.com/p/3eaed85e8cef8112bce8fce20dc2d748), sections 68–70.

The [full product and architecture plan](docs/notion-product-plan.md) is preserved locally from Notion. Sources were imported from the attached `My_Faang_Project.zip` (archive source commit `8f7a84fd7d204d62f56f6ae57a10d3604125ccd5`) on 2026-10-14. Planned features in that document are not claims of implemented functionality.

## Foundation (first increment)

Java 21, Kotlin, Spring Boot, Spring Modulith, PostgreSQL and Liquibase. Three Gradle modules: `app` (composition and HTTP infrastructure), `shared-kernel` (domain identifiers), `user-profile` (initial public contract). This is a backend foundation, not a complete MVP or implemented login flow.

## Run locally

Requires Java 21 and Docker.

```sh
cp .env.example .env
./gradlew check bootJar
docker compose up --build
```

Set the issuer, JWKS endpoint and audience in `.env` to a managed OIDC provider. Placeholder URLs allow health checks to start but cannot authenticate users. No fake login or development authentication bypass is provided.

- Public health: `GET http://localhost:8080/actuator/health`
- Authenticated smoke check: `GET /api/v1/platform/status`
- Profile: `GET/PATCH /api/v1/me`
- Legal acceptance: `GET /api/v1/legal/acceptances`, `PUT /api/v1/legal/acceptances/{type}`
- OpenAPI: `GET /v3/api-docs` with a bearer token (enabled locally, disabled by default)
- Requests return `X-Request-ID`; failures use Problem Details.

```sh
curl http://localhost:8080/actuator/health
curl -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:8080/api/v1/platform/status
curl -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:8080/api/v1/me
```

`./gradlew test` runs architecture tests without Docker. `./gradlew check` also runs mandatory PostgreSQL Testcontainers integration tests, including migrations, health, authorization and OpenAPI. CI runs both plus container build, a high/critical vulnerability gate and CodeQL. CodeQL requires code scanning availability for the repository. Dependabot tracks dependency updates; production vulnerability review remains a release gate.

The Dockerfile packages the already-built JAR and runs as a non-root user. PostgreSQL is bound to loopback for development. Never reuse the sample database password outside local development.

## Next steps and blockers

See [implementation status](docs/implementation-status.md) and [architecture decisions](docs/adr/001.md). The account slice (identity mapping → `/me` → legal acceptance) is implemented and tested; `UserRegisteredV1` is published durably for future consumers. Next: connect a real managed OIDC provider, then curriculum and assessment. Before staging, choose cloud/region, OIDC provider and secret bindings; IaC and deployment cannot be marked complete yet. No cloud resources or paid services are provisioned by this increment.
