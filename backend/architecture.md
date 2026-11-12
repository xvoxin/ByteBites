# ByteBites Backend Architecture Guidelines

This document defines the architectural principles, design patterns, and engineering standards for the **ByteBites** Spring Boot (Kotlin) backend service.

---

## 🛠️ Architecture & Design Principles

### 1. Clean Architecture Layering
The backend is strictly partitioned into Clean Architecture layers to enforce a clean separation of concerns:
- **`domain`**: Pure Kotlin code containing business models (`Recipe`, `User`), repository interfaces (`RecipeRepository`), and role-based domain components (`RecipeProvider`, `RecipeCreator`, `RecipeDeleter`, `UserAuthenticator`). Zero Spring annotations or database framework dependencies allowed in pure domain models.
- **`infrastructure` / `data`**: Data access and external integration layer. Contains database entities (JPA `@Entity`), Spring Data Repositories (`SpringDataJpaRecipeRepository`), and repository implementations (`PostgresRecipeRepository`, `InMemoryRecipeRepository`). Maps entities to domain models.
- **`web` / `api`**: Presentation and HTTP API layer. Contains REST Controllers (`HealthController`, `RecipeController`), Request/Response DTOs (`HealthStatusDto`), DTO Mappers, and `@RestControllerAdvice` exception handlers.
- **`config`**: Spring Boot configuration classes and bean definitions (`DatabaseConfig`, `SecurityConfig`).

---

### 2. Role-Based Domain Components & Single Responsibility Principle (SRP)
- **Monolithic `*Service` classes (e.g., `RecipeService`) are strictly forbidden!**
- Business logic must be split into focused, single-responsibility role-based components matching their specific operation:
  - `RecipeProvider`: Querying and retrieving recipes.
  - `RecipeCreator`: Validating and creating new recipes.
  - `RecipeDeleter`: Verifying ownership and deleting recipes.
  - `UserAuthenticator`: Handling user authentication and token validation.

---

### 3. Abstractions & No `Impl` Suffix Rule
- Every domain component or repository interface must be backed by an explicit interface abstraction.
- **The `Impl` class suffix is strictly prohibited!**
  - *Forbidden:* `RecipeRepositoryImpl`, `RecipeServiceImpl`
  - *Required:* Technical and descriptive implementation names (e.g., `PostgresRecipeRepository`, `InMemoryRecipeRepository`, `DefaultHealthStatusProvider`, `DefaultRecipeCreator`).

---

### 4. Code Formatting & Kotlin Syntax Rules
- **Single-expression functions returning immediately must use expression body (`=`) syntax:**
  ```kotlin
  override suspend fun getHealthStatus(): HealthStatus =
      HealthStatus(status = "UP", timestamp = Instant.now().toString())
  ```
- **Class bodies `{ ... }` must include an empty line after the opening brace and before the closing brace:**
  ```kotlin
  class DefaultHealthStatusProvider : HealthStatusProvider {

      override suspend fun getHealthStatus(): HealthStatus = ...

  }
  ```
- All code, comments, documentation, and commit messages must be written in English.

---

### 5. SOLID Unit Testing Strategy & Kotlin Backtick Naming 🧪
- Built TDD-first (RED → GREEN → REFACTOR).
- **Test Function Naming Convention**:
  - Use Kotlin's backtick syntax to write natural English sentences describing test actions:
    ```kotlin
    @Test
    fun `return health status UP when service is healthy`() = runTest { ... }
    ```
  - `snake_case` (e.g. `test_function_name`) is strictly forbidden.
- Testing frameworks: `kotlin.test`, `MockK`, Spring Boot Test.

---

### 6. Zero Hardcoded Secrets & Environment Configuration
- Database credentials and API secrets must **never** be hardcoded or committed to version control.
- Configuration parameters are supplied via environment variables (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`), backed locally by `.env` (git-ignored) and `.env.example` template.
