package com.edify.learning.presentation.common

data class Language(
    val code: String,
    val name: String,
    val displayName: String
)

object AppConstants {
    val SUPPORTED_LANGUAGES = listOf(
        Language("en", "English", "English"),
        Language("hi", "Hindi", "हिंदी"),
        Language("bn", "Bengali", "বাংলা"),
        Language("te", "Telugu", "తెలుగు"),
        Language("mr", "Marathi", "मराठी"),
        Language("ta", "Tamil", "தமிழ்"),
        Language("kn", "Kannada", "ಕನ್ನಡ"),
        Language("gu", "Gujarati", "ગુજરાતી"),
        Language("or", "Odia", "ଓଡ଼ିଆ"),
        Language("ml", "Malayalam", "മലയാളം")
    )
    
    const val TOTAL_STEPS = 2
}
