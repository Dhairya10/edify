package com.edify.learning.presentation.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.data.model.GeneratedQuest
import com.edify.learning.data.model.PersonalizedChapterQuest
import com.edify.learning.data.model.QuestState
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
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        if (uiState.hasQuests) {
            // Show "Your Quests" header and quest list
            Text(
                text = "Your Quests",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.quests) { quest ->
                        PersonalizedQuestCard(
                            quest = quest,
                            onClick = {
                                // Only navigate if quest is not locked
                                if (quest.state != QuestState.LOCKED) {
                                    onNavigateToQuestDetail(quest.chapterId)
                                }
                            },
                            onUnlock = { chapterId ->
                                viewModel.unlockQuest(chapterId)
                            }
                        )
                    }
                }
            }
        } else {
            // Show empty state when no quests are available
            EmptyQuestState()
        }
        
        // Show error if any
        uiState.error?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
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
fun PersonalizedQuestCard(
    quest: PersonalizedChapterQuest,
    onClick: () -> Unit,
    onUnlock: (String) -> Unit = {}
) {
    val subjectColor = when (quest.subject.lowercase()) {
        "science" -> Color(0xFF28a745)
        "english" -> Color(0xFF007bff)
        "maths", "mathematics" -> Color(0xFFfd7e14)
        else -> Color(0xFF6c757d)
    }
    
    // Card styling based on state
    val cardColor = when(quest.state) {
        QuestState.LOCKED -> Color(0xFFF5F5F5) // Light gray for locked quests
        QuestState.COMPLETED -> Color(0xFFEDF7ED) // More visible light green tint for completed quests
        else -> Color.White
    }
    val isClickable = quest.state != QuestState.LOCKED
    
    // Prepare trimmed title text to ensure consistent heights
    val titleText = if (quest.chapterTitle.length > 40) {
        quest.chapterTitle.take(40) + "..."
    } else {
        quest.chapterTitle
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp) // Fixed height for all cards
            .padding(top = 4.dp)
            .then(
                if (isClickable) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        border = if (quest.state == QuestState.LOCKED) {
            BorderStroke(1.dp, Color(0xFFDDDDDD)) // Light gray border for locked quests
        } else {
            null
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp) // Provide space for the button/icon on right
            ) {
                // Chapter title - always visible with normal color
                Text(
                    text = titleText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (quest.state == QuestState.LOCKED) {
                        TextPrimary.copy(alpha = 0.7f) // Dimmed text for locked quests
                    } else {
                        TextPrimary
                    },
                    lineHeight = 24.sp,
                    maxLines = 2
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Display the question only if quest is unlocked or completed
                if (quest.state != QuestState.LOCKED) {
                    // All quests should have questions now due to repository filtering
                    val questionText = quest.generatedQuestion ?: ""
                    val displayText = if (questionText.length > 100) {
                        questionText.take(100) + "..."
                    } else {
                        questionText
                    }
                    
                    Text(
                        text = displayText,
                        fontSize = 14.sp,
                        color = TextSecondary,
                        lineHeight = 20.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    // For locked quests, show a completely blurred content area (no visible text)
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                    ) {
                        // Blurred content representation - just gray blocks
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .height(12.dp)
                                    .background(
                                        color = Color(0xFFEEEEEE),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .height(12.dp)
                                    .background(
                                        color = Color(0xFFEEEEEE),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .height(12.dp)
                                    .background(
                                        color = Color(0xFFEEEEEE),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                    }
                }
            }
            
            // State indicators positioned at bottom right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 4.dp)
            ) {
                when (quest.state) {
                    QuestState.LOCKED -> {
                        Button(
                            onClick = {
                                onUnlock(quest.chapterId)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF9C702E) // Orange color
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Unlock",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Unlock",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    QuestState.COMPLETED -> {
                        // No icon for completed state - green background is sufficient
                    }
                    QuestState.UNLOCKED -> {
                        // No icon for unlocked state - clean design
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyQuestState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Empty state icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(60.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🎯",
                fontSize = 48.sp
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Your Journey Begins",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "Engage with your subjects to unlock new Quests. The more you learn, the more challenges you'll discover.",
            fontSize = 16.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}




