package com.edify.learning.presentation.developer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0013"}, d2 = {"Lcom/edify/learning/presentation/developer/QuestScoringViewModel;", "Landroidx/lifecycle/ViewModel;", "questScoringService", "Lcom/edify/learning/domain/service/QuestScoringService;", "(Lcom/edify/learning/domain/service/QuestScoringService;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/edify/learning/presentation/developer/QuestScoringUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "hideDetailedScoring", "", "loadQuestScoring", "refreshData", "showDetailedScoring", "chapterId", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QuestScoringViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.domain.service.QuestScoringService questScoringService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.developer.QuestScoringUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.developer.QuestScoringUiState> uiState = null;
    
    @javax.inject.Inject()
    public QuestScoringViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.domain.service.QuestScoringService questScoringService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.developer.QuestScoringUiState> getUiState() {
        return null;
    }
    
    public final void loadQuestScoring() {
    }
    
    public final void refreshData() {
    }
    
    public final void showDetailedScoring(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId) {
    }
    
    public final void hideDetailedScoring() {
    }
}