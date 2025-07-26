package com.edify.learning.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0007J\u0016\u0010\n\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u000b0\bH\u0007J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0007J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/edify/learning/data/model/ChapterStatsConverters;", "", "()V", "gson", "Lcom/google/gson/Gson;", "fromChatEntryList", "", "value", "", "Lcom/edify/learning/data/model/ChatEntry;", "fromRevisionEntryList", "Lcom/edify/learning/data/model/RevisionEntry;", "toChatEntryList", "toRevisionEntryList", "app_release"})
public final class ChapterStatsConverters {
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    
    public ChapterStatsConverters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromRevisionEntryList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.RevisionEntry> value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.RevisionEntry> toRevisionEntryList(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromChatEntryList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.ChatEntry> value) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.ChatEntry> toChatEntryList(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
        return null;
    }
}