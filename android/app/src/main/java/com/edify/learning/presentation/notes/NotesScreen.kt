package com.edify.learning.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn 
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.data.model.Subject
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen() {
    val viewModel: NotesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val selectedSubjectFilter by viewModel.selectedSubjectFilter.collectAsState()
    var selectedNoteForDetail by remember { mutableStateOf<NoteWithSubjectInfo?>(null) }
    
    // Pull to refresh state
    val pullToRefreshState = rememberPullToRefreshState()
    
    // Handle pull to refresh
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            viewModel.refreshNotes()
        }
    }
    
    // Stop refreshing when data is loaded
    LaunchedEffect(uiState.isLoading) {
        if (!uiState.isLoading && pullToRefreshState.isRefreshing) {
            pullToRefreshState.endRefresh()
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
                    text = "Notes",
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryBlue,
                titleContentColor = White
            )
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
            ) {
                // Subject Filter Chips
                if (uiState.subjects.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        // All subjects chip
                        item {
                            SubjectFilterChip(
                                subject = null,
                                isSelected = selectedSubjectFilter == null,
                                onClick = { viewModel.onSubjectFilterChanged(null) }
                            )
                        }
                        
                        // Individual subject chips
                        items(uiState.subjects) { subject ->
                            SubjectFilterChip(
                                subject = subject,
                                isSelected = selectedSubjectFilter == subject.id,
                                onClick = { viewModel.onSubjectFilterChanged(subject.id) }
                            )
                        }
                    }
                }
                
                // Notes List
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = PrimaryBlue)
                        }
                    }
                    uiState.displayNotes.isEmpty() -> {
                        EmptySubjectNotesState(
                            selectedFilter = selectedSubjectFilter,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(uiState.displayNotes) { noteWithSubject ->
                                ImprovedNoteCard(
                                    noteWithSubject = noteWithSubject,
                                    onDeleteNote = { viewModel.deleteNote(noteWithSubject) },
                                    onNoteClick = { selectedNoteForDetail = it }
                                )
                            }
                        }
                    }
                }
                
                // Error Message
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
            }
            
            // Pull to refresh container
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullToRefreshState,
            )
        }
    }
    
    // Note Detail Dialog
    selectedNoteForDetail?.let { noteWithSubject ->
        NoteDetailDialog(
            noteWithSubject = noteWithSubject,
            onDismiss = { selectedNoteForDetail = null }
        )
    }
}

@Composable
fun SubjectFilterChip(
    subject: Subject?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val chipColor = if (subject != null) {
        Color(android.graphics.Color.parseColor(subject.color))
    } else {
        PrimaryBlue
    }
    
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) chipColor else Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = chipColor
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = subject?.name ?: "All",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) White else chipColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ImprovedNoteCard(
    noteWithSubject: NoteWithSubjectInfo,
    onDeleteNote: () -> Unit,
    onNoteClick: (NoteWithSubjectInfo) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onNoteClick(noteWithSubject) },
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            // Chapter name at the top
            Text(
                text = noteWithSubject.chapterTitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF003c63),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Note Content
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = noteWithSubject.note.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Text(
                        text = getImprovedDisplayContent(noteWithSubject.note.content),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                
                // Delete Button
                IconButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note",
                        tint = Color(0xFF003c63),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
    
    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Delete Note",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete this note? This action cannot be undone.",
                    color = Color.Black
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteNote()
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        "Delete",
                        color = Color.Red,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text(
                        "Cancel",
                        color = Color(0xFF003c63),
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            containerColor = Color.White,
            titleContentColor = Color.Black,
            textContentColor = Color.Black
        )
    }
}

@Composable
fun EmptySubjectNotesState(
    selectedFilter: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val message = if (selectedFilter == null) {
            "No notes found. Start taking notes while reading chapters!"
        } else {
            "No notes found for this subject. Start taking notes while reading chapters!"
        }
        
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = TextSecondary,
            fontWeight = FontWeight.Medium
        )
        
        Text(
            text = "Start reading and add notes to see them here",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun NoteDetailDialog(
    noteWithSubject: NoteWithSubjectInfo,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = noteWithSubject.chapterTitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF003c63),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = noteWithSubject.note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        },
        text = {
            LazyColumn {
                item {
                    Text(
                        text = getFullContent(noteWithSubject.note.content),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        lineHeight = 24.sp
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    "Close",
                    color = Color(0xFF003c63),
                    fontWeight = FontWeight.Medium
                )
            }
        },
        containerColor = Color.White,
        titleContentColor = Color.Black,
        textContentColor = Color.Black,
        modifier = Modifier.fillMaxWidth(0.95f)
    )
}

fun getFullContent(content: String): String {
    return try {
        // Check if content contains old serialized format (for backward compatibility)
        if (content.contains("TextContent(") || content.contains("text=")) {
            // Try to extract text from old format
            val textPattern = "text=([^,)]+)".toRegex()
            val match = textPattern.find(content)
            if (match != null) {
                return match.groupValues[1].trim()
            }
            
            // Fallback cleaning for old format
            content
                .replace("TextContent(", "")
                .replace("blockId=", "")
                .replace("text=", "")
                .replace("[", "")
                .replace("]", "")
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "")
                .replace(")", "")
                .split(",")
                .find { it.length > 10 }
                ?.trim() ?: content
        } else {
            // New format: content is stored as plain text
            content.trim()
        }
    } catch (e: Exception) {
        content.trim()
    }
}

fun getImprovedDisplayContent(content: String): String {
    return try {
        val displayText = if (content.contains("TextContent(") || content.contains("text=")) {
            // Handle old serialized format (for backward compatibility)
            val textPattern = "text=([^,)]+)".toRegex()
            val match = textPattern.find(content)
            if (match != null) {
                match.groupValues[1].trim()
            } else {
                // Fallback cleaning for old format
                content
                    .replace("TextContent(", "")
                    .replace("blockId=", "")
                    .replace("text=", "")
                    .replace("[", "")
                    .replace("]", "")
                    .replace("{", "")
                    .replace("}", "")
                    .replace("\"", "")
                    .replace(")", "")
                    .split(",")
                    .find { it.length > 10 }
                    ?.trim() ?: content
            }
        } else {
            // New format: content is stored as plain text
            content.trim()
        }
        
        // Truncate for display
        if (displayText.length > 80) {
            displayText.take(80).trim() + "..."
        } else {
            displayText
        }
            
    } catch (e: Exception) {
        // Final fallback
        val fallback = content.take(80)
        if (content.length > 80) "$fallback..." else fallback
    }
}
