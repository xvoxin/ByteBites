# ByteBites Technical Debt & Roadmap Backlog

This document maintains the backlog of technical debt, architectural remediation, and planned feature milestones for ByteBites.
T-shirt sizing: **S** (< 1 day), **M** (1-3 days), **L** (1-2 weeks), **XL** (> 2 weeks).

---

## 🛠️ Active Architectural Remediation

- [ ] **[S] Koin & ViewModel Lifecycle Fixes** (Remediation Step 2)
  - Replace `koinInject()` with `koinViewModel()` in `AppNavigation.kt`.
  - Move `startKoin` from `@Composable App()` into platform entry points (`MainActivity`, `MainViewController`, `main.kt`).
  - Eliminate memory leaks caused by orphaned collectors and preserve state during configuration changes.

- [ ] **[M] Production-Ready KMP Navigation** (Remediation Step 3)
  - Replace custom `NavDisplay` / `AppNavigator` with official Compose Navigation for KMP.
  - Implement `rememberSaveable` state restoration for navigation backstack.
  - Ensure back navigation works reliably across iOS swipe gesture and Web browser history.
  - Provide an exit/back button on error screens (e.g. `RecipeDetailScreen`).

- [ ] **[M] Robust Error Handling in Interactors & UI** (Remediation Step 4)
  - Add `catch` operators to all Presentation Interactors (`FeedProviderInteractor`, `RecipeDetailProviderInteractor`, `ProfileProviderInteractor`).
  - Map domain exceptions to structured UI error models.
  - Cancel previous in-flight `Job` on repeated ViewModel intents.
  - Render error states and retry buttons in UI composables.

- [ ] **[M] Localization & Strings Extraction** (Remediation Step 5)
  - Extract all 22 hardcoded Polish UI strings from composables and ViewModels to `composeResources/values/strings.xml`.
  - Add support for `<plurals>` (servings: "1 porcja", "2 porcje", "5 porcji").
  - Refactor ViewModels to return string resource identifiers or sealed errors instead of hardcoded strings.

- [ ] **[S] Screen Modularization & UI Polish** (Remediation Step 6)
  - Break monolithic `ProfileScreen.kt` (317 lines) into smaller composables in `presentation/screen/profile/view/`.
  - Extract reusable `BackTopBar` component for navigation headers.
  - Replace `Card(...).clickable` with `Card(onClick = ...)`.
  - Adopt `collectAsStateWithLifecycle()` in composables.
  - Remove dead code (`FakeFeedViewModel`, `FakeRecipeDetailViewModel` in `commonMain`).

- [ ] **[S] Gradle Build & Dependencies Cleanup** (Remediation Step 7)
  - Remove unused Material 2 dependency (`compose.material`).
  - Stabilize Material 3 dependency version (move off experimental alpha).
  - Scope `compose.uiTooling` strictly to `debugImplementation`.
  - Consolidate iOS framework targets to export only `:shared`.
  - Remove obsolete empty directories (`mobile/network`, `mobile/repository`).

- [ ] **[M] Comprehensive Unit Test Suite** (Remediation Step 8)
  - Add dedicated unit tests for Presentation Interactors.
  - Implement tests for missing domain components (`RecipeDeleter`, `RecipeFavoriteToggler`, `RecipeDetailsProvider`).
  - Cover negative edge cases across all ViewModels.
  - Deduplicate `FakeRecipeRepository` into a shared test fixture.

---

## 🗺️ Functional Feature Roadmap

- [ ] **[L] Milestone 4: User Authentication Flow (`feature/auth`)**
  - Login and Registration forms with credential validation.
  - Session token management and persistence.
  - Profile screen connected to active user state.

- [ ] **[L] Milestone 5: Recipe Management & CRUD (`feature/recipe_crud`)**
  - Add Recipe form with dynamic ingredients and step inputs.
  - Recipe deletion with author ownership check.

- [ ] **[M] Milestone 6: Search & Category Filter Chapters**
  - Top search bar with debounced query filtering.
  - Horizontal category chips (Breakfast, Mains, Desserts).

- [ ] **[M] Milestone 7: Favorites System**
  - Heart toggle on recipe cards and detail header.
  - Dedicated "Favorites" tab/filter.

- [ ] **[XL] Milestone 8: Spring Boot Backend & Cloud Run Deployment**
  - Ktor HTTP client in `:data:network` connecting to backend REST API.
  - PostgreSQL database with Liquibase/Flyway migrations.
  - Docker containerization and deployment to Google Cloud Run.

- [ ] **[M] Milestone 9: AI Culinary Assistant (Google AI Studio Free Tier)**
  - Gemini Flash nutritional macro estimation (calories, proteins, carbs, fats).
  - Imagen 3 preview photo generation for recipes without user images.
