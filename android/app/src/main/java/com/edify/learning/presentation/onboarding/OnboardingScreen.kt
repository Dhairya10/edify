package com.edify.learning.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.R
import com.edify.learning.presentation.common.Language

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isEnabled by remember(uiState.currentStep, uiState.name, uiState.selectedLanguage, uiState.isLoading) { 
        derivedStateOf {
            viewModel.canProceedFromCurrentStep() && !uiState.isLoading
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Progress indicators
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(OnboardingConstants.TOTAL_STEPS) { index ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (index == uiState.currentStep) Color.White
                                else Color.Gray
                            )
                    )
                    if (index < OnboardingConstants.TOTAL_STEPS - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            // Content based on current step
            when (uiState.currentStep) {
                0 -> NameStep(
                    name = uiState.name,
                    onNameChange = viewModel::updateName,
                    viewModel = viewModel
                )
                1 -> LanguageStep(
                    selectedLanguage = uiState.selectedLanguage,
                    onLanguageSelect = viewModel::updateLanguage
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Navigation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp), // Increased bottom spacing by 100%
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                if (uiState.currentStep > 0) {
                    Box(
                        modifier = Modifier
                            .size(72.dp) // Increased size by 50%
                            .background(
                                Color.Gray.copy(alpha = 0.3f),
                                RoundedCornerShape(36.dp) // Adjusted shape for circular button
                            )
                            .clickable { viewModel.previousStep() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp) // Increased icon size
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(72.dp)) // Placeholder for alignment
                }

                // Next/Complete button
                Box(
                    modifier = Modifier
                        .size(72.dp) // Increased size by 50%
                        .background(
                            color = if (isEnabled) Color.White else Color.Gray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(36.dp) // Adjusted shape for circular button
                        )
                        .clickable(enabled = isEnabled) {
                            if (uiState.currentStep == OnboardingConstants.TOTAL_STEPS - 1) {
                                viewModel.completeOnboarding(onOnboardingComplete)
                            } else {
                                viewModel.nextStep()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp), // Adjusted for larger button
                            color = Color.Black,
                            strokeWidth = 3.dp
                        )
                    } else {
                        val icon = if (uiState.currentStep == OnboardingConstants.TOTAL_STEPS - 1) {
                            painterResource(id = R.drawable.check_24dp_000000_fill0_wght400_grad0_opsz24)
                        } else {
                            painterResource(id = R.drawable.arrow_forward_ios_24dp_000000_fill0_wght400_grad0_opsz24)
                        }
                        Icon(
                            painter = icon,
                            contentDescription = if (uiState.currentStep == OnboardingConstants.TOTAL_STEPS - 1) "Finish" else "Next",
                            tint = if (isEnabled) Color.Black else Color.Gray,
                            modifier = Modifier.size(32.dp) // Increased icon size
                        )
                    }
                }
            }

            // Error message
            uiState.error?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun NameStep(
    name: String,
    onNameChange: (String) -> Unit,
    viewModel: OnboardingViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Title
        Text(
            text = "Welcome!",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Subtitle
        Text(
            text = "Let's get your profile set up",
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Question
        Text(
            text = "What should we call you?",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Input field
        val validationError = viewModel.getNameValidationError(name)
        val isError = validationError != null && name.isNotEmpty()
        
        OutlinedTextField(
            value = name,
            onValueChange = { input -> onNameChange(input.trim()) },
            placeholder = {
                Text(
                    text = "Enter your name",
                    color = Color.Gray
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = if (isError) Color.Red else Color.White,
                unfocusedBorderColor = if (isError) Color.Red else Color.Gray,
                cursorColor = Color.White,
                errorBorderColor = Color.Red,
                errorTextColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = isError,
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Error message or helper text
                    Text(
                        text = validationError ?: "2-50 characters",
                        color = if (isError) Color.Red else Color.Gray,
                        fontSize = 12.sp
                    )
                    
                    // Character count
                    Text(
                        text = "${name.trim().length}/50",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        )
    }
}

@Composable
private fun LanguageStep(
    selectedLanguage: String,
    onLanguageSelect: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Title
        Text(
            text = "Language Preference",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Subtitle
        Text(
            text = "Choose your preferred language for the app",
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Language grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(OnboardingConstants.SUPPORTED_LANGUAGES) { language ->
                LanguageCard(
                    language = language,
                    isSelected = selectedLanguage == language.name,
                    onSelect = { onLanguageSelect(language.name) }
                )
            }
        }
    }
}

@Composable
private fun LanguageCard(
    language: Language,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // Set a fixed height for all cards
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.White else Color.Gray.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Use fillMaxSize to center content within the fixed height
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {
            Text(
                text = language.name,
                color = if (isSelected) Color.Black else Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            if (language.displayName != language.name) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = language.displayName,
                    color = if (isSelected) Color.Gray else Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}




