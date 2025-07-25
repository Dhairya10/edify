package com.edify.learning.presentation.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.data.model.QuestQuestion
import com.edify.learning.data.model.Quest
import com.edify.learning.presentation.quest.PersonalizedChapterQuest
import com.edify.learning.presentation.quest.QuestProgressData
import com.edify.learning.presentation.quest.getSubjectIcon
import com.edify.learning.presentation.quest.getSubjectColor
import com.edify.learning.ui.theme.*

@Composable
fun QuestScreen(
    viewModel: QuestViewModel = hiltViewModel(),
    onNavigateToQuestDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Refresh quest data when screen becomes visible
    LaunchedEffect(Unit) {
        viewModel.refreshQuestData()
    }
    
    // Handle quest item clicks with Gemma integration
    val handleQuestItemClick = { chapterId: String ->
        viewModel.generateDeepExplorationQuestion(chapterId) { _, question ->
            // Navigate to Q&A view with the generated question
            // For now, we'll use the chapter ID as the quest ID
            onNavigateToQuestDetail(chapterId)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Quest",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Explore your curiosity beyond textbooks",
            fontSize = 16.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        if (uiState.hasUnlockedPersonalizedQuests) {
            // Show personalized quest chapters
            PersonalizedQuestContent(
                topChapters = uiState.topInterestedChapters,
                onChapterClick = handleQuestItemClick,
                isGeneratingQuestion = uiState.isGeneratingQuestion
            )
        } else {
            // Show progress section and introductory questions
            Column {
                // Progress section
                QuestProgressSection(
                    progressData = uiState.progressData,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // Introductory questions section
                EmptyQuestState(
                    introductoryQuestions = uiState.introductoryQuestions,
                    onQuestionClick = { question ->
                        onNavigateToQuestDetail(question.id)
                    }
                )
            }
        }
    }
}

@Composable
fun QuestProgressSection(
    progressData: QuestProgressData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Unlock Your Personalized Quests",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Explore ${progressData.totalActions} different chapters by adding a note, chatting, or answering an exercise to reveal your personal Quest board.",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Progress indicator with circles
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                repeat(progressData.totalActions) { index ->
                    val isCompleted = index < progressData.completedActions
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Completed",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = CircleShape
                                )
                        )
                    }
                    
                    if (index < progressData.totalActions - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                Text(
                    text = "${progressData.completedActions}/${progressData.totalActions}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            // Progress bar
            LinearProgressIndicator(
                progress = { progressData.progressPercentage },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
            )
        }
    }
}

@Composable
fun EmptyQuestState(
    introductoryQuestions: List<QuestQuestion>,
    onQuestionClick: (QuestQuestion) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Or, Try an Introductory Quest",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Introductory Questions
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(introductoryQuestions) { question ->
                IntroductoryQuestionCard(
                    question = question,
                    onClick = { onQuestionClick(question) }
                )
            }
        }
    }
}

@Composable
fun IntroductoryQuestionCard(
    question: QuestQuestion,
    onClick: () -> Unit
) {
    val subjectIcon = getSubjectIcon(question.subject)
    val subjectColor = getSubjectColor(question.subject)
    
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = subjectIcon,
                    contentDescription = question.subject,
                    tint = subjectColor,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = question.subject,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = subjectColor
                )
            }
            
            Text(
                text = question.question,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun PersonalizedQuestContent(
    topChapters: List<PersonalizedChapterQuest>,
    onChapterClick: (String) -> Unit,
    isGeneratingQuestion: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Your Quests",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Here are the topics you seem most interested in.\nClick one to explore with a deep question!",
            fontSize = 16.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        if (isGeneratingQuestion) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Generating your question...",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }
        }
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(topChapters) { chapter ->
                PersonalizedChapterCard(
                    chapter = chapter,
                    onClick = { onChapterClick(chapter.chapterId) },
                    isEnabled = !isGeneratingQuestion
                )
            }
        }
    }
}
