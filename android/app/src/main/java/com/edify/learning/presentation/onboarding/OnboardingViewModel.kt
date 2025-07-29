package com.edify.learning.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edify.learning.data.dao.UserProfileDao
import com.edify.learning.data.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userProfileDao: UserProfileDao
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()
    
    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }
    
    fun updateLanguage(language: String) {
        _uiState.value = _uiState.value.copy(selectedLanguage = language)
    }
    
    fun updateClass(classLevel: Int) {
        _uiState.value = _uiState.value.copy(selectedClass = classLevel)
    }
    
    fun nextStep() {
        val currentStep = _uiState.value.currentStep
        if (currentStep < OnboardingConstants.TOTAL_STEPS - 1) {
            _uiState.value = _uiState.value.copy(currentStep = currentStep + 1)
        }
    }
    
    fun previousStep() {
        val currentStep = _uiState.value.currentStep
        if (currentStep > 0) {
            _uiState.value = _uiState.value.copy(currentStep = currentStep - 1)
        }
    }
    
    fun canProceedFromCurrentStep(): Boolean {
        return when (_uiState.value.currentStep) {
            0 -> isNameValid(_uiState.value.name)
            1 -> _uiState.value.selectedLanguage.isNotEmpty()
            2 -> _uiState.value.selectedClass in OnboardingConstants.CLASS_RANGE
            else -> false
        }
    }
    
    fun isNameValid(name: String): Boolean {
        val trimmedName = name.trim()
        return trimmedName.length in 2..50
    }
    
    fun getNameValidationError(name: String): String? {
        val trimmedName = name.trim()
        return when {
            trimmedName.isEmpty() -> "Please enter your name"
            trimmedName.length < 2 -> "Name must be at least 2 characters"
            trimmedName.length > 50 -> "Name must be 50 characters or less"
            else -> null
        }
    }
    
    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                val userId = "user_${UUID.randomUUID()}"
                val userProfile = UserProfile(
                    userId = userId,
                    name = _uiState.value.name.trim(),
                    languagePreference = _uiState.value.selectedLanguage,
                    classLevel = _uiState.value.selectedClass,
                    hasCompletedOnboarding = true
                )
                
                userProfileDao.insertOrUpdateUserProfile(userProfile)
                
                _uiState.value = _uiState.value.copy(isLoading = false)
                onComplete()
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to save profile: ${e.message}"
                )
            }
        }
    }
    
    suspend fun checkOnboardingStatus(): Boolean {
        return try {
            val profiles = userProfileDao.getAllProfiles()
            profiles.any { it.hasCompletedOnboarding }
        } catch (e: Exception) {
            false
        }
    }
}
