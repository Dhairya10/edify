package com.edify.learning.presentation.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000X\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u001aF\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00032\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a\u0016\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000fH\u0003\u001a\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0003H\u0003\u001at\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0014\b\u0002\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\u001a\b\u0002\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u0003H\u0007\u001aP\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\u0014\b\u0002\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a*\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f2\u0006\u0010 \u001a\u00020\u00032\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u000f2\u0006\u0010\u0015\u001a\u00020\u0003\u00a8\u0006#"}, d2 = {"MarkdownHeader", "", "text", "", "level", "", "MarkdownImage", "imagePath", "onImageClick", "Lkotlin/Function1;", "onContentSelected", "isSelected", "", "MarkdownList", "items", "", "MarkdownMath", "expression", "MarkdownRenderer", "content", "Lcom/edify/learning/data/model/ChapterContent;", "baseImagePath", "modifier", "Landroidx/compose/ui/Modifier;", "onTextSelected", "Lkotlin/Function2;", "selectedContent", "MarkdownText", "style", "Landroidx/compose/ui/text/TextStyle;", "processMarkdownWithImages", "Lcom/edify/learning/presentation/components/MarkdownElement;", "markdownText", "images", "Lcom/edify/learning/data/model/ImageMetadata;", "app_release"})
public final class MarkdownRendererKt {
    
    @androidx.compose.runtime.Composable()
    public static final void MarkdownRenderer(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.ChapterContent content, @org.jetbrains.annotations.NotNull()
    java.lang.String baseImagePath, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTextSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onImageClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onContentSelected, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedContent) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MarkdownText(java.lang.String text, androidx.compose.ui.text.TextStyle style, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTextSelected, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onContentSelected, boolean isSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MarkdownImage(java.lang.String imagePath, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onImageClick, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onContentSelected, boolean isSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MarkdownHeader(java.lang.String text, int level) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MarkdownList(java.util.List<java.lang.String> items) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MarkdownMath(java.lang.String expression) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.edify.learning.presentation.components.MarkdownElement> processMarkdownWithImages(@org.jetbrains.annotations.NotNull()
    java.lang.String markdownText, @org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.ImageMetadata> images, @org.jetbrains.annotations.NotNull()
    java.lang.String baseImagePath) {
        return null;
    }
}