Requirements & Architecture Specification: Culinary Application (Full-Stack KMP + Spring Boot)
1. Project Overview
This repository contains a product-minded culinary full-stack application. The goal is to provide a seamless cross-platform mobile experience (built with Kotlin Multiplatform) backed by a robust backend service (Spring Boot), leveraging a modern, cost-effective, cloud-native deployment workflow.

2. Technology Stack & Environment
2.1. Client (Mobile Frontend)
Framework: Kotlin Multiplatform (KMP) with Compose Multiplatform.

IDE: Android Studio.

AI Assistance: Gemini in IDE plugin as a coding agent for UI development and shared logic.

2.2. Server (Backend)
Framework: Spring Boot (Kotlin/Java).

Database: PostgreSQL (managed locally via Docker, cloud-hosted in production).

IDE: IntelliJ IDEA Community.

AI Assistance: Gemini in IDE plugin for API development and business logic implementation.

2.3. Infrastructure & Hosting
Cloud Provider: Google Cloud Platform (GCP).

Backend Hosting: Google Cloud Run (leveraging the Always Free tier for cost-efficient container execution).

Containerization: Custom Dockerfile for Spring Boot, containerized via Docker.

2.4. CI/CD & Automation
Automation Server: Jenkins (triggered automatically upon a git push).

Pipeline Stages:

Fetch the latest monorepo code.

Execute automated unit and integration tests for both backend and mobile client.

Build container images upon a green build and deploy automatically to Google Cloud Run.

3. Architecture & Project Structure (Monorepo)
Plaintext
kulinarna-apka/
├── backend/            # Spring Boot backend project (Kotlin/Java + Gradle/Maven)
│   ├── src/
│   ├── Dockerfile
│   └── build.gradle.kts
├── mobile/             # Kotlin Multiplatform mobile application
│   ├── shared/
│   ├── androidApp/
│   ├── iosApp/
│   └── build.gradle.kts
├── docs/               # Project documentation, architecture notes, and specifications
├── docker-compose.yml  # Local development environment (PostgreSQL & local services)
├── AGENTS.md           # Guidelines and context configuration for AI agents
└── requirements.md     # This system specification file
4. Development & Engineering Guidelines
Docs-as-Code: All system requirements, architecture notes, and API documentation must be maintained in Markdown format within the /docs directory to live alongside the codebase.

Agent Context: The AGENTS.md file in the root directory provides context for desktop AI assistants (e.g., Gemini, Claude) regarding project conventions, Clean Architecture principles, and tech stacks.

Secret Management: Sensitive credentials (database passwords, API keys, service account tokens) must never be committed to version control. They are managed locally via .env files (ignored by Git) and injected via environment variables in Google Cloud Run.