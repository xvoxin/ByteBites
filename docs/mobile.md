# ByteBites Mobile Architecture & Specifications

This document defines the mobile architecture, technology choices, engineering standards, and AI integration strategy for the **ByteBites (KMP)** application.

---

## 🛠️ Tech Stack & Core Standards

- **Framework**: Kotlin Multiplatform (KMP) with Compose Multiplatform for Android & iOS.
- **Dependency Injection**: Koin.
- **Presentation Pattern**: Model-View-Intent (MVI) with Abstract ViewModels and Presentation Interactors.
- **Testing Tools**: `kotlin.test`, `kotlinx-coroutines-test`, `Turbine` (for `StateFlow` testing).

---

## 🏛️ Architecture & Module Breakdown

ByteBites strictly enforces **Clean Architecture** using a dedicated nested Multi-module Gradle structure:

1. **`:domain`**:
   - Pure Kotlin Multiplatform code containing Domain Models (`Recipe`, `User`, `RecipeCategory`), Repository Interfaces, and Role-based Domain Components (`RecipeProvider`, `RecipeDetailsProvider`, `RecipeCreator`, `RecipeDeleter`, `RecipeFavoriteToggler`, `UserProvider`, `UserAuthenticator`).
2. **`:data:network`**:
   - Manages HTTP API communication, Ktor client setup, DTO models (`RecipeDto`, `UserDto`), JSON serialization, and network error handling.
3. **`:data:repository`**:
   - Implements repository interfaces defined in `:domain`.
   - Coordinates data sources (injects `:data:network` services and local storage) and maps DTOs to Domain Models.
   - **Naming Convention**: Class names reflect the underlying technology/strategy instead of generic `Impl` suffixes (e.g., `InMemoryRecipeRepository`, `KtorRecipeRepository`, `RoomRecipeRepository`).
4. **`:shared` (Presentation Layer)**:
   - Feature package structure:
     - **`view`**: Compose views (`FeedScreen`, `RecipeDetailScreen`, etc.). No `ui` package!
     - **`viewmodel`**: Abstract and concrete ViewModels in separate files (`RecipeDetailViewModel.kt`, `RecipeDetailScreenViewModel.kt`, `FakeRecipeDetailViewModel.kt`).
     - **`model`**: MVI States, Intents, Effects, UI Models, Mappers (`RecipeDetailState`, `RecipeDetailIntent`, `RecipeDetailEffect`, `RecipeDetail`, `RecipeDetailMapper`).
     - **`interactor`**: Presentation Interactor interface and implementation (`RecipeDetailProvider.kt` / `RecipeDetailProviderInteractor.kt`).
   - **Presentation Interactor Rule**: Domain components are NOT injected directly into ViewModels. ViewModels inject Presentation Interactors (`RecipeDetailProvider`), which proxy calls to `:domain`, map Domain Models to `RecipeDetail`, and manage UI errors.
   - **Expression Body Functions**: Single-expression functions returning immediately must use the expression body (`=`) syntax.
   - **Single `state` property in ViewModels**: Concrete ViewModels override `val state = MutableStateFlow(...)` directly without duplicating `_state` and `state`.
   - **UI Constants**: All `.dp` and `.sp` dimensions are declared as top-level private constants using extension properties (e.g., `private val HEADER_ELEVATION = 6.dp`). No raw dimensions inside `@Composable` functions!
5. **`:androidApp` / `:iosApp`**:
   - Platform entry points.

---

## 🗺️ Incremental MVP Roadmap (Step-by-Step UI/UX)

- **Step 2**: **Minimal Feed Screen**
  - Display public list of recipes (`FeedScreen`).
- **Step 3 (Current Focus)**: **Recipe Detail Screen**
  - `RecipeDetailScreen` displaying ingredients checklist and step-by-step preparation instructions.
  - Interactive ingredient checkboxes and step numbers.
- **Step 4**: **Search Bar & Category Chapters**
  - Header search input and horizontal category filter chips.
- **Step 5**: **Favorites System**
  - Heart toggle action on cards and dedicated "Favorites" tab/filter.
- **Step 6**: **Recipe Management (Add & Delete)**
  - Form modal to submit new recipes and author delete option.
- **Step 7**: **Spring Boot Backend & Cloud Integration**
  - Ktor network implementation connecting mobile client to backend REST services.

---

## 🤖 AI Integration Strategy (Zero Cost / Free Tier)

To ensure maximum cross-platform code reuse and zero infrastructure cost during development:

- **Architecture**: **Cloud API Approach** (Option B) via Ktor / HTTP requests to Google AI Studio.
- **Cross-Platform Compatibility**: 100% shared Kotlin code across Android and iOS without writing platform-native plugins.
- **Nutritional Macro Calculation**:
  - Uses **Gemini Flash** (Gemini 1.5/2.0 Flash) in Structured Output (JSON) mode.
  - Cost: **100% Free** under Google AI Studio Free Tier (up to 15 Requests Per Minute / 1500 Requests Per Day).
- **Image Generation**:
  - Uses **Imagen 3** via Google AI Studio API for preview images when a user does not upload a photo.

---

## 🧪 Testing Strategy

- **`:domain`**: 100% Unit test coverage on Role-based Domain Components (`RecipeCreator`, `RecipeProvider`, `RecipeDeleter`).
- **`:presentation`**: Unit tests for ViewModels and Interactors verifying MVI State reductions using `Turbine`.
- **`:data:repository` & `:data:network`**: Unit tests for repositories, network clients, and data mappers.
