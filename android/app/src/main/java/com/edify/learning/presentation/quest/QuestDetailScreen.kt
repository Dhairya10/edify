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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.data.model.QuestQuestion
import com.edify.learning.presentation.quest.getSubjectIcon
import com.edify.learning.presentation.quest.getSubjectColor
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailScreen(
    questId: String,
    onNavigateBack: () -> Unit,
    viewModel: QuestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Find the specific quest question by ID (for introductory questions)
    val questQuestion = uiState.introductoryQuestions.find { it.id == questId }
    
    // Check if this is a chapter-based quest (generated question)
    val isChapterQuest = questQuestion == null
    val chapterQuest = if (isChapterQuest) {
        uiState.topInterestedChapters.find { it.chapterId == questId }
    } else null
    
    LaunchedEffect(questId) {
        if (!isChapterQuest) {
            viewModel.loadQuestDetail(questId)
        }
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
                    text = when {
                        questQuestion != null -> questQuestion.subject
                        chapterQuest != null -> chapterQuest.subject
                        else -> "Quest"
                    },
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
        
        when {
            questQuestion != null -> {
                // Show introductory question
                QuestDetailContent(
                    question = questQuestion,
                    onAnswerSubmit = { answer ->
                        viewModel.submitQuestAnswer(questId, answer)
                    }
                )
            }
            chapterQuest != null -> {
                // Show chapter-based deep exploration question
                ChapterQuestContent(
                    chapter = chapterQuest,
                    onAnswerSubmit = { answer ->
                        viewModel.submitChapterQuestAnswer(questId, answer)
                    },
                    onGenerateQuestion = {
                        viewModel.generateDeepExplorationQuestion(questId) { _, _ -> }
                    }
                )
            }
            else -> {
                // Loading or error state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isGeneratingQuestion) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Generating your deep exploration question...",
                                fontSize = 16.sp,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuestDetailContent(
    question: QuestQuestion,
    onAnswerSubmit: (String) -> Unit
) {
    var userAnswer by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Subject Header with Icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = getSubjectIcon(question.subject),
                contentDescription = question.subject,
                tint = getSubjectColor(question.subject),
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = question.subject,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = getSubjectColor(question.subject)
            )
        }
        
        // Question Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = question.question,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                    lineHeight = 28.sp
                )
            }
        }
        
        // Answer Input Section
        Text(
            text = "Your Answer",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        // Long Text Input
        OutlinedTextField(
            value = userAnswer,
            onValueChange = { userAnswer = it },
            placeholder = {
                Text(
                    text = "Share your thoughts and insights here...",
                    color = TextSecondary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            maxLines = 10
        )
        
        // Submit Button
        Button(
            onClick = { onAnswerSubmit(userAnswer) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            enabled = userAnswer.trim().isNotBlank()
        ) {
            Text(
                text = "Submit Answer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = White
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ChapterQuestContent(
    chapter: PersonalizedChapterQuest,
    onAnswerSubmit: (String) -> Unit,
    onGenerateQuestion: () -> Unit
) {
    var answerText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Subject header with icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            val subjectIcon = getSubjectIcon(chapter.subject)
            val subjectColor = getSubjectColor(chapter.subject)
            
            Icon(
                imageVector = subjectIcon,
                contentDescription = chapter.subject,
                tint = subjectColor,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    text = chapter.subject,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = subjectColor
                )
                Text(
                    text = chapter.chapterTitle,
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }
        }
        
        // Question card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Deep Exploration Question",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Show generated question or generate button
                if (chapter.generatedQuestion != null) {
                    Text(
                        text = chapter.generatedQuestion,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary,
                        lineHeight = 24.sp
                    )
                } else {
                    Text(
                        text = "Click below to generate a personalized deep exploration question for this chapter.",
                        fontSize = 16.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Button(
                        onClick = onGenerateQuestion,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Generate Question")
                    }
                }
            }
        }
        
        // Answer input section (only show if question is generated)
        if (chapter.generatedQuestion != null) {
            Text(
                text = "Your Thoughts",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        
        Text(
            text = "Take your time to reflect and share your detailed thoughts on this question.",
            fontSize = 14.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Answer input field
        OutlinedTextField(
            value = answerText,
            onValueChange = { answerText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = {
                Text(
                    text = "Share your thoughts, insights, and reflections here...",
                    color = TextSecondary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(12.dp),
            maxLines = 10
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Submit button
        Button(
            onClick = {
                if (answerText.trim().isNotEmpty()) {
                    onAnswerSubmit(answerText.trim())
                }
            },
            enabled = answerText.trim().isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Submit Answer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        }
    }
}

