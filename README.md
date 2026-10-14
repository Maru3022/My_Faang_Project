# DevPath AI

> **Персональный AI-наставник для разработчиков.** Превращает карьерную цель в ежедневный адаптивный план обучения и подготовки к интервью.

Это не просто чат-бот, не просто курс и не просто roadmap. Это адаптивная система обучения, которая знает цель пользователя, его пробелы и историю прогресса — и каждый день отвечает на вопрос: **«что конкретно мне сегодня делать, чтобы приблизиться к моей цели?»**

---

## 1. Что это за продукт

Пользователь указывает:

- текущий уровень и направление (Backend / Frontend / QA / Data / DevOps / Mobile);
- стек (Java, Kotlin, Python, JavaScript и т.д.);
- опыт и уровень английского;
- карьерную цель и желаемый срок достижения;
- сколько времени готов учиться в день.

На основе этого приложение строит персональный roadmap и ежедневный план. Пример цели: *Junior Kotlin Backend → Middle Backend → международные собеседования*.

## 2. Главная ценность

Проблемы пользователя:

- не понимает, что именно учить;
- прыгает между курсами;
- не видит прогресса;
- не знает, готов ли к собеседованию;
- сложно удерживать дисциплину;
- материалы часто не соответствуют реальному уровню;
- сложно получить качественную обратную связь;
- непонятно, какие пробелы критичны именно для выбранной вакансии.

Решение — единый продукт, который закрывает весь цикл: roadmap, адаптивный AI-наставник, ежедневный план, система повторения, практика, проекты, подготовка к интервью и измеримый прогресс.

## 3. Целевая аудитория

**Основная:** студенты, входящие в IT; Junior, растущие до Middle; Middle, готовящиеся к международным компаниям; разработчики, меняющие стек; готовящиеся к техническим интервью.

**Дополнительная:** выпускники bootcamp, self-taught разработчики, специалисты после перерыва, компании для внутреннего обучения.

## 4. Пользовательские сценарии

- **Первый запуск:** регистрация → выбор цели → диагностический тест → self-assessment → время в день → генерация roadmap → первая неделя обучения.
- **Ежедневное использование:** Home → Today's Plan → изучение темы → задача → мини-квиз → AI анализирует ошибки → прогресс пересчитывается → следующие задания адаптируются.
- **Подготовка к интервью:** выбор роли → вставка вакансии → AI выделяет skills → gap analysis → interview plan → mock interview → обратная связь.

## 5. Функциональные модули

- **Dashboard** — цель, общий прогресс, streak, часы обучения, слабые/сильные темы, задания на день, ближайший milestone, прогноз готовности.
- **Roadmap** — структура *Career Goal → Skills → Topics → Lessons → Practice → Assessment*. Каждая тема имеет статус, уровень владения, приоритет, prerequisites, оценку часов и confidence score.
- **AI Tutor** — объясняет темы, меняет сложность, даёт аналогии, задаёт уточняющие вопросы, проверяет понимание, разбирает код, даёт подсказки и follow-up задания. Режимы: Learn, Explain simply, Deep dive, Quiz me, Practice, Interview mode.
- **Lessons** — цель, объяснение, пример, интерактивный вопрос, мини-квиз, практика, summary, ссылки, следующий шаг.
- **Practice** — multiple choice, short answer, code task, debug task, design question, SQL, архитектурные рассуждения, интервью-вопросы.
- **Coding Playground** — редактор, запуск, тесты, hidden tests, ограничения CPU/RAM/time, sandbox (в MVP можно без полноценного execution engine).
- **Knowledge Assessment** — skill graph с уровнями unknown → learning → familiar → confident → mastered; оценка по квизам, повторениям, сложности, ошибкам и времени ответа.
- **Spaced Repetition** — `last_reviewed_at`, `confidence`, `error_rate`, `next_review_at`, `review_count`.
- **AI Mock Interview** — текстовый и позже голосовой форматы, поведенческие/алгоритмические/backend/system design вопросы + разбор сильных и слабых сторон.
- **Job Gap Analyzer** — вставка вакансии → извлечение skills → сравнение с профилем → gap analysis → план подготовки.

## 6. Онбординг

Не дольше 5–8 минут: кто ты → роль → опыт → стек → цель → срок → время в день → английский → диагностика → генерация roadmap. Не перегружать пользователя вопросами до первой ценности.

## 7. UX/UI

Экраны: Landing, Sign Up/In, Onboarding, Home, Roadmap, Lesson, Practice, AI Tutor, Interview, Progress, Profile, Billing, Settings. Главный экран отвечает на три вопроса: *что делать сегодня, насколько я продвинулся, что мешает двигаться быстрее.*

## 8. MVP

**Обязательно:** регистрация; onboarding; выбор направления; персональный roadmap; ежедневный план; AI Tutor; lessons; quizzes; progress tracking; streak; базовая оценка навыков; простой interview mode; подписка/paywall; admin panel; аналитика; форма обратной связи.

**Не в первой версии:** соцсеть, marketplace, живые менторы, нативные мобильные приложения, сложный code execution, enterprise, огромная геймификация. Начинаем с одной сильной вертикали — **Java/Kotlin Backend**.

## 9. После MVP (v1.0)

Полноценный coding sandbox; голосовые интервью; vacancy и CV analyzer; симулятор system design; адаптивная сложность; spaced repetition; достижения; еженедельные отчёты; AI learning memory; продвинутая аналитика; реферальная система.

## 10. Монетизация

**Free:** ограниченный roadmap, несколько AI-запросов в день, базовые квизы, ограниченный прогресс.

**Pro ($10–20/мес.):** расширенный AI Tutor, интервью, gap analyzer, глубокая аналитика, spaced repetition, увеличенные лимиты.

**Позже:** Team / University / Company планы. Цену валидируем через реальные продажи и willingness-to-pay интервью.

## 11. Метрики

- **Acquisition:** конверсия лендинга, signup rate, источник, CAC.
- **Activation:** завершил onboarding и выполнил первое задание.
- **Engagement:** DAU/WAU, lessons/week, попытки практики, AI-сессии, streak.
- **Retention:** D1, D7, D30.
- **Learning:** прирост навыков, повторяющиеся ошибки, completion, assessment delta.
- **Revenue:** free→paid конверсия, MRR, ARPU, churn, LTV.

## 12. Архитектура и технический стек

**Решено: Modular Monolith + Hexagonal/Clean Architecture + событийное взаимодействие внутри приложения + stateless backend.** Цель — быстро выпустить MVP, не строя преждевременные микросервисы, но сохранить жёсткие границы модулей и возможность позже выносить части без переписывания.

Принципы:

- один deployable backend, внутри — независимые бизнес-модули;
- модуль владеет своей моделью, таблицами, use cases и repository-интерфейсами;
- модуль не читает JPA-сущности/repository другого модуля;
- синхронное взаимодействие — только через публичный API/facade, асинхронное — через versioned domain-события;
- общий shared-kernel минимален (идентификаторы, базовые value objects, ошибки, clock);
- одна база PostgreSQL, владение таблицами разделено по схемам;
- Redis — только ускоритель, не источник истины;
- stateless на уровне обработки HTTP;
- все внешние провайдеры подключаются через ports/adapters;
- внешние side-effect операции идемпотентны;
- изменения БД — только versioned-миграции (Liquibase);
- API и события — backward-compatible контракты.

**Стек:** Kotlin · Spring Boot · Spring Security · Spring Modulith · PostgreSQL · Redis · Liquibase · REST/JSON · SSE (для стриминга AI) · OpenAPI 3.x · Testcontainers · OpenTelemetry. Frontend (позже): Next.js + React + TypeScript, PWA; мобильные — React Native.

## 13. Доменные сущности

User, Profile, CareerGoal, Skill, SkillLevel, Roadmap, RoadmapItem, Lesson, Quiz, Question, Answer, PracticeTask, Attempt, Progress, DailyPlan, AIConversation, InterviewSession, InterviewQuestion, Feedback, Subscription, Payment, AuditEvent.

## 14. AI-архитектура

LLM — не единственный источник истины. Используем curated-контент, versioned-промпты, структурированный JSON-выход, валидацию, детерминированные бизнес-правила, RAG только там, где нужен, ссылки на источники, moderation и лимиты токенов. Отдельный слой абстракции над LLM-провайдерами: retries, rate limits, cost tracking, fallback-модель, evaluation pipeline.

Оценка AI: фактическая корректность, галлюцинации, релевантность, сложность, консистентность, качество ответа, задержка, стоимость. Создаётся regression-датасет из реальных вопросов (100+).

## 15. Безопасность, privacy, платёж, email

- **Безопасность:** хэширование паролей/OAuth, email-verification, JWT, rate limiting, CSRF, CORS, валидация входных данных, secrets manager, шифрование, least privilege, audit logs, dependency scanning, SAST, бэкапы и проверка восстановления, удаление аккаунта и экспорт данных. Для code execution — отдельный sandbox без доступа к файловой системе хоста, ограничения CPU/RAM/time, изоляция процессов.
- **Privacy/Legal:** Privacy Policy, Terms of Service, Cookie Policy, процедуры удаления и экспорта данных, retention policy, consent management. Не отправлять персональные данные в LLM без необходимости.
- **Payments:** checkout, жизненный цикл подписки, upgrade/downgrade/cancellation, обработка ошибок оплаты, grace period, инвойсы, webhook verification, идемпотентность, refund.
- **Email:** verification, welcome, password reset, payment success/failed, subscription canceled, weekly learning report, inactivity reminder.

## 16. Наблюдаемость, тестирование, CI/CD, бэкапы

- **Observability:** structured logging, метрики, трейсы, error tracking, дашборды, алерты, задержка и стоимость AI, failed payments, аномалии signup, здоровье БД.
- **Testing:** unit (прогресс, расчёт навыков, подписка, roadmap, spaced repetition); integration (БД, Redis, моки AI-адаптеров, webhook, email); API (contract, auth, permissions, validation); E2E (регистрация, onboarding, первый урок, квиз, AI Tutor, оплата, отмена, удаление аккаунта); AI-тесты (golden dataset).
- **CI/CD:** lint → compile → unit → integration → security scan → frontend build → Docker → staging → smoke → production → health check.
- **Backup/DR:** частота бэкапов, retention, процедура восстановления, RPO/RTO; восстановление реально проверяется.
- **Feature flags:** для новых AI-моделей, interview mode, экспериментов с ценами, beta-функций.
- **Anti-abuse:** защита от ботов, prompt abuse, token farming, shared accounts, scraping, excessive code execution, payment fraud.

## 17. План разработки

- **Phase 0 — Discovery:** 20–50 интервью, одна аудитория и одна карьерная цель, лендинг, waitlist.
- **Phase 1 — Foundation:** репозиторий, backend, frontend, БД, auth, CI/CD, staging.
- **Phase 2 — Learning Core:** onboarding, roadmap, lessons, quiz, progress.
- **Phase 3 — AI:** AI Tutor, structured output, промпты, оценки, контроль стоимости.
- **Phase 4 — Product:** ежедневный план, streak, interview MVP, аналитика, feedback.
- **Phase 5 — Monetization:** цены, платежи, подписки, лимиты, billing portal.
- **Phase 6 — Hardening:** security review, load tests, бэкапы, мониторинг, legal pages, E2E.
- **Phase 7 — Beta:** 30–100 пользователей, фиксы onboarding, замер retention, валидация цен.
- **Phase 8 — Release:** production, лендинг, оплата, поддержка, launch campaign, дашборды.

## 18. Definition of Done для публичного релиза

Новый пользователь регистрируется сам; auth/email стабильны; onboarding понятен; roadmap генерируется; урок проходится; квиз работает; прогресс сохраняется; AI Tutor отвечает стабильно и валидируется; критичные кейсы галлюцинаций протестированы; подписка покупается и отменяется; webhook идемпотентны; ошибки пользователя не приводят к 500; есть rate limits, логи, метрики, алерты, бэкапы с проверенным восстановлением; опубликованы Privacy Policy и Terms; работает удаление аккаунта; есть поддержка и аналитика; критические E2E зелёные; mobile layout usable; нет известных critical/high security issues; есть rollback plan.

## 19. Главные риски и их снижение

- **Слишком широкий продукт** → начинаем с Java/Kotlin Backend.
- **AI отвечает неправильно** → curated-контент + валидация + evals.
- **Пользователи не возвращаются** → discovery + retention-first дизайн.
- **Высокая стоимость AI** → model routing, кэширование, лимиты, дешёвые модели для простых задач.
- **Слишком долго делать** → MVP доказывает learning loop, а не содержит все идеи.
- **Нет платёжеспособного спроса** → pricing-интервью и pre-sale до долгой разработки.

## 20. Главная продуктовая гипотеза

> Если разработчику дать персональный roadmap, ежедневный план, AI feedback и измеримый skill progress в одном продукте, он будет учиться стабильнее и быстрее, чем при самостоятельном использовании разрозненных курсов, YouTube и AI-чата.

Именно эту гипотезу нужно доказать первой.

---

## Текущее состояние реализации

⚠️ Важно: ниже — **что уже реально реализовано**. Всё, что выше, — продуктовая визия и план; не все описанные функции реализованы.

Проект находится на раннем этапе. Готов фундамент и первый бизнес-срез:

- **Фундамент:** многомодульная Kotlin-сборка, Spring Modulith (проверка границ модулей), PostgreSQL + Liquibase, OIDC JWT-аутентификация (resource server), корреляция запросов, Problem Details, OpenAPI, Docker, локальный Compose, CI.
- **Учётный срез (account slice):**
  - модуль `identity-access` — маппинг OIDC `(issuer, subject)` → внутренний `UserId` (find-or-create, идемпотентно) и публикация durable-события `UserRegisteredV1`;
  - модуль `user-profile` — JDBC-персистентность за портом, `UserProfileFacade`, оптимистичная блокировка по `version`;
  - модуль `legal` — версионирование `PRIVACY_POLICY` / `TERMS_OF_SERVICE` и фиксация согласий пользователя;
  - `shared-kernel` — `DomainException` / `DomainErrorStatus`;
  - API: `GET/PATCH /api/v1/me`, `GET/PUT /api/v1/legal/acceptances`.

**Подробности, решения и следующий шаг** — в [`docs/implementation-status.md`](docs/implementation-status.md) и решениях [`docs/adr/`](docs/adr/).

## Как запустить локально

Требуется Java 21 и Docker.

```sh
# 1. Задать переменные окружения (пример значений)
export DATABASE_PASSWORD=local-dev-only
export OIDC_ISSUER_URI=https://identity.example.test
export OIDC_JWK_SET_URI=https://identity.example.test/.well-known/jwks.json

# 2. Собрать и запустить
./gradlew check bootJar
docker compose up --build
```

Окружение задаётся переменными (`DATABASE_URL`, `DATABASE_USER`, `DATABASE_PASSWORD`, `OIDC_ISSUER_URI`, `OIDC_JWK_SET_URI`, `OIDC_AUDIENCE`, `OPENAPI_ENABLED`). Задайте `issuer`, `jwks` и `audience` под ваш managed OIDC-провайдер — placeholder-значения позволяют поднять health check, но не аутентифицировать пользователей. Намеренно нет фейкового логина или dev-обхода аутентификации.

Полезные endpoints (все кроме health требуют bearer-токен):

- `GET /actuator/health` — публичный health check;
- `GET /api/v1/platform/status` — проверка аутентификации;
- `GET|PATCH /api/v1/me` — профиль пользователя;
- `GET|PUT /api/v1/legal/acceptances` — юридические согласия;
- `GET /v3/api-docs` — OpenAPI (локально, если включено).

```sh
curl http://localhost:8080/actuator/health
curl -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:8080/api/v1/me
```

Тесты: `./gradlew test` — архитектурные (без Docker); `./gradlew check` — ещё и PostgreSQL-интеграционные (Testcontainers).

## Структура репозитория

```
app/                — композиционный корень, HTTP-инфраструктура, контроллеры, DTO
shared-kernel/      — общие идентификаторы и примитивы (UserId, ошибки)
identity-access/    — маппинг внешней OIDC-идентичности на внутреннего пользователя
user-profile/       — профиль пользователя, его цель и настройки
legal/              — юридические документы и согласия
docs/               — статус реализации и архитектурные решения (ADR)
```

## Документы

- [`docs/implementation-status.md`](docs/implementation-status.md) — что реализовано и что дальше.
- [`docs/adr/`](docs/adr/) — архитектурные решения (ADR-001…006).