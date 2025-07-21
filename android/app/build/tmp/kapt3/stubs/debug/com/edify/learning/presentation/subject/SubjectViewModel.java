package com.edify.learning.presentation.subject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0010\u001a\u00020\u0011J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0007J\u001e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r\u00a8\u0006\u001e"}, d2 = {"Lcom/edify/learning/presentation/subject/SubjectViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/edify/learning/data/repository/LearningRepository;", "(Lcom/edify/learning/data/repository/LearningRepository;)V", "_selectedMode", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/edify/learning/presentation/subject/SubjectMode;", "_uiState", "Lcom/edify/learning/presentation/subject/SubjectUiState;", "selectedMode", "Lkotlinx/coroutines/flow/StateFlow;", "getSelectedMode", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "clearError", "", "loadExercisesForSubject", "loadSubject", "subjectId", "", "onModeChanged", "mode", "updateUserResponse", "chapterId", "exerciseIndex", "", "response", "Lcom/edify/learning/data/model/UserResponse;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SubjectViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.LearningRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.subject.SubjectUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.subject.SubjectUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.subject.SubjectMode> _selectedMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.subject.SubjectMode> selectedMode = null;
    
    @javax.inject.Inject()
    public SubjectViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.LearningRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.subject.SubjectUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.subject.SubjectMode> getSelectedMode() {
        return null;
    }
    
    public final void loadSubject(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId) {
    }
    
    public final void onModeChanged(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.subject.SubjectMode mode) {
    }
    
    private final void loadExercisesForSubject() {
    }
    
    public final void updateUserResponse(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, int exerciseIndex, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.UserResponse response) {
    }
    
    public final void clearError() {
    }
}