package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.GeneratedQuest
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneratedQuestDao {
    
    @Query("SELECT * FROM generated_quests WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAllQuests(userId: String = "default_user"): Flow<List<GeneratedQuest>>
    
    @Query("SELECT * FROM generated_quests WHERE id = :questId")
    suspend fun getQuestById(questId: String): GeneratedQuest?
    
    @Query("SELECT * FROM generated_quests WHERE userId = :userId AND isCompleted = 0 ORDER BY createdAt DESC")
    fun getIncompleteQuests(userId: String = "default_user"): Flow<List<GeneratedQuest>>
    
    @Query("SELECT * FROM generated_quests WHERE userId = :userId AND isCompleted = 1 ORDER BY completedAt DESC")
    fun getCompletedQuests(userId: String = "default_user"): Flow<List<GeneratedQuest>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuest(quest: GeneratedQuest)
    
    @Update
    suspend fun updateQuest(quest: GeneratedQuest)
    
    @Query("UPDATE generated_quests SET isCompleted = 1, userAnswer = :answer, completedAt = :completedAt WHERE id = :questId")
    suspend fun completeQuest(questId: String, answer: String, completedAt: Long = System.currentTimeMillis())
    
    @Delete
    suspend fun deleteQuest(quest: GeneratedQuest)
    
    @Query("DELETE FROM generated_quests WHERE id = :questId")
    suspend fun deleteQuestById(questId: String)
    
    @Query("SELECT COUNT(*) FROM generated_quests WHERE userId = :userId")
    suspend fun getQuestCount(userId: String = "default_user"): Int
    
    @Query("DELETE FROM generated_quests")
    suspend fun deleteAllQuests()
}
