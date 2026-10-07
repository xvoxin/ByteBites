# ByteBites Mobile Architecture Guidelines

This document defines the architectural principles, design patterns, and engineering standards for the **ByteBites (KMP)** mobile client.

---

## 🛠️ Architecture & Design Principles

### 1. Clean Architecture & Nested Gradle Multi-Modularity
The application is strictly partitioned into Clean Architecture layers using nested Gradle modules (without redundant `:core` nesting):
- **`:domain`**: Pure Kotlin Multiplatform code containing business logic, domain models, repository interfaces, and role-based domain components (`Provider`, `Creator`, `Deleter`, `Authenticator`). Zero dependencies on Android, iOS, Ktor, or Compose.
- **`:data:network`**: API communication, Ktor HTTP client, DTOs, JSON serialization, and network error handling.
- **`:data:repository`**: Data integration layer depending on `:domain` and `:data:network` (and future `:data:database`). Implements repository interfaces defined in `:domain` (e.g., `InMemoryRecipeRepository`, `KtorRecipeRepository`) and maps DTOs to domain models.
- **`:shared` (Presentation Layer)**: UI (Compose Multiplatform), ViewModels (MVI), presentation interactors, and view components. Depends on `:domain` and `:data:repository`.
- **`:androidApp` / `:iosApp`**: Platform entry points.

---

### 2. Presentation Layer Standards: Package Structure, Interactors, and Formatting

#### A) Feature Package Structure (`presentation/feature/`)
Every feature in the presentation layer must strictly follow this package layout:
- **`view`**: Compose views (`FeedScreen`, `FeedHeader`, `RecipeListItemCard`). The package name `ui` is strictly forbidden!
- **`viewmodel`**: ViewModels. Each ViewModel class must reside in its own separate file!
  - Abstract ViewModel: `FeedViewModel.kt`
  - Screen ViewModel: `FeedScreenViewModel.kt`
  - Fake ViewModel for previews: `FakeFeedViewModel.kt`
- **`model`**: MVI States, Intents, Effects, UI models, and mappers (`FeedState`, `FeedIntent`, `FeedEffect`, `Recipe`, `RecipeMapper`).
- **`interactor`**: Presentation interactors (`FeedProvider.kt`, `FeedProviderInteractor.kt`).

#### B) Single State Flow Pattern (No `_state` Duplication)
- Concrete ViewModels must not duplicate `_state` and `state` fields.
- Directly override the state property:
  `override val state = MutableStateFlow(FeedState())`
- Mutate state directly using `state.update { ... }` or `state.value = ...`.

#### C) Presentation Interactor & Naming Conventions
- **Domain components MUST NOT be injected directly into ViewModels!**
- ViewModels inject presentation interactor interfaces (e.g., `FeedProvider`).
- Interactor implementations (e.g., `FeedProviderInteractor`) inject domain components (aliased as `import pl.bytebites.mobile.domain.provider.RecipeProvider as DomainRecipeProvider`), invoke domain operations, map domain models to presentation `Recipe` models, and expose them to the ViewModel.

#### D) UI Dimension Constants (`.dp` / `.sp`)
- **Raw numbers (`16.dp`, `24.dp`, `28.sp`) inside `@Composable` functions are strictly forbidden!**
- All UI dimensions must be declared at the top of the file using extension properties:
  ```kotlin
  private val HEADER_ELEVATION = 6.dp
  private val EMBLEM_SIZE = 56.dp
  private val EMBLEM_EMOJI_SIZE = 28.sp
  ```

#### E) Code Formatting Rules
- **No empty lines inside constructor parentheses `(...)`.**
- **Class bodies `{ ... }` must include an empty line after the opening brace and before the closing brace.**
- Separate property declarations and functions with an empty line.

---

### 3. Abstractions & Dependency Injection (Koin)
- **Koin** is the primary DI framework for KMP.
- **Every injected class must implement an interface / abstraction.**
- **The `Impl` class suffix is strictly prohibited!**
  - *Forbidden:* `RecipeRepositoryImpl`
  - *Required:* Descriptive technical names like `InMemoryRecipeRepository`, `FeedProviderInteractor`, `FeedScreenViewModel`.

---

### 4. Solid Unit Testing Strategy 🧪
- Thorough unit test coverage across all layers:
  - **`:domain` unit tests**: Testing role-based domain components (`RecipeCreator`, `RecipeProvider`, `RecipeDeleter`).
  - **`:shared` unit tests**: Testing ViewModels and presentation interactors (using **Turbine** for `StateFlow`).
  - **`:data:repository` & `:data:network` unit tests**: Testing repositories, data mappers, and API clients.

---

### 5. SOLID & Single Responsibility Principle (SRP)
- Classes must fulfill a single, well-defined responsibility matching their name.

---

### 6. Small, Composable UI Components
- Monolithic views are strictly prohibited. Views are decomposed into small, reusable components in the `view` package.

---

## 🤝 Collaboration & AI Workflow

1. **Constructive Partnership & Critical Feedback**:
   - The AI assistant provides objective, architectural analysis and constructive criticism rather than blindly agreeing.
2. **Explicit User Approval Before Implementation**:
   - Before executing code changes, the AI presents a clear plan and **requires explicit user confirmation**.
3. **Docs-as-Code Maintenance**:
   - Architectural decisions are kept updated in `architecture.md` and `/docs`.
