package com.edify.learning.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.model.UserProfile
import com.edify.learning.data.repository.UserProfileRepository
import com.edify.learning.data.repository.LearningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ProfileField {
    NAME, LANGUAGE, CLASS
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val learningRepository: LearningRepository
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
    
    fun updateClass(classLevel: Int) {
        viewModelScope.launch {
            val currentProfile = _uiState.value.userProfile ?: return@launch
            val updatedProfile = currentProfile.copy(
                classLevel = classLevel,
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
                
                // Reinitialize content data with clean state
                learningRepository.initializeContentData()
                
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
}
