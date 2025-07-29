package com.edify.learning.data.dao

import androidx.room.*
import com.edify.learning.data.model.UserProfile

@Dao
interface UserProfileDao {
    
    @Query("SELECT * FROM user_profile ORDER BY createdAt DESC")
    suspend fun getAllProfiles(): List<UserProfile>
    
    @Query("SELECT * FROM user_profile WHERE userId = :userId")
    suspend fun getUserProfile(userId: String): UserProfile?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProfile(userProfile: UserProfile)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(userProfile: UserProfile)
    
    @Query("DELETE FROM user_profile WHERE userId = :id")
    suspend fun deleteById(id: String)
    
    @Query("UPDATE user_profile SET hasUnlockedPersonalizedQuests = :hasUnlocked, updatedAt = :timestamp WHERE userId = :userId")
    suspend fun updatePersonalizationStatus(userId: String, hasUnlocked: Boolean, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE user_profile SET name = :name, languagePreference = :language, classLevel = :classLevel, hasCompletedOnboarding = :completed, updatedAt = :timestamp WHERE userId = :userId")
    suspend fun updateOnboardingInfo(userId: String, name: String, language: String, classLevel: Int, completed: Boolean, timestamp: Long = System.currentTimeMillis())
}
