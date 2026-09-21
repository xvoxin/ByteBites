Welcome, AI assistant. You are helping develop ByteBites, a product-minded, full-stack culinary application. This file provides architectural guidelines, tech stack constraints, and engineering standards for this repository.

1. Project Overview & Philosophy
Project Name: ByteBites

Goal: A robust, cross-platform culinary application with a shared Kotlin Multiplatform (KMP) client and a Spring Boot backend, deployed cost-effectively via Google Cloud Run.

Core Principles:

Docs-as-Code: Keep architectural notes and requirements updated in Markdown inside /docs or the root directory.

Clean Architecture: Enforce clear separation of concerns (domain, data/infrastructure, presentation/UI) across both mobile and backend layers.

English Code: All code, comments, documentation, and commit messages must be written in English.

2. Tech Stack & Modules
Plaintext
bytebites/
├── backend/            # Spring Boot (Kotlin/Java) + PostgreSQL
├── mobile/             # Kotlin Multiplatform (Compose Multiplatform)
├── docs/               # Markdown documentation & specs
├── docker-compose.yml  # Local infrastructure (PostgreSQL)
├── requirements.md     # Full system specification
└── AGENTS.md           # This context file
Backend (/backend)
Language/Framework: Kotlin / Spring Boot.

Database: PostgreSQL (connected via Docker locally, cloud-hosted in production).

Build Tool: Gradle or Maven (configured with wrapper).

IDE: IntelliJ IDEA Community.

Mobile (/mobile)
Framework: Kotlin Multiplatform with Compose Multiplatform.

IDE: Android Studio.

UI/UX: Declarative UI using Jetpack/Compose Multiplatform paradigms.

3. Engineering Guidelines for AI Code Generation
When generating or modifying code in this repository, always adhere to the following rules:

Idiomatic Kotlin: Use modern Kotlin features (coroutines, flows, data classes, immutability by default) where appropriate.

Error Handling: Avoid empty catch blocks. Use structured result types or proper exception handling patterns.

No Hardcoded Secrets: Never generate hardcoded database credentials, API keys, or tokens. Always rely on environment variables (.env or system properties).

Modularity: Respect the monorepo boundaries. Do not leak backend logic into the mobile shared module or vice-versa.