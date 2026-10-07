# ByteBites Mobile Architecture & Specifications

This document defines the mobile architecture, technology choices, engineering standards, and AI integration strategy for the **ByteBites (KMP)** application.

---

## 🛠️ Tech Stack & Core Standards

- **Framework**: Kotlin Multiplatform (KMP) with Compose Multiplatform for Android & iOS.
- **Dependency Injection**: Koin.
- **Presentation Pattern**: Model-View-Intent (MVI) with Abstract ViewModels.
- **Testing Tools**: `kotlin.test`, `kotlinx-coroutines-test`, `Turbine` (for `StateFlow` testing).

---

## 🏛️ Architecture & Module Breakdown

ByteBites strictly enforces **Clean Architecture** using a dedicated nested Multi-module Gradle structure:

1. **`:domain`**:
   - Pure Kotlin Multiplatform code containing Domain Models (`Recipe`, `User`, `RecipeCategory`), Repository Interfaces, and Role-based Domain Components:
     - `RecipeProvider`: Fetching and filtering recipes.
     - `RecipeCreator`: Validating and creating recipes.
     - `RecipeDeleter`: Author authorization check and recipe deletion.
     - `RecipeFavoriteToggler`: Toggling favorite status.
     - `UserProvider`: Providing authenticated user profile.
     - `UserAuthenticator`: Handling user login and logout.
   - **Crucial Rule**: ViewModels in presentation layer MUST NOT inject repositories directly; they only interact with these role-based domain components.
2. **`:data:network`**:
   - Manages HTTP API communication, Ktor client setup, DTO models (`RecipeDto`, `UserDto`), JSON serialization, and network error handling.
3. **`:data:repository`**:
   - Implements repository interfaces defined in `:domain`.
   - Coordinates data sources (injects `:data:network` services and local storage) and maps DTOs to Domain Models.
   - **Naming Convention**: Class names reflect the underlying technology/strategy instead of generic `Impl` suffixes (e.g., `InMemoryRecipeRepository`, `KtorRecipeRepository`, `RoomRecipeRepository`).
4. **`:shared` (Presentation Layer)**:
   - Contains Compose Multiplatform UI, MVI Contracts (State, Intent, Effect), Abstract ViewModels, Navigation, and Koin modules.
   - **Abstract ViewModel Pattern**: ViewModels are passed to Compose functions as interfaces or abstract classes (e.g., `abstract class FeedViewModel`), allowing clean `@Preview` renders with `FakeFeedViewModel` and decoupled UI testing.
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
- **`:presentation`**: Unit tests for ViewModels verifying MVI State reductions and SideEffect emissions using `Turbine`.
- **`:data:repository` & `:data:network`**: Unit tests for repositories, network clients, and data mappers.
