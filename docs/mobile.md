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
   - Pure Kotlin Multiplatform code containing Domain Models (`Recipe`, `User`, `RecipeCategory`), Repository Interfaces, and Role-based Domain Components (`RecipeProvider`, `RecipeCreator`, `RecipeDeleter`, `RecipeFavoriteToggler`, `UserProvider`, `UserAuthenticator`).
2. **`:data:network`**:
   - Manages HTTP API communication, Ktor client setup, DTO models (`RecipeDto`, `UserDto`), JSON serialization, and network error handling.
3. **`:data:repository`**:
   - Implements repository interfaces defined in `:domain`.
   - Coordinates data sources (injects `:data:network` services and local storage) and maps DTOs to Domain Models.
   - **Naming Convention**: Class names reflect the underlying technology/strategy instead of generic `Impl` suffixes (e.g., `InMemoryRecipeRepository`, `KtorRecipeRepository`, `RoomRecipeRepository`).
4. **`:shared` (Presentation Layer)**:
   - Feature package structure:
     - **`view`**: Compose views (`FeedScreen`, `FeedHeader`, `RecipeListItemCard`). No `ui` package!
     - **`viewmodel`**: Abstract and concrete ViewModels in separate files (`FeedViewModel.kt`, `FeedScreenViewModel.kt`, `FakeFeedViewModel.kt`).
     - **`model`**: MVI States, Intents, Effects, UI Models, Mappers (`FeedState`, `FeedIntent`, `FeedEffect`, `RecipeUiModel`, `RecipeMapper`).
     - **`interactor`**: Presentation Interactor interface and implementation (`FeedProvider.kt` / `FeedProviderInteractor.kt`).
   - **Presentation Interactor Rule**: Domain components are NOT injected directly into ViewModels. ViewModels inject Presentation Interactors (`FeedProvider`), which proxy calls to `:domain`, map Domain Models to `RecipeUiModel`, and manage UI errors.
   - **Single `state` property in ViewModels**: Concrete ViewModels override `val state = MutableStateFlow(...)` directly without duplicating `_state` and `state`.
   - **UI Constants**: All `.dp` and `.sp` dimensions are declared as top-level private constants using extension properties (e.g., `private val HEADER_ELEVATION = 6.dp`). No raw dimensions inside `@Composable` functions!
5. **`:androidApp` / `:iosApp`**:
   - Platform entry points.

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
