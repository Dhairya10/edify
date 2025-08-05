package com.edify.learning.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.Constraints
import com.edify.learning.data.model.UserProfile
import com.edify.learning.data.repository.UserProfileRepository
import com.edify.learning.data.repository.LearningRepository
import com.edify.learning.data.service.QuestGenerationService
import com.edify.learning.data.worker.DailyQuestWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

enum class ProfileField {
    NAME, LANGUAGE
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val error: String? = null,
    val isGeneratingQuest: Boolean = false,
    val questGenerationMessage: String? = null,
    val questGenerationHour: Int = 18, // Default 6 PM (18:00)
    val questGenerationMinute: Int = 0 // Default :00 minutes
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val learningRepository: LearningRepository,
    private val questGenerationService: QuestGenerationService,
    private val workManager: WorkManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val userProfileState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    init {
        loadUserProfile()
    }
    
    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val user = userProfileRepository.getCurrentUser()
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        userProfile = user,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to load profile: ${e.message}"
                    )
                }
            }
        }
    }
    
    fun updateName(name: String) {
        viewModelScope.launch {
            val currentProfile = _uiState.value.userProfile ?: return@launch
            val updatedProfile = currentProfile.copy(
                name = name,
                updatedAt = System.currentTimeMillis()
            )
            userProfileRepository.updateUserProfile(updatedProfile)
            _uiState.update { it.copy(userProfile = updatedProfile) }
        }
    }
    
    fun updateLanguage(language: String) {
        viewModelScope.launch {
            val currentProfile = _uiState.value.userProfile ?: return@launch
            val updatedProfile = currentProfile.copy(
                languagePreference = language,
                updatedAt = System.currentTimeMillis()
            )
            userProfileRepository.updateUserProfile(updatedProfile)
            _uiState.update { it.copy(userProfile = updatedProfile) }
        }
    }
    

    
    fun onEditFieldRequested(field: ProfileField) {
        // For future implementation of name editing
        // For now, language and class use dialogs directly
    }
    
    fun onClearDataClicked() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                
                // Clear all app data
                learningRepository.clearAllData()
                
                // Reset user profile to trigger onboarding
                val currentProfile = _uiState.value.userProfile
                if (currentProfile != null) {
                    val resetProfile = currentProfile.copy(
                        hasCompletedOnboarding = false,
                        updatedAt = System.currentTimeMillis()
                    )
                    userProfileRepository.updateUserProfile(resetProfile)
                    _uiState.update { it.copy(userProfile = resetProfile, isLoading = false) }
                } else {
                    _uiState.update { it.copy(isLoading = false) }
                }
                
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to clear data: ${e.message}"
                    )
                }
            }
        }
    }
    
    // TEMP: Quest generation trigger for testing (remove before deployment)
    fun triggerQuestGeneration() {
        viewModelScope.launch {
            try {
                _uiState.update { 
                    it.copy(
                        isGeneratingQuest = true, 
                        questGenerationMessage = "Generating quest..."
                    ) 
                }
                
                questGenerationService.checkAndGenerateQuests()
                
                _uiState.update { 
                    it.copy(
                        isGeneratingQuest = false, 
                        questGenerationMessage = "Quest generated successfully!"
                    ) 
                }
                
                // Clear message after 3 seconds
                kotlinx.coroutines.delay(3000)
                _uiState.update { it.copy(questGenerationMessage = null) }
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isGeneratingQuest = false, 
                        questGenerationMessage = "Quest generation failed: ${e.message}"
                    ) 
                }
                
                // Clear error message after 5 seconds
                kotlinx.coroutines.delay(5000)
                _uiState.update { it.copy(questGenerationMessage = null) }
            }
        }
    }
    
    fun updateQuestGenerationTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            _uiState.update { 
                it.copy(
                    questGenerationHour = hour,
                    questGenerationMinute = minute
                )
            }
            rescheduleQuestGeneration(hour, minute)
        }
    }
    
    private fun rescheduleQuestGeneration(hour: Int, minute: Int) {
        try {
            // Cancel existing work
            workManager.cancelUniqueWork(DailyQuestWorker.WORK_NAME)
            
            // Calculate delay until the new time
            val delay = calculateDelayUntilTime(hour, minute)
            
            // Create new work request
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            val dailyWork = PeriodicWorkRequestBuilder<DailyQuestWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()

            workManager.enqueueUniquePeriodicWork(
                DailyQuestWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE, // Replace the existing one
                dailyWork
            )
            
            println("ProfileViewModel: Quest generation rescheduled for ${String.format("%02d:%02d", hour, minute)} daily")
            
        } catch (e: Exception) {
            println("ProfileViewModel: Failed to reschedule quest generation: ${e.message}")
        }
    }
    
    private fun calculateDelayUntilTime(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            
            // If it's already past the target time today, schedule for tomorrow
            if (before(now)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        
        return target.timeInMillis - now.timeInMillis
    }
}
