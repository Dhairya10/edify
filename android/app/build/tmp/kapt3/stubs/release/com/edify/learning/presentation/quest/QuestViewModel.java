package com.edify.learning.presentation.quest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\rH\u0002J\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017J\u0006\u0010\u0019\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001a"}, d2 = {"Lcom/edify/learning/presentation/quest/QuestViewModel;", "Landroidx/lifecycle/ViewModel;", "questRepository", "Lcom/edify/learning/data/repository/QuestRepository;", "(Lcom/edify/learning/data/repository/QuestRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/edify/learning/presentation/quest/QuestUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearSelectedQuest", "", "getQuestDisplayItem", "Lcom/edify/learning/presentation/quest/QuestDisplayItem;", "quest", "Lcom/edify/learning/data/model/GeneratedQuest;", "(Lcom/edify/learning/data/model/GeneratedQuest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadQuests", "selectQuest", "submitQuestAnswer", "questId", "", "answer", "triggerQuestGeneration", "app_release"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QuestViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.QuestRepository questRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.quest.QuestUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.quest.QuestUiState> uiState = null;
    
    @javax.inject.Inject()
    public QuestViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.QuestRepository questRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.quest.QuestUiState> getUiState() {
        return null;
    }
    
    /**
     * Load all quests for the user
     */
    private final void loadQuests() {
    }
    
    /**
     * Select a quest to view details
     */
    public final void selectQuest(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.GeneratedQuest quest) {
    }
    
    /**
     * Clear selected quest
     */
    public final void clearSelectedQuest() {
    }
    
    /**
     * Submit answer for a quest
     */
    public final void submitQuestAnswer(@org.jetbrains.annotations.NotNull()
    java.lang.String questId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer) {
    }
    
    /**
     * Trigger quest generation manually
     */
    public final void triggerQuestGeneration() {
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