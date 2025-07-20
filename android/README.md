# Edify - Android Learning App

A native Android learning companion app powered by Gemma 3n for offline AI capabilities. Transform static digital textbooks into an interactive, personalized study experience.

## Features

- **Home Screen**: Search functionality, continue reading, subject progress tracking
- **Subject Management**: Learning/Revision modes, chapter navigation, progress tracking
- **Interactive Reading**: Add notes, highlight text, explain concepts with AI
- **AI-Powered Chat**: Chat with Gemma 3n for explanations and learning assistance
- **Notes & Highlights**: Organize and filter your learning materials
- **Offline-First**: All AI processing happens on-device using MediaPipe LLM Inference

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM with Repository pattern
- **Database**: Room for local storage
- **Dependency Injection**: Hilt
- **AI Model**: Gemma 3n via MediaPipe LLM Inference API v0.10.24
- **Navigation**: Jetpack Navigation Compose

## Setup Instructions

### Prerequisites

1. **Android Studio**: Latest stable version (Hedgehog or newer)
2. **Android SDK**: API level 24+ (Android 7.0)
3. **Gemma 3n Model**: Download and prepare the model file

### Model Setup

1. Download Gemma 3n 1B 4-bit quantized model from Hugging Face
2. Convert to `.task` format using MediaPipe Model Maker
3. Push to device using ADB:

```bash
# Remove any previously loaded models
adb shell rm -r /data/local/tmp/llm/

# Create directory
adb shell mkdir -p /data/local/tmp/llm/

# Push model file
adb push path/to/your/model.task /data/local/tmp/llm/gemma_3n_1b_4bit.task
```

### Project Setup

1. **Clone the repository**:
```bash
git clone <repository-url>
cd project-unbound/android
```

2. **Open in Android Studio**:
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the `android` folder and select it

3. **Sync Project**:
   - Android Studio will automatically sync Gradle
   - Wait for all dependencies to download

4. **Build and Run**:
   - Connect an Android device or start an emulator
   - Click "Run" or press Shift+F10

## Project Structure

```
app/src/main/java/com/edify/learning/
├── data/
│   ├── dao/                 # Room DAOs
│   ├── database/           # Room database and converters
│   ├── model/              # Data models
│   ├── repository/         # Repository layer
│   └── service/            # Gemma service
├── di/                     # Dependency injection modules
├── presentation/           # UI layer
│   ├── home/              # Home screen
│   ├── subject/           # Subject screen
│   ├── chapter/           # Chapter reading screen
│   ├── notes/             # Notes and highlights
│   └── chat/              # Chat with Gemma
└── ui/theme/              # UI theme and styling
```

## Key Components

### Data Layer
- **Models**: Subject, Chapter, Note, ChatMessage
- **Database**: Room database with TypeConverters for JSON content
- **Repository**: Centralized data access with Gemma integration
- **Service**: GemmaService for AI model interactions

### UI Layer
- **Home**: Subject overview with search and continue reading
- **Subject**: Chapter list with Learning/Revision toggle
- **Chapter**: Interactive reading with note-taking and highlighting
- **Notes**: Filtered view of notes and highlights
- **Chat**: Real-time chat interface with Gemma

### Features Implementation

#### AI Integration
- MediaPipe LLM Inference for on-device AI
- Educational prompt engineering for better responses
- Streaming response support (ready for future implementation)

#### Content Management
- JSON-based content structure
- Highlight and note-taking with precise text selection
- Progress tracking and spaced repetition system foundation

#### User Experience
- Material Design 3 with custom brand colors
- Smooth navigation and state management
- Error handling and loading states

## Configuration

### Colors
- Primary Blue: `#003c63`
- Secondary Blue: `#5ea9ec`
- Background: White with light gray accents

### Model Configuration
Update `GemmaService.kt` to modify AI model parameters:
- `MAX_TOKENS`: Maximum response length
- `TOP_K`: Sampling parameter
- `TEMPERATURE`: Response creativity
- `MODEL_PATH`: Path to your model file

## Development

### Adding New Subjects
1. Update `LearningRepository.initializeSampleData()`
2. Add new Subject and Chapter entries
3. Create JSON content structure

### Customizing AI Responses
1. Modify prompt templates in `GemmaService`
2. Adjust model parameters for different use cases
3. Implement custom response processing

### Extending Features
- Add quiz and exercise functionality in revision mode
- Implement voice input/output
- Add image analysis capabilities
- Integrate spaced repetition algorithms

## Troubleshooting

### Common Issues

1. **Model Loading Fails**:
   - Verify model file exists at `/data/local/tmp/llm/gemma_3n_1b_4bit.task`
   - Check device storage and permissions
   - Ensure model is in correct `.task` format

2. **Build Errors**:
   - Clean and rebuild project
   - Sync Gradle files
   - Check Android SDK and build tools versions

3. **Performance Issues**:
   - Reduce model parameters (TOP_K, MAX_TOKENS)
   - Test on device with sufficient RAM (4GB+ recommended)
   - Monitor memory usage during AI inference

### Debug Mode
Enable debug logging in `GemmaService` to troubleshoot AI responses:
```kotlin
private const val DEBUG_MODE = true
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly on device
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Google MediaPipe team for LLM Inference API
- Gemma model by Google DeepMind
- Material Design 3 components
- Android Jetpack libraries
