# Gemma Model Setup Instructions

## Required Model File

The Edify app requires the Gemma model file to be placed in this directory for the AI chat functionality to work properly.

**Required file:** `gemma_3n_1b_4bit.task`

## Installation Instructions

1. Download the Gemma-3 1B model in 4-bit quantized format from [Hugging Face](https://huggingface.co/google/gemma-2b-it)
2. Convert the model to task format using MediaPipe tools if needed
3. Place the `gemma_3n_1b_4bit.task` file in this directory (`android/app/src/main/assets/models/`)
4. Rebuild the app

## Alternative Installation Options

If you can't place the model file in assets during build time, the app will also look for the model in:

1. `assets/gemma_3n_1b_4bit.task` (root assets directory)
2. `/data/local/tmp/llm/gemma_3n_1b_4bit.task` (device storage, requires adb)
3. App's internal file storage

## Using ADB to Push the Model

```bash
# Remove any previously loaded models
adb shell rm -r /data/local/tmp/llm/

# Create directory
adb shell mkdir -p /data/local/tmp/llm/

# Push model file
adb push path/to/gemma_3n_1b_4bit.task /data/local/tmp/llm/gemma_3n_1b_4bit.task
```

## Reference

For more information, refer to the official MediaPipe LLM Inference documentation:
https://ai.google.dev/edge/mediapipe/solutions/genai/llm_inference/android
