# ByteBites Monorepo Guidelines

ByteBites is a product-minded culinary application featuring a shared Kotlin Multiplatform (KMP) client (Android, iOS, Web) and a Spring Boot (Kotlin) backend service.

---

## 🏛️ Monorepo Structure

```
bytebites/
├── backend/            # Spring Boot (Kotlin) REST API & PostgreSQL
├── mobile/             # Kotlin Multiplatform (Compose Multiplatform) client
├── docs/               # Technical specs, architecture, testing & roadmap
├── requirements.md     # Full functional system specification
└── AGENTS.md           # This root context file
```

---

## ⚡ Core Engineering Principles

1. **Clean Architecture**: Strict separation of concerns (Domain, Data, Presentation) across mobile and backend layers. Business logic resides in pure Kotlin domain modules with zero framework dependencies.
2. **Docs-as-Code**: Architectural decisions, testing guidelines, and technical debt must be kept up to date in `/docs` and `AGENTS.md`.
3. **English Code**: All code, identifiers, comments, documentation, and git commit messages must be in English. User-facing strings must reside in localization resource files (`strings.xml`), never hardcoded in code.
4. **No Hardcoded Secrets**: Never commit credentials, tokens, or private keys. Always use environment variables (`.env`).
5. **Monorepo Modularity**: Maintain strict module boundaries. Never leak backend dependencies into mobile modules or vice-versa.
6. **Zero Underscores in Properties (No `_variable`)**: Never use underscore-prefixed backing properties in any layer or class. Interfaces declare base/read-only types (e.g. `List`, `StateFlow`), and implementations directly override them with the mutable/specific type (e.g. `MutableList`, `MutableStateFlow`).

---

## 📖 Sub-Project Documentation

- **Mobile Client**: See [`mobile/AGENTS.md`](mobile/AGENTS.md) for mobile workflows, Gradle commands, and presentation rules.
- **Backend Service**: See [`backend/architecture.md`](backend/architecture.md) for Spring Boot conventions and database patterns.
- **System Architecture**: Detailed KMP layer and presentation breakdown in [`docs/architecture.md`](docs/architecture.md).
- **Testing Guidelines**: Testing strategy, Turbine usage, and test doubles in [`docs/testing.md`](docs/testing.md).
- **Backlog & Technical Debt**: Tracked debt items and roadmap in [`docs/todo.md`](docs/todo.md).
