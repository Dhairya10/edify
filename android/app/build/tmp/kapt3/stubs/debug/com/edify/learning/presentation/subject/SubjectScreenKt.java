package com.edify.learning.presentation.subject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001aZ\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00120\u00112\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00010\u0014H\u0007\u001aL\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u000f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00122\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a.\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a*\u0010 \u001a\u00020\u00012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00030\"2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a.\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00010\u000e2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a0\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020\f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001aB\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020/2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b0\u00101\u001aJ\u00102\u001a\u00020\u00012\f\u00103\u001a\b\u0012\u0004\u0012\u00020\n0\"2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00120\u00112\u001e\u0010\u001d\u001a\u001a\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000104H\u0007\u001a>\u00105\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u000f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00122\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a2\u00106\u001a\u00020\u00012\u0006\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006;"}, d2 = {"ChapterCard", "", "chapter", "Lcom/edify/learning/data/model/Chapter;", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "CollapsibleChapterCard", "chapterExercises", "Lcom/edify/learning/presentation/subject/ChapterExercises;", "isExpanded", "", "onToggleExpanded", "Lkotlin/Function1;", "", "userResponses", "", "Lcom/edify/learning/data/model/UserResponse;", "onExerciseClick", "Lkotlin/Function2;", "Lcom/edify/learning/data/model/Exercise;", "", "ExerciseModal", "exercise", "exerciseIndex", "chapterTitle", "userResponse", "onDismiss", "onResponseChanged", "ExerciseQuestionCard", "isCompleted", "LearningModeContent", "chapters", "", "onChapterClick", "ModeToggle", "selectedMode", "Lcom/edify/learning/presentation/subject/SubjectMode;", "onModeChanged", "ModeToggleButton", "text", "isSelected", "RevisionCard", "title", "description", "backgroundColor", "Landroidx/compose/ui/graphics/Color;", "RevisionCard-XO-JAsU", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;)V", "RevisionModeContent", "exercises", "Lkotlin/Function3;", "SubjectExerciseCard", "SubjectScreen", "viewModel", "Lcom/edify/learning/presentation/subject/SubjectViewModel;", "onNavigateBack", "onNavigateToChapter", "app_debug"})
public final class SubjectScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SubjectScreen(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.subject.SubjectViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToChapter) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ModeToggle(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.subject.SubjectMode selectedMode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.edify.learning.presentation.subject.SubjectMode, kotlin.Unit> onModeChanged, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ModeToggleButton(@org.jetbrains.annotations.NotNull()
    java.lang.String text, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LearningModeContent(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.Chapter> chapters, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onChapterClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RevisionModeContent(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.presentation.subject.ChapterExercises> exercises, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, com.edify.learning.data.model.UserResponse> userResponses, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.Integer, ? super com.edify.learning.data.model.UserResponse, kotlin.Unit> onResponseChanged) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChapterCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Chapter chapter, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CollapsibleChapterCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.subject.ChapterExercises chapterExercises, boolean isExpanded, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onToggleExpanded, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, com.edify.learning.data.model.UserResponse> userResponses, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.edify.learning.data.model.Exercise, ? super java.lang.Integer, kotlin.Unit> onExerciseClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ExerciseQuestionCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Exercise exercise, int exerciseIndex, boolean isCompleted, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SubjectExerciseCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Exercise exercise, int exerciseIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterTitle, @org.jetbrains.annotations.Nullable()
    com.edify.learning.data.model.UserResponse userResponse, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.edify.learning.data.model.UserResponse, kotlin.Unit> onResponseChanged) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ExerciseModal(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Exercise exercise, int exerciseIndex, @org.jetbrains.annotations.NotNull()
    java.lang.String chapterTitle, @org.jetbrains.annotations.Nullable()
    com.edify.learning.data.model.UserResponse userResponse, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.edify.learning.data.model.UserResponse, kotlin.Unit> onResponseChanged) {
    }
}