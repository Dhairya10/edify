# Quest Generation Logic
This document outlines the background process for automatically generating personalized quests. This system replaces the previous "unlock" mechanic with a continuous, threshold-based generation flow.

## The Trigger
The entire process is initiated whenever a user performs a "meaningful action" (adds a note, chats, or answers a revision question).

After the action, the InterestScore for the relevant chapter is recalculated.

The system then checks if InterestScore > 3.5 AND questGenerated == false.

If both conditions are true, the Quest Generation Flow begins.

## The Quest Generation Flow
This is a multi-step process that leverages the LLM (Gemma 3n) to make intelligent decisions.

### Step 1: Identify All High-Interest Chapters

The system scans the user's ChapterStats to find all chapters that meet the InterestScore > 3.5 condition and have not yet had a quest generated.

### Step 2: LLM Decides the Strategy

The system provides the LLM with a summary of the high-interest chapters.

The LLM is then asked to choose the best strategy for the user at this moment.

Chooser Prompt (System Instruction):

"You are an AI learning coach. The user is showing strong interest in the following topics: {List of chapter titles and their subjects}.

Your goal is to foster advanced thinking skills. Choose one of the following strategies:

'Convergent': If you see a clear and powerful opportunity to connect ideas between two different subjects.

'Divergent': If it's better to generate a very deep and thought-provoking question about a single topic.

Respond with only the chosen strategy name in JSON format: {\"strategy\": \"Convergent\", \"chapters\": [\"chapterId_1\", \"chapterId_2\"]} or {\"strategy\": \"Divergent\", \"chapter\": \"chapterId_1\"}."

### Step 3: Generate the Quest

Based on the LLM's chosen strategy, the system proceeds with one of the following paths.

#### Path A: Convergent Thinking Quest
Goal: Generate a single quest that connects two chapters, often from different subjects.

The system gathers all user interaction data (notes, chats, revision answers) for the two chapters chosen by the LLM.

Convergent Generation Prompt:

"You are a curriculum designer specializing in interdisciplinary studies. Generate a single, thought-provoking question that connects the core themes of {Chapter 1 Summary} ({Subject 1}) and {Chapter 2 Summary} ({Subject 2}).

Here is the user's specific engagement data to guide you:

Chapter 1 Data: {User interaction data for Chapter 1}

Chapter 2 Data: {User interaction data for Chapter 2}

Respond in JSON format: {\"title\": \"A Creative Title for the Quest\", \"questionText\": \"Your generated question.\"}"

#### Path B: Divergent Thinking Quest
Goal: Generate a single, deep quest about one chapter.

The system gathers all user interaction data for the single chapter chosen by the LLM.

Divergent Generation Prompt:

"You are an expert educator. Your goal is to foster creativity and critical thinking. Based on the user's interactions with the chapter {Chapter Summary} ({Subject}), generate a single, deep question that encourages them to think beyond the text. The question could relate to a core theme, a character's motivation, or a 'what if' scenario.

Here is the user's specific engagement data: {User interaction data for the chapter}

Respond in JSON format: {\"title\": \"A Creative Title for the Quest\", \"questionText\": \"Your generated question.\"}"

## Finalization
Save the Quest: The newly generated quest (title and question text) is saved to the user's Quest Board.

Update Flags: The questGenerated flag is set to true for the chapter(s) used in the generation. This is critical to prevent generating duplicate quests from the same high-interest signals.

This new system creates a sophisticated and autonomous learning companion that intelligently adapts to the user's journey, perfectly aligning with your vision.