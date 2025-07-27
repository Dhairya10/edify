package com.edify.learning.presentation.quest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0017J\b\u0010\u001a\u001a\u00020\u000fH\u0002J\u0006\u0010\u001b\u001a\u00020\u000fJ\u0006\u0010\u001c\u001a\u00020\u000fJ\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0017J\u0006\u0010 \u001a\u00020\u000fJ\u000e\u0010!\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0017R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\""}, d2 = {"Lcom/edify/learning/presentation/quest/QuestViewModel;", "Landroidx/lifecycle/ViewModel;", "questRepository", "Lcom/edify/learning/data/repository/QuestRepository;", "questGenerationService", "Lcom/edify/learning/data/service/QuestGenerationService;", "(Lcom/edify/learning/data/repository/QuestRepository;Lcom/edify/learning/data/service/QuestGenerationService;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/edify/learning/presentation/quest/QuestUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearSelectedQuest", "", "getQuestDisplayItem", "Lcom/edify/learning/presentation/quest/QuestDisplayItem;", "quest", "Lcom/edify/learning/data/model/GeneratedQuest;", "(Lcom/edify/learning/data/model/GeneratedQuest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadQuestDetail", "questId", "", "loadQuestDetailByChapterId", "chapterId", "loadQuests", "onMeaningfulUserAction", "refreshQuestData", "selectQuest", "submitQuestAnswer", "answer", "triggerQuestGeneration", "unlockQuest", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QuestViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.QuestRepository questRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.service.QuestGenerationService questGenerationService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.quest.QuestUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.quest.QuestUiState> uiState = null;
    
    @javax.inject.Inject()
    public QuestViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.QuestRepository questRepository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.service.QuestGenerationService questGenerationService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.quest.QuestUiState> getUiState() {
        return null;
    }
    
    /**
     * Load all quests for the user
     * Displays only actual generated quests from the database
     */
    private final void loadQuests() {
    }
    
    /**
     * Select a quest to view details
     */
    public final void selectQuest(@org.jetbrains.annotations.NotNull()
    java.lang.String questId) {
    }
    
    /**
     * Clear selected quest
     */
    public final void clearSelectedQuest() {
    }
    
    /**
     * Called after meaningful user actions (notes, chats, revision answers)
     * Triggers quest generation based on updated interest scores
     * Quest generation runs in background and survives navigation
     */
    public final void onMeaningfulUserAction() {
    }
    
    /**
     * Trigger quest generation manually
     * This is a more immediate trigger for testing/debugging
     */
    public final void triggerQuestGeneration() {
    }
    
    /**
     * Refresh quest data - called when QuestScreen becomes visible
     */
    public final void refreshQuestData() {
    }
    
    /**
     * Unlock a quest by updating its state in the database
     */
    public final void unlockQuest(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
    }
    
    /**
     * Load quest detail for a specific quest ID
     */
    public final void loadQuestDetail(@org.jetbrains.annotations.NotNull()
    java.lang.String questId) {
    }
    
    /**
     * Load quest detail by chapter ID (for PersonalizedChapterQuest navigation)
     */
    public final void loadQuestDetailByChapterId(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
    }
    
    /**
     * Submit answer for a quest
     */
    public final void submitQuestAnswer(@org.jetbrains.annotations.NotNull()
    java.lang.String questId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer) {
    }
    
    /**
     * Get display information for a quest
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQuestDisplayItem(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.GeneratedQuest quest, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.presentation.quest.QuestDisplayItem> $completion) {
        return null;
    }
}