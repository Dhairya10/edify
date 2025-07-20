package com.edify.learning.presentation.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edify.learning.data.model.Chapter
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    viewModel: SubjectViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToChapter: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedMode by viewModel.selectedMode.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = uiState.subject?.name ?: "Subject",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryBlue,
                titleContentColor = White,
                navigationIconContentColor = White
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Mode Toggle
            ModeToggle(
                selectedMode = selectedMode,
                onModeChanged = viewModel::onModeChanged,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Loading State
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = SecondaryBlue)
                }
            }
            
            // Error State
            uiState.error?.let { error ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = ErrorColor.copy(alpha = 0.1f))
                ) {
                    Text(
                        text = error,
                        color = ErrorColor,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            // Content based on selected mode
            when (selectedMode) {
                SubjectMode.LEARNING -> {
                    LearningModeContent(
                        chapters = uiState.chapters,
                        onChapterClick = onNavigateToChapter
                    )
                }
                SubjectMode.REVISION -> {
                    RevisionModeContent()
                }
            }
        }
    }
}

@Composable
fun ModeToggle(
    selectedMode: SubjectMode,
    onModeChanged: (SubjectMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BackgroundGray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ModeToggleButton(
            text = "Learning",
            isSelected = selectedMode == SubjectMode.LEARNING,
            onClick = { onModeChanged(SubjectMode.LEARNING) },
            modifier = Modifier.weight(1f)
        )
        
        ModeToggleButton(
            text = "Revision",
            isSelected = selectedMode == SubjectMode.REVISION,
            onClick = { onModeChanged(SubjectMode.REVISION) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ModeToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = if (isSelected) PrimaryBlue else Color.Transparent
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) White else TextSecondary,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}

@Composable
fun LearningModeContent(
    chapters: List<Chapter>,
    onChapterClick: (String) -> Unit
) {
    Text(
        text = "Chapters",
        style = MaterialTheme.typography.titleLarge,
        color = TextPrimary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(chapters) { chapter ->
            ChapterCard(
                chapter = chapter,
                onClick = { onChapterClick(chapter.id) }
            )
        }
    }
}

@Composable
fun RevisionModeContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Revision Tools",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Quiz Card
        RevisionCard(
            title = "Quiz",
            description = "Test your knowledge with objective questions",
            backgroundColor = SecondaryBlue,
            onClick = { /* TODO: Navigate to quiz */ }
        )
        
        // Exercises Card
        RevisionCard(
            title = "Exercises",
            description = "Practice with subjective questions",
            backgroundColor = PrimaryBlue,
            onClick = { /* TODO: Navigate to exercises */ }
        )
    }
}

@Composable
fun ChapterCard(
    chapter: Chapter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Text(
                        text = chapter.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    
                    Text(
                        text = "Estimated reading time: ${chapter.estimatedReadingTime} minutes",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                
                // Progress indicator
                if (chapter.readingProgress > 0) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (chapter.isCompleted) SuccessColor else SecondaryBlue
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (chapter.isCompleted) "✓" else "${(chapter.readingProgress * 100).toInt()}%",
                            color = White,
                            fontSize = if (chapter.isCompleted) 12.sp else 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RevisionCard(
    title: String,
    description: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = White.copy(alpha = 0.9f)
            )
        }
    }
}
