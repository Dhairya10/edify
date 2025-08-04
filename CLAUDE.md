# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development Commands

### Android Development
```bash
# Navigate to Android directory
cd android

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Run tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Clean build
./gradlew clean

# Build release APK
./gradlew assembleRelease
```

NOTE: I will build the app directly in Android Studio, do not run gradle commands outside of it

### Model Setup (Required for AI functionality)
The app uses Gemma 3n model for on-device AI. Model setup is critical:

```bash
# Remove previous models
adb shell rm -r /data/local/tmp/llm/

# Create directory and push model
adb shell mkdir -p /data/local/tmp/llm/
adb push path/to/gemma-3n-E2B-it-int4.task /data/local/tmp/llm/
```

Alternative locations the app checks:
- `/sdcard/Android/data/com.edify.learning/files/` (app-specific, no permissions needed)
- `/data/local/tmp/llm/`
- `/sdcard/Models/`
- `/sdcard/Download/`

## Architecture Overview

### Tech Stack
- **Language**: Kotlin with Jetpack Compose
- **Architecture**: MVVM + Repository pattern
- **Database**: Room with TypeConverters for JSON content
- **DI**: Hilt
- **AI**: MediaPipe LLM Inference (Gemma 3n model)
- **Navigation**: Navigation Compose

### Key Architectural Components

#### Data Layer (`data/`)
- **Models**: Subject, Chapter, Note, ChatMessage, Quest, ChapterStats
- **DAOs**: Room database access objects for all entities
- **Repository**: `LearningRepository` centralizes data access and AI integration
- **Services**: 
  - `GemmaService`: Handles AI model interactions with fallback mock responses
  - `QuestService`: Manages quest generation and scoring
  - `QuestGenerationService`: AI-powered quest creation

#### Presentation Layer (`presentation/`)
- **ViewModels**: Each screen has dedicated ViewModel with Hilt injection
- **Screens**: Compose UI screens for Home, Subject, Chapter, Quest, Chat, Notes
- **Components**: Reusable UI components including `MarkdownRenderer`

#### Domain Logic
- **Quest System**: Personalized learning quests based on user interaction patterns
- **Chapter Stats**: Tracks notes, chats, revisions for personalization
- **Content Loading**: JSON-based content from assets with dynamic parsing

### Critical Implementation Details

#### AI Integration Pattern
```kotlin
// Service initialization with fallback
suspend fun initializeModel(): Result<Unit>
// Always provides response (real AI or mock)
suspend fun generateResponse(prompt: String): Result<String>
```

#### Content Management
- JSON files in `assets/` directories by subject (english/, maths/, science/, social_science/)
- Images in `assets/images/` with chapter-specific subdirectories
- Dynamic content loading with `ContentLoader` utility

#### Database Schema
- Subjects have Chapters (1:many)
- Chapters have Notes (1:many) and ChatMessages (1:many)
- ChapterStats tracks user interaction patterns for quest generation
- Quest system with GeneratedQuest table for personalization

#### Quest Generation Logic
- Triggered by user actions: notes, chats, revision answers
- Uses ChapterStats to determine user interest and difficulty
- AI-generated questions with scoring algorithm
- Personalization based on interaction frequency and patterns

## Development Guidelines

### When Working with AI Features
- Always test both with and without model file present
- GemmaService provides fallback responses when model unavailable
- Model path: `gemma-3n-E2B-it-int4.task` (specific filename expected)
- AI responses are limited to mobile-friendly lengths (80-150 words)

### Content Addition
- Add JSON files to appropriate subject directory in assets
- Include images in assets/images/[chapter_id]/ structure
- Use `ContentLoader.loadChaptersForSubject()` for dynamic loading
- Update fallback data in `LearningRepository.initializeFallbackData()` if needed

### Database Changes
- Use Room migrations for schema changes
- Update TypeConverters in `Converters.kt` for complex data types
- ChapterStats uses JSON fields for flexible tracking data

### Quest System Integration
- Quest generation happens automatically on user interactions
- Modify scoring algorithm in `QuestScoringService`
- Adjust personalization logic in `QuestGenerationService`

### UI Development
- Use Material 3 design with custom colors: Primary Blue (#003c63), Secondary Blue (#5ea9ec)
- Compose UI with consistent theming in `ui/theme/`
- Handle loading/error states consistently across screens

## Common Issues & Solutions

### Model Loading Issues
- Check file exists at expected path with `adb shell ls -la /data/local/tmp/llm/`
- Verify file size > 1MB and has proper format
- App falls back to mock responses automatically

### Build Issues
- Clean and sync Gradle if compilation fails
- Ensure Android SDK API 24+ and latest build tools
- KAPT annotation processing used for Room and Hilt
- **Java 17 required**: MediaPipe dependency requires Java 17+ (configured in app/build.gradle)
- If KAPT fails with "wrong version" errors, verify Java target version compatibility

### Content Loading Problems
- JSON files must match expected structure with `contentItems` array
- Images referenced in JSON must exist in assets/images/
- ContentLoader provides detailed logging for debugging

### Database Migration
- Room auto-migration enabled but manual migrations may be needed
- Use `@Database(exportSchema = true)` for migration path planning
- Clear app data if schema changes break existing installations

## Important Notes for Quest Development
- Always review @Quest - Generation Logc.md and @Quest - Scoring Algo.md for updating anything related to Quests
