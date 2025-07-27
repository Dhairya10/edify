package com.edify.learning.presentation.quest;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001a\u001e\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a2\u0010\u000b\u001a\u00020\u00012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\u0007\u001a\u001f\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\r2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007\u00a2\u0006\u0002\u0010\u0016\u001a(\u0010\u0017\u001a\u00020\u00012\b\b\u0002\u0010\u0018\u001a\u00020\u00192\u0014\b\u0002\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u00a8\u0006\u001b"}, d2 = {"EmptyQuestState", "", "introductoryQuestions", "", "Lcom/edify/learning/data/model/QuestQuestion;", "onQuestionClick", "Lkotlin/Function1;", "IntroductoryQuestionCard", "question", "onClick", "Lkotlin/Function0;", "PersonalizedQuestContent", "topChapters", "error/NonExistentClass", "onChapterClick", "", "isGeneratingQuestion", "", "QuestProgressSection", "progressData", "modifier", "Landroidx/compose/ui/Modifier;", "(Lerror/NonExistentClass;Landroidx/compose/ui/Modifier;)V", "QuestScreen", "viewModel", "Lcom/edify/learning/presentation/quest/QuestViewModel;", "onNavigateToQuestDetail", "app_release"})
public final class QuestScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void QuestScreen(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.quest.QuestViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToQuestDetail) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void QuestProgressSection(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass progressData, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyQuestState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.QuestQuestion> introductoryQuestions, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.edify.learning.data.model.QuestQuestion, kotlin.Unit> onQuestionClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void IntroductoryQuestionCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.QuestQuestion question, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PersonalizedQuestContent(@org.jetbrains.annotations.NotNull()
    java.util.List<error.NonExistentClass> topChapters, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onChapterClick, boolean isGeneratingQuestion) {
    }
}