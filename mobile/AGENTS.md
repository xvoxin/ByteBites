# ByteBites - Instructions & Rules for AI Agents

ByteBites is a Kotlin Multiplatform (KMP) cookbook application targeting Android, iOS, and Web (Wasm/JS).
Architectural Stack: Clean Architecture, MVI Presentation, Navigation 3, Koin DI, Compose Multiplatform.

---

## ⛔ MANDATORY OPERATIONAL WORKFLOW

### 1. STEP-LEVEL PLAN APPROVAL
- **Before starting any new feature or refactoring step, present the full step plan first**:
  - List of TDD behaviors / test cases to drive in the test file.
  - List of files that will be created or modified during the step.
- Wait for user approval on the plan ("Działamy z...").
- Do NOT ask for approval on every individual file creation within an approved step.

### 2. STRICT TEST-DRIVEN DEVELOPMENT (TDD) WORKFLOW
- **ALWAYS write the test file FIRST (RED 🔴)** before writing production code.
- Run the test using Gradle to confirm that it fails on assertion (compile errors do NOT count as RED).
- Present **Checkpoint RED** to the user.
- Write the minimal production code required to make the test pass (**GREEN 🟢**).
- Run tests again, present **Checkpoint GREEN**, and proceed to **REFACTOR 🔵** if needed.

### 3. STRICT LAYER-FIRST (INSIDE-OUT) ORDER FOR FEATURES
- **ALWAYS build new features from the deepest layer outwards**:
  1. **`:domain`** (Domain Models, Role-based Components & Domain Unit Tests)
  2. **`:data:repository`** (Repository Implementations & Data Unit Tests)
  3. **`:shared` Interactor** (`presentation/screen/<feature>/interactor/` & Interactor Unit Tests)
  4. **`:shared` ViewModel** (`presentation/screen/<feature>/viewmodel/` & ViewModel Unit Tests)
  5. **`:shared` View** (`presentation/screen/<feature>/view/` Composable UI)
- NEVER start a new feature or sub-feature by editing or creating UI views first!

### 4. PACKAGE & CODE CONVENTIONS
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
