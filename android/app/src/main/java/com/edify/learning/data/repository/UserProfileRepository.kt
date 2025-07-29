package com.edify.learning.data.repository

import com.edify.learning.data.dao.UserProfileDao
import com.edify.learning.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepository @Inject constructor(
    private val userProfileDao: UserProfileDao
) {
    
    suspend fun getCurrentUser(): UserProfile? {
        return try {
            val profiles = userProfileDao.getAllProfiles()
            profiles.firstOrNull { it.hasCompletedOnboarding }
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun getUserName(): String {
        return getCurrentUser()?.name?.takeIf { it.isNotBlank() } ?: "there"
    }
    
    suspend fun getUserLanguage(): String {
        return getCurrentUser()?.languagePreference ?: "English"
    }
    
    suspend fun getUserClass(): Int {
        return getCurrentUser()?.classLevel ?: 1
    }
    
    fun getCurrentUserFlow(): Flow<UserProfile?> = flow {
        emit(getCurrentUser())
    }
    
    suspend fun updateUserProfile(userProfile: UserProfile) {
        userProfileDao.insertOrUpdateUserProfile(userProfile)
    }
}
