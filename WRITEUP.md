# Edify Technical Writeup

## Executive Summary

Edify is an Android learning platform that implements AI tutoring using Google's Gemma 3n model. This technical writeup documents our architecture decisions, multimodal integration strategy, mobile optimization challenges, and engineering solutions for deploying a 2B parameter quantized model in production Android applications.

## Architecture Overview

### Design Philosophy

Our architecture decisions were driven by three core principles:
1. **Memory-Efficient**: Optimized resource management for 2B parameter model deployment on mobile devices
2. **Performance-Optimized**: Background processing with proper lifecycle management for long-running AI inference
3. **Scalable Design**: Clean separation of concerns with dedicated AI service layer for future model upgrades

### Technology Stack

**Frontend Choice - Jetpack Compose:**
We chose Jetpack Compose over traditional XML layouts because educational apps require dynamic, interactive UIs. Compose's declarative nature allows us to create responsive learning interfaces that adapt to different content types and user interactions seamlessly.

**Gemma Integration - MediaPipe:**
We implemented MediaPipe LLM Inference API to integrate Gemma 3n's multimodal capabilities in our app. We used the 2B parameter model (gemma-3n-E2B-it-int4.task)

**Database - Room with Flow:**
We implemented Room with reactive Flow patterns to enable real-time updates across the learning experience. This allows features like live progress tracking and instant quest generation based on user engagement.

**Architecture Pattern - MVVM with Services:**
We extended traditional MVVM with dedicated service layers for AI operations. This separation ensures UI responsiveness while handling computationally intensive AI tasks in the background.

## Architecture Details

### Layered Architecture Design
```
┌─────────────────────────────────────┐
│          UI Layer                   │
│    (Jetpack Compose Screens)       │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│      Presentation Layer             │
│    (ViewModels + UI State)          │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│     Business Logic Layer            │
│  (AI Services + Quest Generation)   │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│        Data Layer                   │
│   (Repository + DAOs + Workers)     │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│       Database Layer                │
│      (Room Database)                │
└─────────────────────────────────────┘
```

### Core Components
**UI Layer**: Compose screens handle user interactions and display educational content with reactive state updates.

**ViewModels**: Manage UI state using StateFlow, coordinate with services, and survive configuration changes using Hilt injection.

**AI Services**: Dedicated services (GemmaService, QuestGenerationService, ChatResponseService) handle all Gemma 3n interactions with mutex-protected concurrent access.

**Repository Pattern**: LearningRepository centralizes data access and coordinates between DAOs, AI services, and background workers.

**Background Processing**: WorkManager handles scheduled quest generation with proper dependency injection through Hilt workers.

### Data Flow Architecture
1. **User Interaction** → Compose UI captures input (text/images)
2. **ViewModel Processing** → Validates input and triggers appropriate service calls
3. **AI Service Execution** → Gemma 3n processes requests with multimodal capabilities
4. **Data Persistence** → Results stored via Repository to Room database
5. **State Updates** → Reactive Flow patterns propagate changes back to UI
6. **Background Sync** → WorkManager triggers periodic quest generation based on accumulated engagement data

### Dependency Injection Architecture
Hilt manages the complete dependency graph with proper scoping:
- **@Singleton**: AI services and database components for app-wide state
- **@ViewModelScoped**: UI state management with automatic cleanup
- **@WorkerScoped**: Background processing with injected dependencies

This architecture ensures clean separation of concerns while enabling complex AI-powered workflows

## Gemma 3n Implementation

### 1. Multimodal Processing
Gemma 3n's vision capabilities are integrated across two key educational features. In our **chat system**, students can photograph textbook pages, mathematical diagrams, or handwritten work alongside text questions for comprehensive tutoring. The **revision system** evaluates both typed responses and images of student work, providing feedback on handwritten mathematical solutions and diagrams. This multimodal integration leverages Gemma 3n's extended context window to maintain chapter awareness throughout conversations.

### 2. Educational Content Translation
We utilize Gemma 3n's 140-language support for real-time educational content translation. The model preserves educational terminology and contextual meaning through paragraph-level translation. We also implemented SHA-256 content hashing for efficient caching.

### 3. Automated Quest Generation
Our quest system uses a weighted interest scoring algorithm (40% chat, 25% notes, 20% visits, 15% revision) to identify chapters ripe for personalized challenges. `WorkManager` triggers daily quest generation at 6 PM with battery-conscious constraints. The system follows a two-tier strategy: divergent quests for chapters with `InterestScore > 1.0` focusing on single-chapter exploration, followed by convergent quests that synthesize concepts across multiple high-scoring chapters.

## Technical Challenges and Solutions

### 1. On-Device Performance Optimization

**Challenge**: High inference time on mobile devices
**Solution**: 
- **Model Quantization**: Used int4 quantized version (gemma-3n-E2B-it-int4.task)
- **Background Processing**: Moved inference to background threads with proper lifecycle management
- **Progressive Loading**: Implemented loading states with progress indicators and time estimates
- **Timeout Management**: 5-minute timeouts with graceful fallbacks


### 2. Memory Management and Resource Optimization

**Challenge**: MediaPipe JNI reference management causing crashes
**Solution**:
- **Proper Resource Management**: Used `use{}` blocks for automatic resource cleanup
- **Session-based Inference**: Implemented proper session lifecycle management
- **Mutex Synchronization**: Prevented concurrent model access with coroutine mutexes


### 3. Concurrent Quest Generation

**Challenge**: Race conditions causing duplicate quest generation
**Solution**:
- **Mutex Synchronization**: Implemented thread-safe quest generation
- **State Tracking**: Added flags to prevent duplicate processing
- **Background Processing**: Used GlobalScope for lifecycle-independent processing


### 4. Translation Caching and Performance

**Challenge**: Repeated translation requests for same content
**Solution**:
- **Content Hashing**: SHA-256 hashing for cache validation
- **Smart Invalidation**: Automatic cache invalidation on content changes
- **Instant Loading**: Cache-first approach with background updates


## Conclusion

This technical implementation demonstrates that Gemma 3n can be successfully deployed in production Android applications for educational use cases. Our engineering solutions address the core challenges of mobile AI: performance optimization, resource management, and user experience.
