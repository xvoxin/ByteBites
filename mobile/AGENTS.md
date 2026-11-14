# ByteBites Mobile (KMP) – Agent Instructions & Operational Rules

ByteBites Mobile is a Kotlin Multiplatform (KMP) cookbook client targeting Android, iOS, and Web (Wasm/JS).
Tech Stack: Clean Architecture, MVI Presentation, Compose Multiplatform, Koin DI, Turbine, Coroutines.

Detailed Specs: [`docs/architecture.md`](../docs/architecture.md) | [`docs/testing.md`](../docs/testing.md) | [`docs/todo.md`](../docs/todo.md)

---

## ⛔ MANDATORY OPERATIONAL RULES

### 1. Step-Level Plan Approval
- **Before starting any new feature or refactoring step, present the full step plan first**:
  - List of TDD behaviors / test cases to drive in the test file.
  - List of files that will be created or modified during the step.
- Wait for explicit user confirmation before writing production or test code.

### 2. Strict Test-Driven Development (TDD)
- **ALWAYS write the test file FIRST (RED 🔴)** before writing production code.
- Run the test using Gradle to confirm that it fails on assertion (compile errors do NOT count as RED).
- Write the minimal production code required to make the test pass (**GREEN 🟢**).
- Refactor if needed (**REFACTOR 🔵**).
- **Test Naming**: Use Kotlin backticks with natural English sentences (`@Test fun \`load recipe detail and update state\`()`).
- **Test Doubles**: Use shared KMP Fakes implementing interfaces. Do NOT use MockK in multiplatform tests.

### 3. Strict Layer-First (Inside-Out) Order
1. **`:domain`**: Business Models, Role-based Components & Domain Unit Tests.
2. **`:data:repository`**: Repository Implementations & Data Unit Tests.
3. **`:shared` Interactor**: `presentation/screen/<feature>/interactor/` & Interactor Unit Tests.
4. **`:shared` ViewModel**: `presentation/screen/<feature>/viewmodel/` & ViewModel Unit Tests.
5. **`:shared` View**: `presentation/screen/<feature>/view/` Composable UI & Previews.
- NEVER create UI views before the domain, repository, and presentation logic layers are implemented and tested!

### 4. Architectural & Code Standards
- **Screen Package Layout**: Strictly `pl.bytebites.mobile.presentation.screen.<feature>/` containing `view`, `viewmodel`, `model`, `interactor`. The package name `ui` is strictly forbidden.
- **Presentation Interactor Rule**: Domain components are NEVER injected directly into ViewModels. ViewModels inject Presentation Interactors, which catch domain exceptions, map domain models to UI models, and manage error states.
- **Zero Underscores in Properties (Global Rule)**: Absolutely no underscore-prefixed properties (e.g. `_state`, `_errorDialog`) in any class or layer. Interfaces define read-only contracts (`StateFlow`, `List`), and implementations directly override with the concrete/mutable type (`override val state = MutableStateFlow(...)`, `override val errorDialog = MutableStateFlow(...)`).
- **Koin & ViewModel Lifecycle**:
  - Inside Composables, always acquire ViewModels via `koinViewModel()`, NEVER `koinInject()`.
  - Injected dependencies must implement an interface. Never use the `Impl` suffix (e.g. `InMemoryRecipeRepository`, `ProfileProviderInteractor`).
- **Localization & Zero Hardcoded Strings**: All UI text, button labels, content descriptions, and error messages MUST be defined in `shared/src/commonMain/composeResources/values/strings.xml` (`Res.string.*`). Zero hardcoded text in Composables or ViewModels! Use `<plurals>` for countable items.
- **UI Tokens & Assets**: Use `Dimen.*` constants and `CookbookColor.*`. Raw `.dp`/`.sp` numbers inside `@Composable` functions are forbidden. Save vector assets as Android Vector XML (`.xml`) in `shared/src/commonMain/composeResources/drawable/`.

---

## 🛠️ USEFUL COMMANDS
- Run All JVM Unit Tests: `./gradlew :domain:jvmTest :data:repository:jvmTest :shared:jvmTest`
- Run Single Test Suite: `./gradlew :shared:jvmTest --tests "pl.bytebites.mobile.presentation.screen.feed.FeedScreenViewModelTest"`
- Build Android App: `./gradlew :androidApp:assembleDebug`
- Run Web App Dev Server: `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
