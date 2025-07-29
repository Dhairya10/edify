package com.edify.learning.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn 
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.edify.learning.R
import com.edify.learning.data.model.Subject
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen() {
    val viewModel: NotesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val selectedSubjectFilter by viewModel.selectedSubjectFilter.collectAsState()
    var selectedNoteForDetail by remember { mutableStateOf<NoteWithSubjectInfo?>(null) }
    

    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Top App Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Notes",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = DarkBackground,
                titleContentColor = TextPrimary
            ),
            windowInsets = WindowInsets(0.dp)
        )
        
        Box(
            modifier = Modifier.fillMaxSize()
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
                            CircularProgressIndicator(color = TextPrimary)
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
                        colors = CardDefaults.cardColors(containerColor = ErrorColor.copy(alpha = 0.2f))
                    ) {
                        Text(
                            text = error,
                            color = ErrorColor,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

        }
    }
    
    // Note Detail Dialog
    selectedNoteForDetail?.let { noteWithSubject ->
        NoteDetailDialog(
            noteWithSubject = noteWithSubject,
            onDismiss = { selectedNoteForDetail = null },
            onDeleteNote = { 
                viewModel.deleteNote(noteWithSubject)
                selectedNoteForDetail = null
            }
        )
    }
}

@Composable
fun SubjectFilterChip(
    subject: Subject?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) White else Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = White
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = subject?.name ?: "All",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) DarkBackground else White,
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
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            // Note title at the top
            Text(
                text = noteWithSubject.note.title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Note content
            Text(
                text = getImprovedDisplayContent(noteWithSubject.note.content),
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Subject badge at the bottom right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF3A3A3C)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = noteWithSubject.subjectName,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
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
                    color = Color(0xFF3A3A3C),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.draft_24dp_ffffff_fill0_wght400_grad0_opsz24),
                contentDescription = "No notes",
                modifier = Modifier.size(48.dp),
                tint = Color.Unspecified
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No Notes Yet",
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Notes you take during your study sessions\nwill appear here automatically.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Explore Library Button
        Button(
            onClick = { /* TODO: Navigate to library */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TextPrimary,
                contentColor = DarkBackground
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

@Composable
fun NoteDetailDialog(
    noteWithSubject: NoteWithSubjectInfo,
    onDismiss: () -> Unit,
    onDeleteNote: () -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = noteWithSubject.chapterTitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = noteWithSubject.note.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = TextPrimary
                    )
                }
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        text = {
            Column {
                LazyColumn(
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    item {
                        Text(
                            text = getFullContent(noteWithSubject.note.content),
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary,
                            lineHeight = 24.sp
                        )
                    }
                }
                
                // Delete icon at bottom-right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete note",
                            tint = ErrorColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {},
        containerColor = DarkCard,
        titleContentColor = TextPrimary,
        textContentColor = TextPrimary,
        modifier = Modifier.fillMaxWidth(0.95f)
    )
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    "Delete Note",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "Are you sure you want to delete this note? This action cannot be undone.",
                    color = TextSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteNote()
                        showDeleteDialog = false
                        onDismiss()
                    }
                ) {
                    Text(
                        "Delete",
                        color = ErrorColor,
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
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            containerColor = DarkCard,
            titleContentColor = TextPrimary,
            textContentColor = TextSecondary
        )
    }
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
