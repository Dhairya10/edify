package com.edify.learning.presentation.onboarding;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a$\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00032\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a&\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a$\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a,\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00112\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0003\u001a \u0010\u0018\u001a\u00020\u00012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u00a8\u0006\u001a"}, d2 = {"ClassCard", "", "classLevel", "", "isSelected", "", "onSelect", "Lkotlin/Function0;", "ClassStep", "selectedClass", "onClassSelect", "Lkotlin/Function1;", "LanguageCard", "language", "Lcom/edify/learning/presentation/common/Language;", "LanguageStep", "selectedLanguage", "", "onLanguageSelect", "NameStep", "name", "onNameChange", "viewModel", "Lcom/edify/learning/presentation/onboarding/OnboardingViewModel;", "OnboardingScreen", "onOnboardingComplete", "app_debug"})
public final class OnboardingScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void OnboardingScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOnboardingComplete, @org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.onboarding.OnboardingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NameStep(java.lang.String name, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNameChange, com.edify.learning.presentation.onboarding.OnboardingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LanguageStep(java.lang.String selectedLanguage, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onLanguageSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LanguageCard(com.edify.learning.presentation.common.Language language, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ClassStep(int selectedClass, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onClassSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ClassCard(int classLevel, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onSelect) {
    }
}