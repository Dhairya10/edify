package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.Note
import com.edify.learning.data.model.NoteType
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    
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
    
    @Query("DELETE FROM notes WHERE chapterId = :chapterId")
    suspend fun deleteNotesByChapter(chapterId: String)
    
    @Query("SELECT COUNT(*) FROM notes WHERE chapterId = :chapterId AND type = :type")
    suspend fun getNotesCountByType(chapterId: String, type: NoteType): Int
}
