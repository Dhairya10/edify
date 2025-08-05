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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.R
import com.edify.learning.data.model.GeneratedQuest
import com.edify.learning.data.model.PersonalizedChapterQuest
import com.edify.learning.data.model.QuestState
import com.edify.learning.presentation.quest.getSubjectIcon
import com.edify.learning.presentation.quest.getSubjectColor
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestScreen(
    viewModel: QuestViewModel = hiltViewModel(),
    onNavigateToQuestDetail: (String) -> Unit = {},
    onNavigateToLibrary: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Refresh quest data when screen becomes visible
    LaunchedEffect(Unit) {
        viewModel.refreshQuestData()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        // Top bar with refresh functionality
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Quest",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        viewModel.refreshQuestData()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.refresh_24dp_ffffff_fill0_wght400_grad0_opsz24),
                        contentDescription = "Refresh Quests",
                        tint = TextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Black
            ),
            windowInsets = WindowInsets(0.dp)
        )
        
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
        if (uiState.hasQuests) {
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
            EmptyQuestState(onNavigateToLibrary = onNavigateToLibrary)
        }
        
        // Show error if any
        uiState.error?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkCard
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = ErrorColor
                )
            }
        }
        } // Close inner Column with padding
    } // Close outer Column
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
        QuestState.LOCKED -> DarkSurface // Dark gray for locked quests
        QuestState.COMPLETED -> Color(0xFF1B3B1B) // Dark green tint for completed quests
        else -> DarkCard
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
            BorderStroke(1.dp, TextSecondary.copy(alpha = 0.3f)) // Dark theme border for locked quests
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
                
                // Display question text or blurred content based on quest state
                if (quest.state == QuestState.LOCKED) {
                    // Show blurred content blobs for locked quests
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // First blob - longer
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(12.dp)
                                .background(
                                    color = Color(0xFF4A5568), // Gray blob color
                                    shape = RoundedCornerShape(6.dp)
                                )
                        )
                        // Second blob - medium
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.65f)
                                .height(12.dp)
                                .background(
                                    color = Color(0xFF4A5568), // Gray blob color
                                    shape = RoundedCornerShape(6.dp)
                                )
                        )
                        // Third blob - shorter
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.45f)
                                .height(12.dp)
                                .background(
                                    color = Color(0xFF4A5568), // Gray blob color
                                    shape = RoundedCornerShape(6.dp)
                                )
                        )
                    }
                } else {
                    // Show actual question text for unlocked/completed quests
                    Text(
                        text = quest.generatedQuestion?.take(120)?.let { 
                            if (quest.generatedQuestion!!.length > 120) "$it..." else it 
                        } ?: "Explore this chapter through personalized questions.",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .wrapContentHeight()
                    )
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
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Unlock",
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Unlock",
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    QuestState.COMPLETED -> {
                        Icon(
                            painter = painterResource(id = R.drawable.task_alt_24dp_6eb48d_fill0_wght400_grad0_opsz24),
                            contentDescription = "Completed",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
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
fun EmptyQuestState(
    onNavigateToLibrary: () -> Unit = {}
) {
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
                    color = DarkSurface,
                    shape = RoundedCornerShape(60.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.explore_24dp_ffffff_fill0_wght400_grad0_opsz24),
                contentDescription = "Explore",
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp)
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
            text = "Engage with your subjects to unlock new Quests. The more you learn, the more challenges you'll discover",
            fontSize = 16.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Explore Library Button
        Button(
            onClick = onNavigateToLibrary,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TextPrimary,
                contentColor = Black
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.book_5_24dp_000000_fill0_wght400_grad0_opsz24),
                contentDescription = "Book icon",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Explore Library",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}




