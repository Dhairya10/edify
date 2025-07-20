package com.edify.learning.data.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\u0007J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0007J\u0016\u0010\f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/edify/learning/data/util/ContentParser;", "", "()V", "json", "Lkotlinx/serialization/json/Json$Default;", "extractImageReferences", "", "", "markdownText", "parseChapterContent", "Lcom/edify/learning/data/model/ChapterContent;", "jsonString", "replaceImagePaths", "baseImagePath", "app_debug"})
public final class ContentParser {
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.serialization.json.Json.Default json = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.edify.learning.data.util.ContentParser INSTANCE = null;
    
    private ContentParser() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.edify.learning.data.model.ChapterContent parseChapterContent(@org.jetbrains.annotations.NotNull()
    java.lang.String jsonString) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> extractImageReferences(@org.jetbrains.annotations.NotNull()
    java.lang.String markdownText) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String replaceImagePaths(@org.jetbrains.annotations.NotNull()
    java.lang.String markdownText, @org.jetbrains.annotations.NotNull()
    java.lang.String baseImagePath) {
        return null;
    }
}