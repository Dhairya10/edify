package com.edify.learning.presentation.onboarding

import com.edify.learning.presentation.common.AppConstants
import com.edify.learning.presentation.common.Language

data class OnboardingUiState(
    val currentStep: Int = 0,
    val name: String = "",
    val selectedLanguage: String = "English",
    val isLoading: Boolean = false,
    val error: String? = null
)

object OnboardingConstants {
    val SUPPORTED_LANGUAGES = AppConstants.SUPPORTED_LANGUAGES
    const val TOTAL_STEPS = AppConstants.TOTAL_STEPS
}
