package com.edify.learning.presentation.revision;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001ah\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a\u0010\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\nH\u0003\u001a0\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0007\u00a8\u0006\u001b"}, d2 = {"ExerciseCard", "", "exercise", "Lcom/edify/learning/data/model/Exercise;", "exerciseIndex", "", "userResponse", "Lcom/edify/learning/data/model/UserResponse;", "revisionSubmissions", "", "Lcom/edify/learning/data/model/RevisionSubmission;", "isEvaluating", "", "onResponseChanged", "Lkotlin/Function1;", "onSubmitForEvaluation", "Lkotlin/Function0;", "onShowHistory", "HistoryItem", "submission", "RevisionScreen", "chapterId", "", "chapterTitle", "onNavigateBack", "viewModel", "Lcom/edify/learning/presentation/revision/RevisionViewModel;", "app_debug"})
public final class RevisionScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void RevisionScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterTitle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.revision.RevisionViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ExerciseCard(com.edify.learning.data.model.Exercise exercise, int exerciseIndex, com.edify.learning.data.model.UserResponse userResponse, java.util.List<com.edify.learning.data.model.RevisionSubmission> revisionSubmissions, boolean isEvaluating, kotlin.jvm.functions.Function1<? super com.edify.learning.data.model.UserResponse, kotlin.Unit> onResponseChanged, kotlin.jvm.functions.Function0<kotlin.Unit> onSubmitForEvaluation, kotlin.jvm.functions.Function0<kotlin.Unit> onShowHistory) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HistoryItem(com.edify.learning.data.model.RevisionSubmission submission) {
    }
}