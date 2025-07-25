This document outlines a practical, latency-aware approach for implementing the interest-scoring mechanism. This version simplifies the revision analysis for better performance.

### 1. Local Database: Data Models

The data model is updated to use a single `correctnessScore` for revision analysis, combining the previous accuracy and completeness metrics.

**`ChapterStats` Table/Collection:**

```
{
  "chapterId": "english_nelson_mandela",
  "userId": "user_alex",
  "visitCount": 4,
  "noteCount": 2,
  "revisionHistory": [
    {
      "questionId": "rev_q1",
      "userAnswer": "He said the obligations were to his family and to his people.",
      "gemmaAnalysis": { "correctnessScore": 4, "originalityScore": 3 }
    },
    {
      "questionId": "rev_q2",
      "userAnswer": "He thought freedom was just for him, but then saw it was for everyone.",
      "gemmaAnalysis": { "correctnessScore": 3, "originalityScore": 4 }
    }
  ],
  "chatHistory": [
    {
      "chatId": "chat_001",
      "question": "What does apartheid mean?",
      "gemmaAnalysis": { "curiosityScore": 1 }
    },
    {
      "chatId": "chat_002",
      "question": "How did his idea of freedom change over time?",
      "gemmaAnalysis": { "curiosityScore": 5 }
    }
  ]
}
```
    

### 2. The Interest Scoring Algorithm

The algorithm now uses the simplified two-part evaluation for revision answers.

Formula:
`InterestScore = (w_revision * RevisionScore) + (w_chat * ChatScore) + (w_notes * NoteScore) + (w_visits * VisitScore)

- **Suggested Weights:**
    - `w_revision`: **0.4**
    - `w_chat`: **0.3**
    - `w_notes`: **0.2**
    - `w_visits`: **0.1**
        
#### A. Calculating Component Scores

**I. Revision Score (`RevisionScore`) - Simplified Quality Analysis**

This score provides a streamlined yet powerful view of a student's understanding.

1. **Gemma Prompt for Analysis:** The prompt is updated to ask for the new `correctnessScore`
    
    > **Prompt:** "You are an expert teacher. A student was asked the following question: '`{original_question_text}`'. The student provided this answer: '`{user_answer_text}`'. Please evaluate the student's answer. Provide two scores in JSON format: `correctnessScore` (from 1=incorrect/incomplete to 5=fully correct and complete) and `originalityScore` (from 1=copied directly to 5=highly original thought)."
    
2. **Calculate Average:** The `RevisionScore` is the average of both scores across all answers for that chapter.
    
    - _Example from above:_ `(4 + 3 + 3 + 4) / 4 = 3.5`
        

**II. Chat Score (`ChatScore`)**

This remains a key signal for curiosity.

1. **Gemma Prompt for Analysis:**
    
    > **Prompt:** "Analyze this student's question... Assign a `curiosityScore` from 1 (basic) to 5 (very curious)..."
    
2. **Calculate Average:** The `ChatScore` is the average `curiosityScore` from all chats.
    
    - _Example from above:_ `(1 + 5) / 2 = 3.0`
        

**III. Note Score (`NoteScore`)**

This remains a simple, quantitative measure.

1. **Formula:** `NoteScore = min(noteCount, 5)`
    
    - _Example from above:_ `min(2, 5) = 2.0`
        

**IV. Visit Score (`VisitScore`)**

This also remains a simple, quantitative measure.

1. **Formula:** `VisitScore = min(visitCount, 5)`
    
    - _Example from above:_ `min(4, 5) = 4.0`
        

### 3. Final Calculation Example

Using the new data and weights:
- `RevisionScore` = 3.5
- `ChatScore` = 3.0
- `NoteScore` = 2.0
- `VisitScore` = 4.0
    

InterestScore = (0.4 * 3.5) + (0.3 * 3.0) + (0.2 * 2.0) + (0.1 * 4.0)

= 1.4 + 0.9 + 0.4 + 0.4

= 3.1

# Implementation

![[Screenshot 2025-07-23 at 17.21.18.png]]
