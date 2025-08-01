package com.edify.learning.presentation.profile;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0006\u0010\u0010\u001a\u00020\u000fJ\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0019"}, d2 = {"Lcom/edify/learning/presentation/profile/ProfileViewModel;", "Landroidx/lifecycle/ViewModel;", "userProfileRepository", "Lcom/edify/learning/data/repository/UserProfileRepository;", "learningRepository", "Lcom/edify/learning/data/repository/LearningRepository;", "(Lcom/edify/learning/data/repository/UserProfileRepository;Lcom/edify/learning/data/repository/LearningRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/edify/learning/presentation/profile/ProfileUiState;", "userProfileState", "Lkotlinx/coroutines/flow/StateFlow;", "getUserProfileState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadUserProfile", "", "onClearDataClicked", "onEditFieldRequested", "field", "Lcom/edify/learning/presentation/profile/ProfileField;", "updateLanguage", "language", "", "updateName", "name", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ProfileViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.UserProfileRepository userProfileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.edify.learning.data.repository.LearningRepository learningRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.edify.learning.presentation.profile.ProfileUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.profile.ProfileUiState> userProfileState = null;
    
    @javax.inject.Inject()
    public ProfileViewModel(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.UserProfileRepository userProfileRepository, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.repository.LearningRepository learningRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.edify.learning.presentation.profile.ProfileUiState> getUserProfileState() {
        return null;
    }
    
    private final void loadUserProfile() {
    }
    
    public final void updateName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updateLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    public final void onEditFieldRequested(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.profile.ProfileField field) {
    }
    
    public final void onClearDataClicked() {
    }
}