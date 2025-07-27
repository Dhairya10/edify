package com.edify.learning.presentation.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.data.model.GeneratedQuest
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailScreen(
    questId: String,
    onNavigateBack: () -> Unit,
    viewModel: QuestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Load quest details - try by chapterId (for PersonalizedChapterQuest navigation)
    // Load quest details using LaunchedEffect for proper composition handling
    LaunchedEffect(questId) {
        viewModel.loadQuestDetailByChapterId(questId)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = uiState.selectedQuest?.title ?: "Quest",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = White
            )
        )
        
        if (uiState.selectedQuest != null) {
            QuestDetailContent(
                quest = uiState.selectedQuest!!,
                onAnswerSubmit = { answer ->
                    // Use the actual quest ID from the loaded quest, not the navigation parameter
                    viewModel.submitQuestAnswer(uiState.selectedQuest!!.id, answer)
                    // Don't navigate back automatically - let user see their submitted answer
                },
                isSubmitting = uiState.isSubmittingAnswer
            )
        } else {
            // Loading or error state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading quest...",
                            fontSize = 16.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Text(
                        text = "Quest not found",
                        fontSize = 18.sp,
                        color = TextSecondary
                    )
                }
            }
        }
        
        // Show error if any
        uiState.error?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
fun QuestDetailContent(
    quest: GeneratedQuest,
    onAnswerSubmit: (String) -> Unit,
    isSubmitting: Boolean = false
) {
    // Use remember with quest.id as key to reset state when quest changes
    var userAnswer by remember(quest.id) { mutableStateOf(quest.userAnswer ?: "") }
    val scrollState = rememberScrollState()
    
    // Update the userAnswer when quest's userAnswer changes (e.g., after submission)
    LaunchedEffect(quest.userAnswer) {
        if (quest.userAnswer != null && quest.userAnswer != userAnswer) {
            userAnswer = quest.userAnswer!!
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .imePadding() // Add padding for keyboard
    ) {
        // Question display
        Text(
            text = quest.question,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            lineHeight = 28.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Answer input
        OutlinedTextField(
            value = userAnswer,
            onValueChange = { userAnswer = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = {
                Text(
                    text = "Share your thoughts here",
                    color = TextSecondary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f),
                focusedTextColor = TextPrimary,  // Ensure text is visible (dark color)
                unfocusedTextColor = TextPrimary  // Ensure text is visible (dark color)
            ),
            shape = RoundedCornerShape(12.dp),
            maxLines = 10,
            enabled = !isSubmitting
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Submit button
        Button(
            onClick = {
                if (userAnswer.trim().isNotEmpty()) {
                    onAnswerSubmit(userAnswer.trim())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = userAnswer.trim().isNotBlank() && !isSubmitting
        ) {
            if (isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White
                )
            } else {
                Text(
                    text = if (quest.isCompleted) "Update Answer" else "Submit Answer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}


