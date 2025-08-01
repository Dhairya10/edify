package com.edify.learning.presentation.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000`\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u001a\u0016\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\bH\u0003\u001av\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000f2\u001a\b\u0002\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00122\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000fH\u0007\u001ap\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u00182\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000f2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0019\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u00122\u0014\b\u0002\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000fH\u0003\u001a\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\b2\u0006\u0010\u001c\u001a\u00020\u0003\u001a\u0018\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002\u001a\u0018\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020!H\u0002\u001a\u001e\u0010%\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001f2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001b0\'H\u0002\u00a8\u0006("}, d2 = {"HtmlHeader", "", "text", "", "level", "", "HtmlList", "items", "", "HtmlRenderer", "content", "Lcom/edify/learning/data/model/ChapterContent;", "modifier", "Landroidx/compose/ui/Modifier;", "onTextSelected", "Lkotlin/Function1;", "onContentSelected", "Lkotlin/Function2;", "", "selectedContent", "isSelectionMode", "onParagraphSelected", "HtmlText", "style", "Landroidx/compose/ui/text/TextStyle;", "isSelected", "parseHtmlToElements", "Lcom/edify/learning/presentation/components/HtmlElement;", "htmlContent", "processElementForAnnotation", "element", "Lorg/jsoup/nodes/Element;", "builder", "Landroidx/compose/ui/text/AnnotatedString$Builder;", "processHtmlFormatting", "htmlText", "annotatedStringBuilder", "processHtmlNode", "elements", "", "app_debug"})
public final class HtmlRendererKt {
    
    @androidx.compose.runtime.Composable()
    public static final void HtmlRenderer(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.ChapterContent content, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTextSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Boolean, kotlin.Unit> onContentSelected, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedContent, boolean isSelectionMode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onParagraphSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HtmlText(java.lang.String text, androidx.compose.ui.text.TextStyle style, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onTextSelected, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onContentSelected, boolean isSelected, boolean isSelectionMode, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onParagraphSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HtmlHeader(java.lang.String text, int level) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HtmlList(java.util.List<java.lang.String> items) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.edify.learning.presentation.components.HtmlElement> parseHtmlToElements(@org.jetbrains.annotations.NotNull()
    java.lang.String htmlContent) {
        return null;
    }
    
    private static final void processHtmlNode(org.jsoup.nodes.Element element, java.util.List<com.edify.learning.presentation.components.HtmlElement> elements) {
    }
    
    private static final void processHtmlFormatting(java.lang.String htmlText, androidx.compose.ui.text.AnnotatedString.Builder annotatedStringBuilder) {
    }
    
    private static final void processElementForAnnotation(org.jsoup.nodes.Element element, androidx.compose.ui.text.AnnotatedString.Builder builder) {
    }
}