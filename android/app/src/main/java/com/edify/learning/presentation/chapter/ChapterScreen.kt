package com.edify.learning.presentation.chapter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import com.edify.learning.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import com.edify.learning.data.model.ChapterContent
import com.edify.learning.data.util.ContentParser
import com.edify.learning.presentation.components.MarkdownRenderer
import com.edify.learning.presentation.components.ImageViewer
import com.edify.learning.ui.theme.*
import java.io.File

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onAddNote: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Note",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        onAddNote(title, content)
                    }
                },
                enabled = title.isNotBlank() && content.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterScreen(
    viewModel: ChapterViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToNotes: (String) -> Unit,
    onNavigateToChat: (String, String?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedText by viewModel.selectedText.collectAsState()
    val selectedContent by viewModel.selectedContent.collectAsState()
    val isImageSelected by viewModel.isImageSelected.collectAsState()

    // Monitor text selection state
    var hasTextSelection by remember { mutableStateOf(false) }

    // Custom text selection colors to detect selection
    val customTextSelectionColors = TextSelectionColors(
        handleColor = SecondaryBlue,
        backgroundColor = SecondaryBlue.copy(alpha = 0.3f)
    )

    var showAddNoteDialog by remember { mutableStateOf(false) }
    var showImageViewer by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = uiState.chapter?.title ?: "Chapter",
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
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
            actions = {
                // Add Note Button
                IconButton(
                    onClick = { showAddNoteDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Note"
                    )
                }

                // Chat Button with custom icon
                IconButton(
                    onClick = {
                        uiState.chapter?.let { chapter ->
                            onNavigateToChat(chapter.id, selectedText.ifBlank { null })
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat),
                        contentDescription = "Chat"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PrimaryBlue,
                titleContentColor = White,
                navigationIconContentColor = White,
                actionIconContentColor = White
            )
        )

        // Content based on loading state
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = SecondaryBlue)
            }
        } else {
            uiState.chapter?.let { chapter ->
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Progress Bar
                    LinearProgressIndicator(
                        progress = chapter.readingProgress,
                        modifier = Modifier.fillMaxWidth(),
                        color = SecondaryBlue,
                        trackColor = ProgressIncomplete
                    )

                    // Content
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            // Chapter Header
                            Text(
                                text = chapter.title,
                                style = MaterialTheme.typography.headlineSmall,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Text(
                                text = chapter.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextSecondary,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        item {
                            // Display chapter content using MarkdownRenderer
                            val parsedContent = remember(chapter.content) {
                                ContentParser.parseChapterContent(chapter.content)
                            }

                            if (parsedContent != null) {
                                MarkdownRenderer(
                                    content = parsedContent,
                                    baseImagePath = "file:///android_asset/images",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
                                Text(
                                    text = chapter.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }


                    }
                }
            }
        }

        // Error State
        uiState.error?.let { error ->
            LaunchedEffect(error) {
                // Show snackbar or handle error
                viewModel.clearError()
            }
        }

        // Success Message
        val context = LocalContext.current
        uiState.message?.let { message ->
            LaunchedEffect(message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.clearMessage()
            }
        }
        }

        // Add Note Dialog
        if (showAddNoteDialog) {
            AddNoteDialog(
                onDismiss = { showAddNoteDialog = false },
                onAddNote = { title: String, content: String ->
                    viewModel.onAddNote(title, content)
                    showAddNoteDialog = false
                }
            )
        }

        if (showImageViewer) {
            ImageViewer(
                imagePath = selectedImagePath,
                onDismiss = { showImageViewer = false }
            )
        }
    } 







