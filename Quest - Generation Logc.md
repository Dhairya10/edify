# Quest Generation Logic
This document outlines the background process for automatically generating personalized quests using a **divergent-first approach** with intelligent LLM-based chapter selection.

## The Trigger
The entire process is initiated whenever a user performs a "meaningful action" (adds a note, chats, or answers a revision question).

After the action, the InterestScore for the relevant chapter is recalculated using the formula:
**InterestScore = (0.15 × RevisionScore) + (0.4 × ChatScore) + (0.25 × NoteScore) + (0.2 × VisitScore)**

The system then checks if **InterestScore > 1.0** for quest generation eligibility.

## The Quest Generation Flow
This is a **two-phase process** that prioritizes individual chapter exploration before cross-subject synthesis:

### Phase 1: Divergent Quest Generation (Priority)

#### Step 1: Identify Eligible Chapters for Divergent Quests
The system scans ChapterStats to find all chapters that meet:
- **InterestScore > 1.0** 
- **divergentQuestGenerated == false**

#### Step 2: Generate Divergent Quests
For each eligible chapter, the system:
1. Gathers chapter information (title, subject, and description)
2. Generates a **single, deep exploration quest** for that specific chapter
3. Marks the chapter as having a divergent quest generated

**Divergent Quest Generation Prompt:**

```
"You are an expert educator. Your goal is to foster creativity and critical thinking. Generate a single, deep question for the chapter "{Chapter Title}" ({Subject}) that encourages students to think beyond the text.

Chapter Summary: {Chapter Description}


The question should be a DIVERGENT quest that encourages deep exploration of this specific topic. It could relate to a core theme, implications, or a 'what if' scenario.

IMPORTANT: You MUST respond with ONLY valid JSON in this exact format:
{\"title\": \"A Creative Title for the Quest\", \"questionText\": \"Your generated question.\"}

Do not include any other text, explanations, or formatting. Only return the JSON object."
```

### Phase 2: Convergent Quest Generation (Secondary)

#### Step 3: Check for Convergent Quest Opportunity
If **no new divergent quests were generated** in Phase 1, the system proceeds to convergent quest generation.

#### Step 4: LLM-Based Chapter Selection for Convergent Quests
The system:
1. Identifies all chapters with **InterestScore > 1.0**
2. If ≤10 chapters: passes all to LLM
3. If >10 chapters: randomly samples 10 chapters
4. Presents chapter options to LLM with metadata (title, subject, interest score)

#### Step 5: Generate Convergent Quest
The LLM intelligently selects **exactly 2 chapters** for optimal cross-subject synthesis and generates a convergent question.

**Convergent Quest Generation Prompt:**

```
"You are a curriculum designer specializing in interdisciplinary studies. Your task is to:

1. SELECT exactly 2 chapters from the provided options that offer the best opportunity for meaningful cross-subject synthesis
2. GENERATE a thought-provoking convergent question that connects these chapters

Available chapters:
{List of up to 10 eligible chapters with metadata: ID, title, subject, interest score}

Past quest questions for context (to avoid repetition):
{Past quest questions for involved chapters}

IMPORTANT: You MUST respond with ONLY valid JSON in this exact format:
{
  \"selectedChapterIds\": [\"chapterId_1\", \"chapterId_2\"],
  \"title\": \"A Creative Title for the Quest\",
  \"questionText\": \"Your generated convergent question connecting the two selected chapters.\"
}

Do not include any other text, explanations, or formatting. Only return the JSON object."
```

## Finalization Process

### For Divergent Quests:
1. Save quest to database with `questType = "divergent"` and single chapter ID
2. Update `ChapterStats.divergentQuestGenerated = true` for the chapter
3. Quest appears on user's Quest Board

### For Convergent Quests:
1. Save quest to database with `questType = "convergent"` and selected chapter IDs
2. Update `ChapterStats.questGenerated = true` for both involved chapters
3. Quest appears on user's Quest Board

## Algorithm Flow Summary

```
User Action → Interest Score Recalculation → Quest Generation Check
    ↓
Phase 1: Generate Divergent Quests (for eligible chapters without divergent quests)
    ↓
If no divergent quests generated:
    ↓
Phase 2: Generate Convergent Quest (LLM selects 2 chapters from up to 10 options)
    ↓
Save quest and update chapter tracking
```

This system creates an intelligent, adaptive learning companion that prioritizes deep individual exploration while fostering meaningful cross-subject connections through LLM-driven chapter selection.