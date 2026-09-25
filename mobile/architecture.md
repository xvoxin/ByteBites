# Architektura Projektu ByteBites

Dokument zawiera zasady architektoniczne, wzorce projektowe oraz dobre praktyki obowiązujące w projekcie **ByteBites (KMP)**. Dokument jest żywy i rozwijany wraz z kolejnymi ustaleniami.

---

## 🛠️ Zastosowane Wzorce i Zasady

### 1. Clean Architecture & Wielomodułowy Gradle
Podział aplikacji na czyste warstwy reprezentowane przez moduły Gradle (bez zbędnego zagnieżdżania w `:core`):
- **`:domain`** – czysta logika biznesowa (Use Cases / Interactors), modele domenowe, interfejsy repozytoriów. Moduł nie posiada żadnych zależności od Androida, Ktor czy Compose.
- **`:data`** – implementacje repozytoriów, mapowanie modeli, obsługa sieci (Ktor) oraz lokalnych baz danych. Zależy od `:domain`.
- **`:presentation` (lub `:shared`)** – warstwa UI (Compose Multiplatform), ViewModele (MVI), nawigacja, komponenty widokowe. Zależy od `:domain`.
- **`:androidApp` / `:iosApp`** – aplikacje uruchomieniowe (entry point dla danych platform).

---

### 2. Abstrakcje i Dependency Injection (Koin)
- **Koin** jest używany jako framework DI w KMP.
- **Każda wstrzykiwana klasa musi posiadać swoją abstrakcję / interfejs**.
- **Zakaz używania sufiksu `Impl` w nazwach klas!**
  - *Zamiast:* `RecipeRepositoryImpl`
  - *Stosujemy opisowe nazwy techniczne:* `InMemoryRecipeRepository`, `KtorRecipeRepository`, `RoomRecipeRepository`.

---

### 3. Wzorzec MVI & Abstrakcyjny ViewModel
- Warstwa prezentacji opiera się na MVI:
  - **State** – spójny, reaktywny stan widoku reprezentowany przez `StateFlow`.
  - **Intent / Event** – akcje użytkownika przekazywane do ViewModelu.
  - **Effect / Side Effect** – jednorazowe zdarzenia (nawigacja, komuniakaty toast/snackbar itp.).
- **ViewModel jako Abstrakcja**:
  - Do widoku Compose przekazujemy interfejs / klasę abstrakcyjną ViewModelu (np. `abstract class FeedViewModel`).
  - Ułatwia to testowanie samego widoku, izoluje UI od logiki oraz pozwala na czyste `@Preview` z użyciem atrapy `FakeFeedViewModel`.
  - W Koinie rejestrujemy ViewModel wiążąc go z abstrakcją.

---

### 4. Solidne Testy (Testing Strategy) 🧪
- Aplikacja musi być solidnie przetestowana na wszystkich warstwach:
  - **Unit tests w `:domain`**: Testowanie logiki UseCase'ów.
  - **Unit tests w `:presentation`**: Testowanie redukcji stanów i emisji SideEffectów w ViewModelach (z użyciem **Turbine** dla `StateFlow`).
  - **Unit tests w `:data`**: Testowanie repozytoriów i maperów.

---

### 5. SOLID & Single Responsibility Principle (SRP)
- Klasy robią wyłącznie to, na co wskazuje ich nazwa.
- Przestrzegamy zasad SOLID. Każda klasa/Use Case ma jedną jasno określoną odpowiedzialność.

---

### 6. Komponenty UI i Podział Widoków
- Widoki **nie mogą być monolityczne**.
- Komponenty UI dzielimy na mniejsze, czytelne pliki wewnątrz warstwy prezentacji.
- Dbanie o czytelność, łatwe reużywanie oraz czytelne podglądy (`@Preview`).

---

### 7. Brak Stałych w Kodzie (No Magic Values)
- Żadnych zahardkodowanych stałych wewnątrz klas czy funkcji.
- Wszystkie stałe muszą być wyniesione nad klasę/główną metodę `@Composable` jako `private const val`, lub zebrane w dedykowanych strukturach/motywach.

---

## 🤝 Zasady Współpracy & Workflow AI

1. **Partnerstwo i Krytyczne Podejście**:
   - Jako asystent AI nie zgadzam się bezrefleksyjnie ze wszystkimi pomysłami.
   - Wchodzimy w merytoryczną dyskusję, analizujemy za i przeciw (pros/cons) przed wyborem rozwiązania.
2. **Potwierdzenie Przed Każdą Implementacją**:
   - Przed wykonaniem jakiejkolwiek zmiany w kodzie, przedstawiam plan i **wymagam Twojego wyraźnego potwierdzenia**.
3. **Aktualizacja Dokumentu**:
   - Po nowych ustaleniach pytam o zgodę na zaktualizowanie pliku `architecture.md`.
