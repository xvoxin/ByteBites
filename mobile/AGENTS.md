# ByteBites - Instructions & Rules for AI Agents

ByteBites is a Kotlin Multiplatform (KMP) cookbook application targeting Android, iOS, and Web (Wasm/JS).
Architectural Stack: Clean Architecture, MVI Presentation, Navigation 3, Koin DI, Compose Multiplatform.

---

## ⛔ MANDATORY OPERATIONAL GUARDRAILS (STRICT COMPLIANCE REQUIRED)

### 1. EXPLICIT USER APPROVAL BEFORE ANY FILE MODIFICATION
- **NEVER write, edit, or delete any file without first presenting the proposed change to the user and receiving explicit approval ("tak").**
- Do NOT perform multiple file edits or multi-file code generation in a single turn without stopping for user approval between steps.

### 2. STRICT TEST-DRIVEN DEVELOPMENT (TDD) WORKFLOW
- **ALWAYS write the test file FIRST (RED 🔴)** before touching any production code.
- Run the test using Gradle to confirm that it fails on assertion (compile errors do NOT count as RED).
- Present **Checkpoint RED** to the user and wait for feedback/approval.
- Only after user approval, write the minimal production code required to make the test pass (**GREEN 🟢**).
- Run tests again, present **Checkpoint GREEN**, and proceed to **REFACTOR 🔵** if needed.

### 3. PACKAGE & CODE CONVENTIONS
- **Screen Package Layout**: All screen features MUST reside under `presentation/screen/<feature_name>/` containing strictly: `view`, `viewmodel`, `model`, `interactor`. The package name `ui` is strictly forbidden.
- **No `Impl` Suffixes**: Never use the `Impl` suffix in class names. Use descriptive technical names (`InMemoryRecipeRepository`, `ProfileProviderInteractor`).
- **Single State Property**: ViewModels override `val state = MutableStateFlow(...)` directly without duplicating `_state` and `state`.
- **UI Dimensions**: Declare all `.dp` and `.sp` values as constants at top of view files or in `Dimen.kt`.
- **Material Icons First**: Always search for official Material Design icons first before creating custom assets. Save vector assets as Android Vector XML (`.xml`) files inside `shared/src/commonMain/composeResources/drawable/`.
- **Test Naming**: Test functions MUST use Kotlin backticks with natural English sentences (e.g. `@Test fun `load user profile`()`).

---

## 🛠️ USEFUL COMMANDS
- Build Android App: `./gradlew :androidApp:assembleDebug`
- Run All JVM Unit Tests: `./gradlew :domain:jvmTest :data:repository:jvmTest :shared:jvmTest`
- Run Web App Dev Server: `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
