# ByteBites Testing Guidelines

This document establishes the testing strategy, test conventions, and tooling standards for the **ByteBites** codebase.

---

## 🧪 Testing Pyramid & Scope

ByteBites enforces automated unit testing across all layers:

| Layer | Target Module | What We Test | Tooling |
|---|---|---|---|
| **Domain** | `:domain:jvmTest` | Business validation, calculations, role-based component logic (`RecipeCreator`, `RecipeProvider`, `UserAuthenticator`). | `kotlin.test`, `runTest`, `Turbine` |
| **Data Repository** | `:data:repository:jvmTest` | In-memory and cache state mutations, Flow emissions, DTO-to-Domain mappers. | `kotlin.test`, `runTest`, `Turbine` |
| **Presentation Interactor** | `:shared:jvmTest` | Exception catching, error mapping to UI states, Domain-to-Presentation model mapping. | `kotlin.test`, `runTest`, `Turbine` |
| **Presentation ViewModel** | `:shared:jvmTest` | MVI intent processing, StateFlow reductions, one-off Effect channel emissions. | `kotlin.test`, `runTest`, `Turbine`, `StandardTestDispatcher` |
| **Compose UI** | `:shared` (Previews) | Visual rendering of each UI state (Loading, Success, Empty, Error) via `@Preview`. | Compose Multiplatform Previews |

---

## 📜 Test Conventions & Standards

### 1. Test Function Naming (Kotlin Backticks)
- Test function names must describe the expected behavior in natural English sentences enclosed in backticks:
  ```kotlin
  @Test
  fun `load recipe detail and update state with success`() = runTest { ... }

  @Test
  fun `emit error state when recipe repository throws exception`() = runTest { ... }
  ```
- **`snake_case` (e.g. `test_load_recipe`) is strictly forbidden!**

### 2. Test Doubles Strategy in KMP
- **Use shared Fakes, NOT MockK in common tests:**
  - MockK relies on JVM bytecode manipulation and is incompatible with Kotlin/Wasm and Kotlin/Native (iOS).
  - Test doubles are implemented as lightweight, stateful Fake classes implementing the respective interface (e.g., `FakeRecipeRepository : RecipeRepository`, `FakeUserProvider : UserProvider`).
  - Store reusable test fakes in shared test sources (`src/commonTest/kotlin/...`) instead of duplicating them across multiple test suites.

### 3. Coroutine & Turbine Testing Pattern
- Every async test must use `runTest`.
- In ViewModel tests, install and tear down a `StandardTestDispatcher`:
  ```kotlin
  private val testDispatcher = StandardTestDispatcher()

  @BeforeTest
  fun setUp() {
      Dispatchers.setMain(testDispatcher)
  }

  @AfterTest
  fun tearDown() {
      Dispatchers.resetMain()
  }
  ```
- Test `StateFlow` and `Channel` flows using CashApp `Turbine`:
  ```kotlin
  viewModel.state.test {
      viewModel.onIntent(FeedIntent.RecipeClicked("1"))
      testDispatcher.scheduler.advanceUntilIdle()

      val state = expectMostRecentItem()
      assertFalse(state.isLoading)
      assertEquals("1", state.recipes.first().id)
  }
  ```

### 4. Mandatory Edge Case Coverage
Never test only the happy path. Every test suite must cover:
- Empty / null data states (e.g., no recipes available).
- Error paths (network failure, invalid credentials, missing entity).
- Boundary conditions (empty strings, minimum character lengths).
- Intent cancellation (triggering a new load while a previous one is in-flight).

---

## 🛠️ Test Execution Commands

```bash
# Run all JVM unit tests across all mobile modules:
./gradlew :domain:jvmTest :data:repository:jvmTest :shared:jvmTest

# Run tests for a specific module:
./gradlew :domain:jvmTest
./gradlew :data:repository:jvmTest
./gradlew :shared:jvmTest

# Run a single targeted test class:
./gradlew :shared:jvmTest --tests "pl.bytebites.mobile.presentation.screen.feed.FeedScreenViewModelTest"
```
