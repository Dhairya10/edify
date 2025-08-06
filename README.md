# Edify - The Universal Tutor

  

An Android app that combines educational content with Gemma's on-device AI to create personalized & interactive study experiences

  

## Key Features


### **1. Multi-Language Translation**

-  **Trigger**: Translate button available in every chapter

-  **Capability**: Real-time content translation powered by Gemma 3n

-  **Languages**: Supports multiple languages with context-aware translation

-  **Integration**: Seamless in-chapter translation without losing reading progress

  

### **2. Socratic Tutoring**

-  **Trigger**: Chat button in chapter reading interface

-  **Method**: Socratic questioning approach for deeper understanding

-  **Multi-modal**: Supports both text and image inputs for comprehensive learning

-  **Context-Aware**: AI understands chapter content and provides relevant explanations

-  **Educational Focus**: Guides students to discover answers rather than giving direct solutions

  

### **3. Personalized Revision System**

-  **Personalized Feedback**: AI evaluates user responses and provides detailed feedback

-  **Multi-modal Input**: Students can submit text answers or upload images of their work

-  **Adaptive Questions**: Practice questions tailored to individual learning patterns

-  **Progress Tracking**: Monitors improvement and adjusts difficulty accordingly

  

### **4. Personalized Quest Generation**

-  **Smart Scheduling**: Daily quest generation at 6 PM using WorkManager

-  **Two-Phase System**: Divergent quests (deep chapter exploration) and convergent quests (cross-chapter synthesis)

-  **Interest-Based**: Uses interaction patterns (notes, chats, revisions) to generate relevant quests

-  **Battery Optimized**: Runs only when device battery is sufficient
  

## Technical Stack

  

### **Core Technologies**

-  **Language**: Kotlin with Java 17 compatibility

-  **UI Framework**: Jetpack Compose with Material Design 3

-  **Architecture**: MVVM + Repository pattern with Clean Architecture principles

-  **Database**: Room with TypeConverters for JSON content storage

-  **Dependency Injection**: Hilt/Dagger for comprehensive DI

-  **Background Processing**: WorkManager for quest generation

-  **Navigation**: Jetpack Navigation Compose with bottom navigation

-  **Image Processing**: Coil for image loading and caching
  

### **AI / ML**

-  **Model**: Gemma 3n (gemma-3n-E2B-it-int4.task)

-  **Framework**: MediaPipe (LLM Inference API)

-  **Capabilities**: Text generation, Image analysis, Content translation


## Setup Instructions

  

### Prerequisites

  

1.  **Android Studio**: Latest stable version (Hedgehog 2023.1.1 or newer)

2.  **Java**: JDK 17+ (required for MediaPipe compatibility)

3.  **Android SDK**: API level 24+ (Android 7.0)

4.  **Device**: Android device with 4GB+ RAM recommended for optimal AI performance (tested on Pixel 6a)

5.  **Gemma 3n Model**: Required for AI functionality

  

### Gemma Setup

  

The app **requires** the Gemma 3n model file to enable AI features. Without it, the app falls back to mock responses.

Download the model from [Kaggle](https://www.kaggle.com/models/google/gemma-3n/tfLite)

  

#### **Model File Requirements:**

-  **Filename**: `gemma-3n-E2B-it-int4.task` (exact filename expected)

-  **Format**: MediaPipe .task format

-  **Size**: ~2.5GB (4-bit quantized version)

  

#### **Model Placement Options (in order of priority):**

  

**Option 1: App-Specific Directory (Recommended)**

```bash

adb  push  gemma-3n-E2B-it-int4.task  /sdcard/Android/data/com.edify.learning/files/

```

*No special permissions required, app can access directly*

  

**Option 2: System Temp Directory**

```bash

adb  shell  mkdir  -p  /data/local/tmp/llm/

adb  push  gemma-3n-E2B-it-int4.task  /data/local/tmp/llm/

```

  

**Option 3: Models Directory**

```bash

adb  shell  mkdir  -p  /sdcard/Models/

adb  push  gemma-3n-E2B-it-int4.task  /sdcard/Models/

```

  

**Option 4: Download Directory**

```bash

adb  push  gemma-3n-E2B-it-int4.task  /sdcard/Download/

```

  

### Project Setup

  

1.  **Clone the repository**:

```bash

git  clone git@github.com:Dhairya10/edify.git

cd  edify/android

```

  

2.  **Open in Android Studio**:

- Open Android Studio

- Select "Open an existing project"

- Navigate to the `android` folder and select it

  

3.  **Sync Project**:

- Android Studio will automatically sync Gradle

- Wait for all dependencies to download

  

4.  **Build and Run**:

- Connect an Android device or start an emulator

- Click "Run" or press Shift+F10

  

## Project Architecture

  

### **Directory Structure**

```

app/src/main/java/com/edify/learning/

├── data/

	│ ├── dao/ # Room DAOs for database access

	│ ├── database/ # Room database and TypeConverters

	│ ├── model/ # Data models (Subject, Chapter, Quest, etc.)

	│ ├── repository/ # LearningRepository - centralized data access

	│ └── service/ # AI and business logic services

		│ ├── GemmaService # AI model interactions

		│ ├── QuestGenerationService

		│ ├── QuestScoringService

		│ └── PromptService

├── di/ # Hilt dependency injection modules

├── presentation/ # UI layer - MVVM ViewModels and Compose screens

	│ ├── home/ # Library/Dashboard screen

	│ ├── subject/ # Subject listing screen

	│ ├── chapter/ # Interactive reading screen

	│ ├── quest/ # Personalized quest system

	│ ├── notes/ # Notes management

	│ ├── chat/ # AI chat interface

	│ ├── revision/ # Practice questions

	│ └── profile/ # User settings

├── ui/theme/ # Material Design 3 theming

├── util/ # Utilities and helpers

	│ ├── ContentLoader # JSON content parsing

	│ └── MarkdownRenderer # Rich text rendering

└── MainActivity # Navigation host

```

  

### **Key Architectural Patterns**

  

#### MVVM + Repository Pattern**

-  **ViewModels**: Manage UI state with StateFlow/LiveData

-  **Repository**: `LearningRepository` centralizes data access and AI integration

-  **Services**: Dedicated services for AI, quest generation, and scoring


## Download APK

**[Download Edify APK](https://drive.google.com/file/d/11paqtgqd-9zB29M5Ar-sKGnkXu0j_xqh/view?usp=sharing)**

> **Note**: The app requires the Gemma 3n model file for AI features. See the "Gemma Setup" section above for detailed instructions.


## Acknowledgments

  

-  **Google MediaPipe** team for LLM Inference API

-  **Google DeepMind** for Gemma model architecture

-  **Android Jetpack** libraries and Material Design 3

-  **Kotlin** and **Jetpack Compose** communities

  

---

  

**Built with ❤️ and Gemma**