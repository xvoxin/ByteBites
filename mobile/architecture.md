# Architektura Projektu ByteBites

Dokument zawiera zasady architektoniczne, wzorce projektowe oraz dobre praktyki obowiązujące w projekcie **ByteBites (KMP)**. Dokument jest żywy i rozwijany wraz z kolejnymi ustaleniami.

---

## 🛠️ Zastosowane Wzorce i Zasady

### 1. Clean Architecture & Zagnieżdżone Moduły Gradle
Podział aplikacji na czyste warstwy reprezentowane przez osobne moduły i podmoduły Gradle:
- **`:domain`** – czysta logika biznesowa (Use Cases / Interactors / Providers / Creators), modele domenowe, interfejsy repozytoriów. Moduł nie posiada żadnych zależności od Androida, Ktor czy Compose.
- **`:data:network`** – komunikacja z API, klient HTTP (Ktor), DTOs, serializacja JSON, obsługa błędów sieciowych.
- **`:data:repository`** – warstwa integracji i zarządzania danymi. Zależy od `:domain` oraz `:data:network`. Implementuje interfejsy z `:domain` (np. `InMemoryRecipeRepository`, `KtorRecipeRepository`).
- **`:shared` (Presentation Layer)** – warstwa UI (Compose Multiplatform), ViewModele (MVI), Interactory prezentacji, komponenty widokowe.
- **`:androidApp` / `:iosApp`** – aplikacje uruchomieniowe (entry point dla danych platform).

---

### 2. Warstwa Prezentacji: Pakiety, Interactory i Formatowanie

#### A) Struktura Pakietów w Funkcjach Prezentacji (`presentation/feature/`)
Każdy moduł funkcjonalny w warstwie prezentacji posiada twardo zdefiniowaną strukturę pakietów:
- **`view`** – widoki Compose (`FeedScreen`, `FeedHeader`, `RecipeListItemCard`). Brak pakietu `ui`!
- **`viewmodel`** – ViewModele. Każda klasa w osobnym pliku!
  - Abstrakcja: `FeedViewModel.kt`
  - Implementacja: `FeedScreenViewModel.kt`
  - Atrapa: `FakeFeedViewModel.kt`
- **`model`** – stany MVI, intencje, efekty uboczne oraz modele UI i maperzy (`FeedState`, `FeedIntent`, `FeedEffect`, `RecipeUiModel`, `RecipeMapper`).
- **`interactor`** – Interactory prezentacji (`FeedProvider.kt`, `FeedProviderInteractor.kt`).

#### B) Konwencja Stanu MVI w ViewModelu (Brak `_state`)
- W konkretnym ViewModelu nie tworzymy sztucznych dwóch zmiennych `_state` i `state`.
- Nadpisujemy właściwość bezpośrednio:
  `override val state = MutableStateFlow(FeedState())`
- Wewnątrz klasy operujemy bezpośrednio na `state.update { ... }` lub `state.value = ...`.

#### C) Interactor Prezentacji & Nazewnictwo
- **Domena NIE MOŻE trafiać bezpośrednio do ViewModelu!**
- ViewModel wstrzykuje interfejs `FeedProvider` z warstwy prezentacji.
- Implementacja `FeedProviderInteractor` wstrzykuje domenowego dostawcę (z aliasem `import pl.bytebites.mobile.domain.provider.RecipeProvider as DomainRecipeProvider`), wywołuje go, mapuje `Recipe` na `RecipeUiModel` i zwraca do ViewModelu.

#### D) Stałe w Widokach Compose (`.dp` / `.sp`)
- **Żadnych zahardkodowanych wartości liczbowych (`16.dp`, `24.dp`, `28.sp`) wewnątrz funkcji `@Composable`!**
- Wszystkie stałe wymiarowe deklarujemy na samej górze pliku z rozszerzeniem `.dp` / `.sp`:
  ```kotlin
  private val HEADER_ELEVATION = 6.dp
  private val EMBLEM_SIZE = 56.dp
  private val EMBLEM_EMOJI_SIZE = 28.sp
  ```

#### E) Formatowanie i Style Kodu (Code Style Rules)
- **W nawiasach konstruktora `(...)` NIE dodajemy pustych linii.**
- **W ciele klasy `{ ... }` dodajemy pustą linię po otwarciu klamry i przed jej zamknięciem.**
- Oddzielamy pustą linią sekcję właściwości/zmiennych od sekcji metod.

---

### 3. Abstrakcje i Dependency Injection (Koin)
- **Koin** jest używany jako framework DI w KMP.
- **Każda wstrzykiwana klasa musi posiadać swoją abstrakcję / interfejs**.
- **Zakaz używania sufiksu `Impl` w nazwach klas!**
  - *Zamiast:* `RecipeRepositoryImpl`
  - *Stosujemy opisowe nazwy techniczne:* `InMemoryRecipeRepository`, `FeedProviderInteractor`, `FeedScreenViewModel`.

---

### 4. SOLID & Single Responsibility Principle (SRP)
- Klasy robią wyłącznie to, na co wskazuje ich nazwa.

---

## 🤝 Zasady Współpracy & Workflow AI

1. **Partnerstwo i Krytyczne Podejście**:
   - Jako asystent AI nie zgadzam się bezrefleksyjnie ze wszystkimi pomysłami.
2. **Potwierdzenie Przed Każdą Implementacją**:
   - Przed wykonaniem jakiejkolwiek zmiany w kodzie, przedstawiam plan i **wymagam Twojego wyraźnego potwierdzenia**.
3. **Aktualizacja Dokumentu**:
   - Po nowych ustaleniach pytam o zgodę na zaktualizowanie pliku `architecture.md`.
