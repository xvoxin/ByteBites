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

ByteBites strictly enforces **Clean Architecture** using a layered Multi-module Gradle structure (omitting unnecessary `:core` nesting):

1. **`:domain`**:
   - Pure Kotlin Multiplatform code containing Domain Models (`Recipe`, `User`, `RecipeCategory`), Repository Interfaces, and Use Cases.
   - Zero dependencies on Android, iOS, Compose, or Ktor.
2. **`:data`**:
   - Implements repository interfaces defined in `:domain`.
   - Responsible for data sources, network clients (Ktor), local databases, and mappers.
   - **Naming Convention**: Class names reflect the underlying technology/strategy instead of generic `Impl` suffixes (e.g., `InMemoryRecipeRepository`, `KtorRecipeRepository`, `RoomRecipeRepository`).
3. **`:shared` (Presentation Layer)**:
   - Contains Compose Multiplatform UI, MVI Contracts (State, Intent, Effect), Abstract ViewModels, Navigation, and Koin modules.
   - **Abstract ViewModel Pattern**: ViewModels are passed to Compose functions as interfaces or abstract classes (e.g., `abstract class FeedViewModel`), allowing clean `@Preview` renders with `FakeFeedViewModel` and decoupled UI testing.
4. **`:androidApp` / `:iosApp`**:
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

- **`:domain`**: 100% Unit test coverage on Use Cases.
- **`:presentation`**: Unit tests for ViewModels verifying MVI State reductions and SideEffect emissions using `Turbine`.
- **`:data`**: Unit tests for repositories and data mappers.
