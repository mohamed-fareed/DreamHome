# DreamHome

## Overview

This project implements a scrollable result list for property searches, inspired by the Hemnet app.
The list displays three types of items: regular properties, highlighted properties (with a golden
frame), and area information. Tapping an item shows a contextual message. Data is loaded from a
simulated API using the provided JSON example.

## Tech Stack

- Kotlin: Main programming language for modern, concise, and safe Android development.
- Android Jetpack Compose: For building declarative, reactive UIs.
- Java 21: Project is compatible with Java 21 as required.
- Gradle: Build system.
- Koin: Dependency injection for modular and testable code.
- Retrofit: For network abstraction (even though the API is simulated/mocked).
- Coil: For efficient image loading in Compose.
- Gson: For JSON parsing.

## Architecture & Design Patterns

- MVVM (Model-View-ViewModel):
    - `ViewModel` (`SearchViewModel`) manages UI state and business logic.
    - UI (`SearchScreen`) observes state and renders accordingly.
    - Data layer (`SearchRepository`) abstracts data sources (local/remote).
- Repository Pattern:
    - `SearchRepository` provides a clean API for fetching and caching search results, abstracting
      data source details.
- Dependency Injection:
    - Koin modules provide dependencies, making the codebase modular and testable.
- Unidirectional Data Flow:
    - UI observes immutable state from the ViewModel, ensuring predictable rendering.

## How It Works

- On launch, the app fetches (mocked) search results and displays them in a scrollable list.
- Each item is rendered according to its type (property, highlighted property, or area).
- Tapping an item shows a contextual snackbar message.
- Images are loaded asynchronously with placeholders and error handling.

## Improvements & Next Steps

- Add navigation to detail screens for each item.
- Implement real API integration and error handling.
- Add UI tests.
- Improve accessibility and localization.