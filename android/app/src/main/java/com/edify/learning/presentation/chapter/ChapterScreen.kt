package com.edify.learning.presentation.chapter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import com.edify.learning.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.edify.learning.presentation.components.TranslationDialog
import com.edify.learning.data.service.GemmaService
import com.edify.learning.ui.theme.*
import java.io.File
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onAddNote: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSurface,
        title = {
            Text(
                text = "Add Note",
                fontWeight = FontWeight.Bold,
                color = TextPrimary
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
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SecondaryBlue,
                        focusedLabelColor = SecondaryBlue,
                        cursorColor = SecondaryBlue,
                        unfocusedBorderColor = TextSecondary,
                        unfocusedLabelColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
                
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SecondaryBlue,
                        focusedLabelColor = SecondaryBlue,
                        cursorColor = SecondaryBlue,
                        unfocusedBorderColor = TextSecondary,
                        unfocusedLabelColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
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
    onNavigateToChat: (String, String?) -> Unit,
    gemmaService: GemmaService
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    var showAddNoteDialog by remember { mutableStateOf(false) }
    var showImageViewer by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }
    
    // Translation states
    var showTranslationDialog by remember { mutableStateOf(false) }
    var originalParagraph by remember { mutableStateOf("") }
    var translatedParagraph by remember { mutableStateOf("") }
    var isTranslating by remember { mutableStateOf(false) }
    var isTranslationSelectionMode by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.chapter?.title ?: "Chapter",
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
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
                actions = {
                    // Translate button
                    IconButton(
                        onClick = {
                            isTranslationSelectionMode = !isTranslationSelectionMode
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.translate_24dp_ffffff_fill0_wght400_grad0_opsz24),
                            contentDescription = "Translate",
                            tint = TextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    // Gemma chat button
                    IconButton(
                        onClick = {
                            uiState.chapter?.let { chapter ->
                                onNavigateToChat(chapter.id, null)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.chat_24dp_ffffff_fill0_wght400_grad0_opsz24),
                            contentDescription = "Chat with Gemma",
                            tint = TextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground
                )
            )
        },
        floatingActionButton = {
            if (!isTranslationSelectionMode) {
                FloatingActionButton(
                    onClick = { showAddNoteDialog = true },
                    containerColor = SecondaryBlue,
                    contentColor = Color.Black,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(64.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            ambientColor = Color.Black.copy(alpha = 0.4f),
                            spotColor = Color.Black.copy(alpha = 0.4f)
                        )
                        .border(
                            width = 4.dp,
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Note",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        containerColor = DarkBackground
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
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
                                trackColor = DarkCard
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
                                            baseImagePath = "${context.filesDir}/chapters/${chapter.id}/",
                                            modifier = Modifier.fillMaxWidth(),
                                            onImageClick = { imagePath ->
                                                selectedImagePath = imagePath
                                                showImageViewer = true
                                            },
                                            onParagraphSelected = { selectedText ->
                                                if (isTranslationSelectionMode) {
                                                    if (selectedText.length <= 500) {
                                                        originalParagraph = selectedText
                                                        isTranslating = true
                                                        showTranslationDialog = true
                                                        isTranslationSelectionMode = false
                                                        
                                                        coroutineScope.launch {
                                                            try {
                                                                gemmaService.translateText(selectedText).fold(
                                                                    onSuccess = { translation ->
                                                                        translatedParagraph = translation
                                                                        isTranslating = false
                                                                    },
                                                                    onFailure = { error ->
                                                                        isTranslating = false
                                                                        translatedParagraph = "Translation failed: ${error.message}"
                                                                        Toast.makeText(
                                                                            context,
                                                                            "Translation failed. Please try again.",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    }
                                                                )
                                                            } catch (e: Exception) {
                                                                isTranslating = false
                                                                translatedParagraph = "Translation error occurred"
                                                                Toast.makeText(
                                                                    context,
                                                                    "Translation error. Please try again.",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                    } else {
                                                        Toast.makeText(
                                                            context,
                                                            "Please select text with 500 characters or less for translation.",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            },
                                            isSelectionMode = isTranslationSelectionMode
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
            }

            // Error State
            uiState.error?.let { error ->
                LaunchedEffect(error) {
                    // Show snackbar or handle error
                    viewModel.clearError()
                }
            }

            // Success Message
            uiState.message?.let { message ->
                LaunchedEffect(message) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    viewModel.clearMessage()
                }
            }

            // Error State
            uiState.error?.let { error ->
                LaunchedEffect(error) {
                    // Show snackbar or handle error
                    viewModel.clearError()
                }
            }

            // Translation Selection Mode Overlay
            if (isTranslationSelectionMode) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )
                
                // FAB positioned above instruction panel
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        // FAB above instruction panel with better spacing
                        Box(
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.7f),
                                    shape = CircleShape
                                )
                                .padding(4.dp)
                        ) {
                            FloatingActionButton(
                                onClick = { showAddNoteDialog = true },
                                containerColor = SecondaryBlue,
                                contentColor = Color.Black,
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(84.dp)
                                    .shadow(
                                        elevation = 12.dp,
                                        shape = CircleShape,
                                        ambientColor = Color.Black.copy(alpha = 0.5f),
                                        spotColor = Color.Black.copy(alpha = 0.5f)
                                    )
                                    .border(
                                        width = 2.dp,
                                        color = Color.Black.copy(alpha = 0.8f),
                                        shape = CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Note",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        
                        // Bottom instruction panel
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = DarkSurface),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Tap any paragraph to translate",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                Button(
                                    onClick = { isTranslationSelectionMode = false },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = SecondaryBlue
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Cancel",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
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
        
        // Translation Dialog
        if (showTranslationDialog) {
            TranslationDialog(
                originalText = originalParagraph,
                translatedText = translatedParagraph,
                isLoading = isTranslating,
                onDismiss = {
                    showTranslationDialog = false
                    originalParagraph = ""
                    translatedParagraph = ""
                    isTranslating = false
                }
            )
        }
        }
    }
}







