# ByteBites Frontend Architecture Specification

This document defines the architectural principles, design patterns, and engineering standards for the **ByteBites (KMP)** frontend client targeting **Android, iOS, and Web (Wasm/JS)**.

---

## 🛠️ Tech Stack & Core Standards

- **Language & Framework**: Kotlin Multiplatform (KMP) with Compose Multiplatform.
- **Target Platforms**: Android, iOS, and Web (Wasm/JS).
- **Dependency Injection**: Koin (`koin-core`, `koin-compose`, `koin-compose-viewmodel`).
- **Presentation Pattern**: Model-View-Intent (MVI) with Abstract ViewModels, Stateless Content, and Presentation Interactors.
- **Concurrency & Reactivity**: Kotlin Coroutines & Flow (`StateFlow`, `Channel`).
- **Testing Tools**: `kotlin.test`, `kotlinx-coroutines-test`, `Turbine`.
- **Assets & Strings**: Compose Multiplatform Resources (`Res.drawable.*`, `Res.string.*`).

---

## 🏛️ Clean Architecture & Module Breakdown

ByteBites enforces Clean Architecture across nested Gradle modules:

```
mobile/
├── domain/            # Pure business logic: domain models, role components, repo interfaces
├── data/
│   ├── network/       # HTTP API, Ktor client, DTOs, network error handling (Future)
│   └── repository/    # Implements domain repositories, in-memory/cache & remote mapping
├── shared/            # Presentation layer: Compose UI, ViewModels, Interactors, Theme
├── androidApp/        # Android application entry point & Manifest
├── iosApp/            # iOS application entry point & Swift bridge
└── webApp/            # WebAssembly (Wasm/JS) entry point & DOM mounting
```

### 1. `:domain`
- Contains pure Kotlin business models (`Recipe`, `User`, `RecipeCategory`).
- Defines repository interfaces (`RecipeRepository`, `AuthRepository`).
- Houses single-responsibility, role-based domain components (`RecipeProvider`, `RecipeDetailsProvider`, `RecipeCreator`, `RecipeDeleter`, `RecipeFavoriteToggler`, `UserProvider`, `UserAuthenticator`).
- **Rule**: Pure Kotlin only. Zero dependencies on Android, iOS, Ktor, Compose, or UI libraries.

### 2. `:data:repository`
- Implements repository interfaces declared in `:domain` (e.g. `InMemoryRecipeRepository`, `InMemoryAuthRepository`).
- Coordinates local in-memory storage and future remote API services.
- Maps remote DTOs into Domain Models.
- **Rule**: Class names reflect strategy/technology (e.g., `InMemoryRecipeRepository`, `KtorRecipeRepository`), **never** using generic `Impl` suffixes.

### 3. `:data:network`
- Manages HTTP communication via Ktor client, serialization, DTOs (`RecipeDto`, `UserDto`), and API exception mappings.

### 4. `:shared` (Presentation Layer)
- Houses all Compose Multiplatform UI components, theme tokens, navigation, and MVI state machines.
- Depends on `:domain` and `:data:repository`.

---

## 🎨 Presentation Layer Standards

### A) Feature Screen Package Structure
Every screen feature must reside under `pl.bytebites.mobile.presentation.screen.<feature_name>/` containing strictly four sub-packages:
- **`view`**: Compose views (`FeedScreen`, `FeedContent`, `FeedHeader`). The package name `ui` is **strictly forbidden**.
- **`viewmodel`**: Abstract ViewModel contract (`FeedViewModel.kt`) and concrete ViewModel (`FeedScreenViewModel.kt`).
- **`model`**: MVI States, Intents, Effects, UI Models, and Mappers (`FeedContract.kt`, `Recipe.kt`, `RecipeMapper.kt`).
- **`interactor`**: Presentation Interactor interface (`FeedProvider.kt`) and implementation (`FeedProviderInteractor.kt`).

### B) Presentation Interactor Pattern
- **Domain components MUST NOT be injected directly into ViewModels!**
- ViewModels inject Presentation Interactor interfaces (e.g., `RecipeDetailProvider`).
- The Interactor implementation (`RecipeDetailProviderInteractor`):
  1. Calls the corresponding Domain Component (`DomainRecipeDetailsProvider`).
  2. Catches domain/data exceptions and maps them to UI-level errors.
  3. Maps domain models to presentation UI models (`RecipeDetail`).
  4. Exposes streams of UI data to the ViewModel.

### C) MVI (Model-View-Intent) & ViewModel Lifecycle
- **Contracts**:
  - `State`: Immutable data class representing complete UI state.
  - `Intent`: Sealed interface representing user actions or events.
  - `Effect`: One-off events (navigation, snackbars) via buffered `Channel` consumed as `Flow`.
- **Single State Flow**: Concrete ViewModels override `override val state = MutableStateFlow(...)` directly without duplicating `_state` and `state`.
- **ViewModel Lifecycle**:
  - In Composables, ViewModels must always be retrieved using `koinViewModel()`, **never** `koinInject()`.
  - Koin must be started at the platform application level (`MainActivity`, `MainViewController`, `main.kt`), **never** inside `@Composable App()`.
- **Screen & Stateless Content**:
  - `*Screen` composable collects state and effects, passing lambdas to a stateless `*Content` composable.
  - `*Content` has dedicated `@Preview` functions for each state (Loading, Success, Empty, Error).

### D) Design Tokens & UI Dimensions
- **Raw numbers (`16.dp`, `24.dp`, `28.sp`) inside `@Composable` functions are strictly forbidden!**
- All UI dimensions must use design tokens from `pl.bytebites.mobile.presentation.theme.Dimen` (e.g. `Dimen.Margin_2`, `Dimen.RadiusLarge`, `Dimen.IconSizeMedium`).
- Screen-specific private constants must be declared at the top of the file as private top-level properties (e.g. `private val AVATAR_SIZE = 64.dp`).
- Colors must be sourced from `CookbookColor.*` or `MaterialTheme.colorScheme`.

### E) Vector Assets & Localization
- **Drawables**: Vector assets must be stored as Android Vector XML (`.xml`) in `shared/src/commonMain/composeResources/drawable/`.
- **Strings**: Zero hardcoded UI strings in composables or ViewModels. All user-facing text, error messages, and content descriptions must be defined in `shared/src/commonMain/composeResources/values/strings.xml` and referenced via `stringResource(Res.string.*)`. Plurals must use `<plurals>`.

---

## 🗺️ Direction of Change (Active Remediation)

1. **Lifecycle & DI**: Migrating ViewModels to `koinViewModel()` and moving `startKoin` out of `@Composable App()`.
2. **Navigation**: Transitioning from custom `NavDisplay` to official multiplatform navigation with persistent backstack.
3. **Robust Error Handling**: Ensuring all Interactors catch repository exceptions and populate `State.error`.
4. **Strings Extraction**: Centralizing all 22 hardcoded Polish strings into Compose Resources.
5. **Screen Modularization**: Breaking monolithic view files (`ProfileScreen.kt`) into composable parts.
