# Overview
This document outlines the strategy for building an interactive, offline-first learning companion as a native Android application. The core mission is to transform static digital textbooks by leveraging on-device AI to create a personalized study loop for students.
We will use Gemma 3n to power the offline AI capabilities of the app.
https://ai.google.dev/edge/mediapipe/solutions/genai/llm_inference/android


# UI
## Home Screen
- Implement simple text search functionality on chapter titles
- Build continue reading component
	- It should store the details of which chapter was being read last and should directly land the user there
- Subject List
	- Show subject list along with progress rate

## Subject Screen
- Toggle - Learning | Revision
	- The toggle at the top should allow the user to switch between learning and revision mode
	- Learning Mode
		- Display a list of chapters
		- User can tap on any chapter and start reading the content inside that chapter
	- Revision Mode
		- It will contain 2 main components - Quiz and Exercises
			- Quiz - Obective questions
			- Exercises - Subjective Questions

## Chapter Screen
- Add Note Button - This will allow users to add a new note. The note will be displayed inside the Notes tab
- Explain Button - This will open up a new chat window with Gemma
	- Users will be able to send text, images and voice notes to chat with Gemma
- Highlight
	- Users will have an option to highlight a piece of text and/or image and save it to Notes
	- They can also highlight a piece of content in the chapter and tap on "Explain". This will open a new chat window with Gemma where the selected text will already be added to the chat and gemma explanation will be loaded after that

## Notes Screen
- It should have 3 quick-filters at the top (audible-style)
	1. All
	2. Notes - It will show the notes add by the user in a chapter
	3. Highlights - It will store all the highlighted text and/or images from a chapter

# Personalized Learning
- We need to track all important user interactions locally in order to geenrate a personalised revision plan for each subject
- We will use Spaced Repetition System (SRS) to generate a revision plan for each subject along with tracking all questions that the student got wrong
- The revision mode inside the subject-view will be used to serve this personalised revision plan

# Design Principles
- Stick to using light theme
- Follow design principles of Google's Material Design
- Follow best practices for a ed-tech app
- Make the UI sleek and modern
- Use the following colors - white, #003c63, #5ea9ec

# Notes
The content inside each chapter will be stored in a JSON format, we need to structure the notes in the following format

## Text and Image Notes
```json
"contentItems": [
  {
    "type": "text",
    "blockId": "ch01-b002",
    "selectedText": "Newton's First Law states that an object will remain at rest or in uniform motion in a straight line unless acted upon by an external force.",
    "startOffset": 0,
    "endOffset": 149
  },
  {
    "type": "image",
    "blockId": "ch01-b003",
    "src": "assets/images/chapter1/fig-1-1.webp",
    "caption": "Fig 1.1: An apple falling from a tree."
  }
]
```

## Single Text Note
```json
"contentItems": [
  {
    "type": "text",
    "blockId": "ch01-b002",
    "selectedText": "uniform motion in a straight line",
    "startOffset": 58,
    "endOffset": 91
  }
]
```