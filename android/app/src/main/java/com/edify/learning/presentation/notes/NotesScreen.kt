package com.edify.learning.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.edify.learning.data.model.Note
import com.edify.learning.data.model.NoteType
import com.edify.learning.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Notes & Highlights",
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
            // Filter Tabs
            FilterTabs(
                selectedFilter = selectedFilter,
                onFilterChanged = viewModel::onFilterChanged,
                modifier = Modifier.padding(bottom = 16.dp)
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
            
            // Notes List
            if (uiState.notes.isEmpty() && !uiState.isLoading) {
                EmptyNotesState(selectedFilter = selectedFilter)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.notes) { note ->
                        NoteCard(
                            note = note,
                            onDeleteNote = { viewModel.deleteNote(note) }
                        )
                    }
                }
            }
        }
    }
    
    // Success Message
    uiState.message?.let { message ->
        LaunchedEffect(message) {
            // Show snackbar or handle message
            viewModel.clearMessage()
        }
    }
}

@Composable
fun FilterTabs(
    selectedFilter: NoteFilter,
    onFilterChanged: (NoteFilter) -> Unit,
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
        FilterTab(
            text = "All",
            isSelected = selectedFilter == NoteFilter.ALL,
            onClick = { onFilterChanged(NoteFilter.ALL) },
            modifier = Modifier.weight(1f)
        )
        
        FilterTab(
            text = "Notes",
            isSelected = selectedFilter == NoteFilter.NOTES,
            onClick = { onFilterChanged(NoteFilter.NOTES) },
            modifier = Modifier.weight(1f)
        )
        
        FilterTab(
            text = "Highlights",
            isSelected = selectedFilter == NoteFilter.HIGHLIGHTS,
            onClick = { onFilterChanged(NoteFilter.HIGHLIGHTS) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun FilterTab(
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
fun NoteCard(
    note: Note,
    onDeleteNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        // Note Type Indicator
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    when (note.type) {
                                        NoteType.USER_NOTE -> SecondaryBlue
                                        NoteType.HIGHLIGHT -> SuccessColor
                                        NoteType.GEMMA_EXPLANATION -> PrimaryBlue
                                    }
                                )
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    Text(
                        text = getDisplayContent(note.content),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Text(
                        text = formatDate(note.createdAt),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary.copy(alpha = 0.7f)
                    )
                }
                
                IconButton(
                    onClick = onDeleteNote,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note",
                        tint = ErrorColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyNotesState(
    selectedFilter: NoteFilter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when (selectedFilter) {
                NoteFilter.ALL -> "No notes or highlights yet"
                NoteFilter.NOTES -> "No notes added yet"
                NoteFilter.HIGHLIGHTS -> "No text highlighted yet"
            },
            style = MaterialTheme.typography.titleMedium,
            color = TextSecondary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = when (selectedFilter) {
                NoteFilter.ALL -> "Start reading and add notes or highlight important text"
                NoteFilter.NOTES -> "Tap 'Add Note' while reading to create your first note"
                NoteFilter.HIGHLIGHTS -> "Select text and tap 'Highlight' to save important passages"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

private fun getDisplayContent(content: String): String {
    // In a real app, this would parse the JSON content
    // For now, return a simplified version
    return content.take(150) + if (content.length > 150) "..." else ""
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
