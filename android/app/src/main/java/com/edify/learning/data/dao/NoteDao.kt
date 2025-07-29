package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.Note
import com.edify.learning.data.model.NoteType
import com.edify.learning.data.model.NoteWithSubjectInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    
    @Query("SELECT * FROM notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>
    
    @Query("SELECT * FROM notes WHERE type = 'USER_NOTE' ORDER BY createdAt DESC")
    fun getAllUserNotes(): Flow<List<Note>>
    
    @Query("SELECT * FROM notes WHERE type = 'USER_NOTE' AND chapterId IN (SELECT id FROM chapters WHERE subjectId = :subjectId) ORDER BY createdAt DESC")
    fun getUserNotesBySubject(subjectId: String): Flow<List<Note>>
    
    @Query("SELECT * FROM notes WHERE chapterId = :chapterId ORDER BY createdAt DESC")
    fun getNotesByChapter(chapterId: String): Flow<List<Note>>
    
    @Query("SELECT * FROM notes WHERE chapterId = :chapterId AND type = :type ORDER BY createdAt DESC")
    fun getNotesByChapterAndType(chapterId: String, type: NoteType): Flow<List<Note>>
    
    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: String): Note?
    
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<Note>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)
    
    @Update
    suspend fun updateNote(note: Note)
    
    @Delete
    suspend fun deleteNote(note: Note)
    
    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteById(id: Long)
    
    @Query("DELETE FROM notes WHERE chapterId = :chapterId")
    suspend fun deleteNotesByChapter(chapterId: String)
    
    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
    
    @Query("SELECT COUNT(*) FROM notes WHERE chapterId = :chapterId AND type = :type")
    suspend fun getNotesCountByType(chapterId: String, type: NoteType): Int
}
