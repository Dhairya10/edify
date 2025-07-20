package com.edify.learning.presentation.subject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000N\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a*\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a.\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a0\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001aB\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a\b\u0010\u001d\u001a\u00020\u0001H\u0007\u001a2\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\fH\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006#"}, d2 = {"ChapterCard", "", "chapter", "Lcom/edify/learning/data/model/Chapter;", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "LearningModeContent", "chapters", "", "onChapterClick", "Lkotlin/Function1;", "", "ModeToggle", "selectedMode", "Lcom/edify/learning/presentation/subject/SubjectMode;", "onModeChanged", "ModeToggleButton", "text", "isSelected", "", "RevisionCard", "title", "description", "backgroundColor", "Landroidx/compose/ui/graphics/Color;", "RevisionCard-XO-JAsU", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/jvm/functions/Function0;Landroidx/compose/ui/Modifier;)V", "RevisionModeContent", "SubjectScreen", "viewModel", "Lcom/edify/learning/presentation/subject/SubjectViewModel;", "onNavigateBack", "onNavigateToChapter", "app_debug"})
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
    public static final void RevisionModeContent() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChapterCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Chapter chapter, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
}