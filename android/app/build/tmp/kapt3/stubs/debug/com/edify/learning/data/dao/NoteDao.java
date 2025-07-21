package com.edify.learning.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\fH\'J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0010\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\b\u001a\u00020\tH\'J$\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\'J\u001e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u0019\u001a\u00020\tH\'J\u0016\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u001b\u001a\u00020\u00032\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00050\rH\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\r0\f2\u0006\u0010\u001f\u001a\u00020\tH\'J\u0016\u0010 \u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006!"}, d2 = {"Lcom/edify/learning/data/dao/NoteDao;", "", "deleteNote", "", "note", "Lcom/edify/learning/data/model/Note;", "(Lcom/edify/learning/data/model/Note;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteNotesByChapter", "chapterId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNotes", "Lkotlinx/coroutines/flow/Flow;", "", "getAllUserNotes", "getNoteById", "id", "getNotesByChapter", "getNotesByChapterAndType", "type", "Lcom/edify/learning/data/model/NoteType;", "getNotesCountByType", "", "(Ljava/lang/String;Lcom/edify/learning/data/model/NoteType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserNotesBySubject", "subjectId", "insertNote", "insertNotes", "notes", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchNotes", "query", "updateNote", "app_debug"})
@androidx.room.Dao()
public abstract interface NoteDao {
    
    @androidx.room.Query(value = "SELECT * FROM notes ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getAllNotes();
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE type = \'USER_NOTE\' ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getAllUserNotes();
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE type = \'USER_NOTE\' AND chapterId IN (SELECT id FROM chapters WHERE subjectId = :subjectId) ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getUserNotesBySubject(@org.jetbrains.annotations.NotNull()
    java.lang.String subjectId);
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE chapterId = :chapterId ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getNotesByChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId);
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE chapterId = :chapterId AND type = :type ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> getNotesByChapterAndType(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.NoteType type);
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNoteById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.Note> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM notes WHERE title LIKE \'%\' || :query || \'%\' OR content LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.edify.learning.data.model.Note>> searchNotes(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertNote(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertNotes(@org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.data.model.Note> notes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateNote(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteNote(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM notes WHERE chapterId = :chapterId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteNotesByChapter(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM notes WHERE chapterId = :chapterId AND type = :type")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNotesCountByType(@org.jetbrains.annotations.NotNull()
    java.lang.String chapterId, @org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.NoteType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}