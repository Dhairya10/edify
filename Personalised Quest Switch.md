### Quest Personalization: Activation Algorithm

This document provides the algorithm and execution strategy for determining when to switch from displaying "Introductory Quests" to "Personalized Quests" for a user.

### 1. The "Readiness" Algorithm

This algorithm checks if a user has met the required thresholds for personalization. It's designed to be fast and efficient, suitable for running frequently on the user's device.

#### **Data Requirements:**

- A local database table/collection (`ChapterStats`) that stores interaction data for each chapter, as defined in our previous blueprint [cite: quest-scoring-blueprint].
    
- A user profile or preference store where you can save a single boolean flag: `hasUnlockedPersonalizedQuests`.
    
#### **Pseudocode:**

```
FUNCTION checkPersonalizationReadiness(userId):

  // Step 1: Initialize counters
  totalMeaningfulActions = 0
  uniqueChaptersInteracted = new Set() // A set automatically handles uniqueness

  // Step 2: Retrieve all chapter stats for the user
  allChapterStats = getChapterStatsForUser(userId)

  // Step 3: Iterate and aggregate the data
  FOR each chapterStat IN allChapterStats:
    
    // Calculate actions for the current chapter
    actionsInThisChapter = chapterStat.noteCount + 
                           length(chapterStat.chatHistory) + 
                           length(chapterStat.revisionHistory)

    // If there's any interaction, add to totals
    IF actionsInThisChapter > 0:
      totalMeaningfulActions += actionsInThisChapter
      uniqueChaptersInteracted.add(chapterStat.chapterId)
    END IF

  END FOR

  // Step 4: Check against thresholds
  hasSufficientDepth = (totalMeaningfulActions >= 10)
  hasSufficientBreadth = (size(uniqueChaptersInteracted) >= 3)

  // Step 5: Return the final result
  RETURN hasSufficientDepth AND hasSufficientBreadth

END FUNCTION
```

### 2. The Execution Strategy

The best way to run this algorithm is to trigger it immediately after a user performs any of the "meaningful actions" you are tracking.

#### **When to Run the Check:**

You should call the `checkPersonalizationReadiness()` function after any of these events:

1. **A new note is saved.**
    
2. **A new question is sent in the chat.**
    
3. **A revision exercise answer is submitted.**
    

This ensures that the user's "readiness" status is always up-to-date.

#### **Implementation Flow (The "One-Time" Switch):**

To avoid running the calculation unnecessarily forever, you should use a one-time switch. This is the most important part of the execution strategy.

1. **Check the Flag First:** Before running the algorithm, always check the `hasUnlockedPersonalizedQuests` flag in the user's profile. If it's already `true`, do nothing.
    
2. **Run the Algorithm:** If the flag is `false`, run the `checkPersonalizationReadiness()` function.
    
3. **Update the Flag:** If the function returns `true`, immediately update the user's profile in your database:
    
    - Set `hasUnlockedPersonalizedQuests` to `true`.
        

This creates a highly efficient flow:

- **For new users:** The check runs after each relevant action.
    
- **The moment the threshold is met:** The flag is flipped to `true`.
    
- **For all subsequent sessions:** The app simply reads the `true` flag and no longer needs to run the readiness calculation, saving computational cycles.
    

When displaying the "Quest" screen, your app's logic will be:

```
IF user.hasUnlockedPersonalizedQuests == true:
  // Run the full Interest Scoring algorithm and show personalized quests
ELSE:
  // Show the "Introductory Quests" empty state
END IF
```