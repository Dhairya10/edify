package com.edify.learning.data.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rH\'J \u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0016J.\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\u001b\u00a8\u0006\u001c"}, d2 = {"Lcom/edify/learning/data/database/TranslatedChapterDao;", "", "deleteAllTranslations", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAllTranslationsForChapter", "chapterId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteTranslatedChapter", "language", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllTranslatedChapters", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/edify/learning/data/model/TranslatedChapter;", "getTranslatedChapter", "getTranslationCount", "", "getTranslationsForChapter", "insertTranslatedChapter", "translatedChapter", "(Lcom/edify/learning/data/model/TranslatedChapter;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateTranslatedContent", "translatedContent", "updatedAt", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface TranslatedChapterDao {
    
    @androidx.room.Query(value = "SELECT * FROM translated_chapters WHERE chapterId = :chapterId AND language = :language")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTranslatedChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.TranslatedChapter> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertTranslatedChapter(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.TranslatedChapter translatedChapter, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE translated_chapters SET translatedContent = :translatedContent, updatedAt = :updatedAt WHERE chapterId = :chapterId AND language = :language")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateTranslatedContent(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    java.lang.String translatedContent, long updatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM translated_chapters WHERE chapterId = :chapterId AND language = :language")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteTranslatedChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM translated_chapters WHERE chapterId = :chapterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllTranslationsForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM translated_chapters")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.TranslatedChapter>> getAllTranslatedChapters();
    
    @androidx.room.Query(value = "DELETE FROM translated_chapters")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllTranslations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM translated_chapters")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTranslationCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM translated_chapters WHERE chapterId = :chapterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTranslationsForChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.TranslatedChapter>> $completion);
}