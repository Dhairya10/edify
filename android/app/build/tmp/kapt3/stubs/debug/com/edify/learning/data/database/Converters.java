package com.edify.learning.data.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0011\u001a\u00020\u0006H\u0007J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/edify/learning/data/database/Converters;", "", "()V", "gson", "Lcom/google/gson/Gson;", "fromContentItemsList", "", "contentItems", "", "Lcom/edify/learning/data/model/ContentItem;", "fromMessageType", "messageType", "Lcom/edify/learning/data/model/MessageType;", "fromNoteType", "noteType", "Lcom/edify/learning/data/model/NoteType;", "toContentItemsList", "contentItemsString", "toMessageType", "toNoteType", "app_debug"})
public final class Converters {
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    
    public Converters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromNoteType(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.NoteType noteType) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.model.NoteType toNoteType(@org.jetbrains.annotations.NotNull()
    java.lang.String noteType) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromMessageType(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.MessageType messageType) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.edify.learning.data.model.MessageType toMessageType(@org.jetbrains.annotations.NotNull()
    java.lang.String messageType) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromContentItemsList(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.edify.learning.data.model.ContentItem> contentItems) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.edify.learning.data.model.ContentItem> toContentItemsList(@org.jetbrains.annotations.NotNull()
    java.lang.String contentItemsString) {
        return null;
    }
}