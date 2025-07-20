package com.edify.learning.presentation.chapter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import com.edify.learning.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edify.learning.data.model.ChapterContent
import com.edify.learning.data.util.ContentParser
import com.edify.learning.presentation.components.MarkdownRenderer
import com.edify.learning.presentation.components.ImageViewer
import com.edify.learning.ui.theme.*
import java.io.File

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
                
                // Content Selection Action Buttons (shown when content is selected)
                if (selectedContent.isNotBlank()) {
                    ContentSelectionActions(
                        onHighlight = {
                            if (isImageSelected) {
                                // Handle image highlighting - for now just clear selection
                                viewModel.clearContentSelection()
                            } else {
                                viewModel.onHighlightText(selectedContent, 0, selectedContent.length)
                                viewModel.clearContentSelection()
                            }
                        },
                        onExplain = {
                            uiState.chapter?.let { chapter ->
                                onNavigateToChat(chapter.id, selectedContent)
                            }
                            viewModel.clearContentSelection()
                        },
                        onCancel = {
                            viewModel.clearContentSelection()
                        },
                        isImageSelected = isImageSelected
                    )
                } else {
                    // Regular Action Buttons Row (shown when no content is selected)
                    ActionButtonsRow(
                        onAddNote = { showAddNoteDialog = true },
                        onExplain = { 
                            uiState.chapter?.let { chapter ->
                                onNavigateToChat(chapter.id, selectedText.ifBlank { null })
                            }
                        },
                        onHighlight = { 
                            if (selectedText.isNotBlank()) {
                                viewModel.onHighlightText(selectedText, 0, selectedText.length)
                            }
                        },
                        hasSelectedText = selectedText.isNotBlank()
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
        
        // Loading State
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
                            ChapterContent(
                                content = chapter.content,
                                chapterId = chapter.id,
                                onTextSelected = viewModel::onTextSelected,
                                onImageClick = { selectedImagePath = it; showImageViewer = true },
                                onContentSelected = viewModel::onContentSelected,
                                selectedContent = selectedContent,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        
                        // Content Selection Actions (shown at bottom when content is selected)
                        if (selectedContent.isNotBlank()) {
                            item {
                                ContentSelectionActions(
                                    onHighlight = {
                                        if (isImageSelected) {
                                            // Handle image highlighting - for now just clear selection
                                            viewModel.clearContentSelection()
                                        } else {
                                            viewModel.onHighlightText(selectedContent, 0, selectedContent.length)
                                            viewModel.clearContentSelection()
                                        }
                                    },
                                    onExplain = {
                                        uiState.chapter?.let { chapter ->
                                            onNavigateToChat(chapter.id, selectedContent)
                                        }
                                        viewModel.clearContentSelection()
                                    },
                                    onCancel = {
                                        viewModel.clearContentSelection()
                                    },
                                    isImageSelected = isImageSelected
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
        uiState.message?.let { message ->
            LaunchedEffect(message) {
                // Show snackbar or handle message
                viewModel.clearMessage()
            }
        }
    }
    
    // Add Note Dialog
    if (showAddNoteDialog) {
        AddNoteDialog(
            onDismiss = { showAddNoteDialog = false },
            onAddNote = { title, content ->
                // Handle adding note
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

@Composable
fun ChapterContent(
    content: String,
    chapterId: String,
    onTextSelected: (String) -> Unit,
    onImageClick: (String) -> Unit,
    onContentSelected: (String, Boolean) -> Unit,
    selectedContent: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val parsedContent = remember(content) {
        println("ChapterContent: Parsing content of length: ${content.length}")
        val parsed = ContentParser.parseChapterContent(content)
        println("ChapterContent: Parsed content: ${if (parsed != null) "SUCCESS" else "FAILED"}")
        if (parsed != null) {
            println("ChapterContent: Markdown text length: ${parsed.markdownText.length}")
            println("ChapterContent: Number of images: ${parsed.images.size}")
        }
        parsed
    }
    
    if (parsedContent != null) {
        // Use Android assets path for images
        val baseImagePath = "file:///android_asset/images"
        
        MarkdownRenderer(
            content = parsedContent,
            baseImagePath = baseImagePath,
            onTextSelected = onTextSelected,
            onImageClick = onImageClick,
            onContentSelected = onContentSelected,
            selectedContent = selectedContent,
            modifier = modifier.fillMaxWidth()
        )
    } else {
        // Fallback to simple text display
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextPrimary,
                lineHeight = 24.sp
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun ActionButtonsRow(
    onAddNote: () -> Unit,
    onExplain: () -> Unit,
    onHighlight: () -> Unit,
    hasSelectedText: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActionButton(
            text = "Add Note",
            icon = Icons.Default.Add,
            onClick = onAddNote,
            modifier = Modifier.weight(1f)
        )
        
        ActionButton(
            text = "Explain",
            icon = Icons.Default.Info,
            onClick = onExplain,
            enabled = hasSelectedText,
            modifier = Modifier.weight(1f)
        )
        
        ActionButton(
            text = "Highlight",
            icon = Icons.Default.Edit,
            onClick = onHighlight,
            enabled = hasSelectedText,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) SecondaryBlue else SecondaryBlue.copy(alpha = 0.3f),
            contentColor = White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

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

@Composable
fun ContentSelectionActions(
    onHighlight: () -> Unit,
    onExplain: () -> Unit,
    onCancel: () -> Unit,
    isImageSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isImageSelected) "Image Selected" else "Text Selected",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                
                IconButton(
                    onClick = onCancel
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel Selection",
                        tint = TextSecondary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Highlight Button
                Button(
                    onClick = onHighlight,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryBlue,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_marker),
                        contentDescription = "Highlight",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Highlight",
                        fontWeight = FontWeight.Medium
                    )
                }
                
                // Explain Button
                Button(
                    onClick = onExplain,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat),
                        contentDescription = "Explain",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Explain",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private fun getSampleContent(): String {
    return """
        A polynomial is an expression consisting of variables and coefficients, that involves only the operations of addition, subtraction, multiplication, and non-negative integer exponentiation of variables.
        
        Examples of polynomials include:
        • x² + 2x + 1
        • 3x³ - 2x + 5
        • 7x⁴ + x² - 3x + 9
        
        Polynomials are fundamental in algebra and appear in many areas of mathematics and science. They are used to approximate functions, solve equations, and model real-world phenomena.
        
        The degree of a polynomial is the highest power of the variable in the expression. For example, in the polynomial 3x³ - 2x + 5, the degree is 3.
        
        Understanding polynomials is crucial for advancing in mathematics, as they form the foundation for more complex topics like calculus and differential equations.
    """.trimIndent()
}
