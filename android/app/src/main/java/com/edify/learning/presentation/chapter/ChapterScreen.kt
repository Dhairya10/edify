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
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import com.edify.learning.data.model.ChapterContent
import com.edify.learning.data.util.ContentParser
import com.edify.learning.presentation.components.HtmlRenderer
import com.edify.learning.presentation.components.ImageViewer
import com.edify.learning.data.service.GemmaService
import com.edify.learning.data.service.TranslationCacheService
import com.edify.learning.ui.theme.*
import java.io.File
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

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
    onNavigateToRevision: (String, String) -> Unit,
    gemmaService: GemmaService,
    translationCacheService: TranslationCacheService
) {
    val uiState by viewModel.uiState.collectAsState()
    val userLanguagePreference by viewModel.userLanguagePreference.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var showAddNoteDialog by remember { mutableStateOf(false) }
    var showImageViewer by remember { mutableStateOf(false) }
    var selectedImagePath by remember { mutableStateOf("") }
    var showToolsMenu by remember { mutableStateOf(false) }
    
    // Translation states
    var isTranslated by remember { mutableStateOf(false) }
    var isTranslating by remember { mutableStateOf(false) }
    var translatedContent by remember { mutableStateOf("") }
    var originalContent by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = uiState.chapter?.title ?: "Chapter",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                    // Tools menu button
                    Box {
                        IconButton(
                            onClick = { showToolsMenu = true }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.apps_24dp_ffffff_fill0_wght400_grad0_opsz24),
                                contentDescription = "Tools",
                                tint = TextPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        DropdownMenu(
                            expanded = showToolsMenu,
                            onDismissRequest = { showToolsMenu = false },
                            modifier = Modifier.background(DarkSurface)
                        ) {
                            // Translate option - only show if user's language is not English
                            if (userLanguagePreference.lowercase() != "english") {
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.translate_24dp_ffffff_fill0_wght400_grad0_opsz24),
                                                contentDescription = null,
                                                tint = if (isTranslated) SecondaryBlue else TextPrimary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Text(
                                                text = if (isTranslated) "Show Original" else "Translate",
                                                color = TextPrimary,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    },
                                    onClick = {
                                        showToolsMenu = false
                                        if (!isTranslating) {
                                            uiState.chapter?.let { chapter ->
                                                val parsedContent = ContentParser.parseChapterContent(chapter.content)
                                                parsedContent?.htmlContent?.let { htmlContent ->
                                                    if (!isTranslated) {
                                                        // Store original content and start translation
                                                        originalContent = htmlContent
                                                        isTranslating = true
                                                        
                                                        coroutineScope.launch {
                                                            try {
                                                                // First, check if we have a cached translation
                                                                val cachedTranslation = translationCacheService.getCachedTranslation(
                                                                    chapterId = chapter.id,
                                                                    language = userLanguagePreference,
                                                                    originalContent = htmlContent
                                                                )
                                                                
                                                                if (cachedTranslation != null) {
                                                                    // Use cached translation
                                                                    translatedContent = cachedTranslation
                                                                    isTranslated = true
                                                                    isTranslating = false
                                                                    
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Loaded cached translation",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                } else {
                                                                    // No cache available, perform fresh translation
                                                                    val maxChunkSize = 25000 // Conservative limit for 32k tokens (~30k chars)
                                                                    
                                                                    if (htmlContent.length <= maxChunkSize) {
                                                                        // Content fits in single request - translate directly
                                                                        gemmaService.translateText(htmlContent, userLanguagePreference).fold(
                                                                            onSuccess = { translation ->
                                                                                translatedContent = translation
                                                                                isTranslated = true
                                                                                isTranslating = false
                                                                                
                                                                                // Cache the translation for future use
                                                                                coroutineScope.launch {
                                                                                    translationCacheService.cacheTranslation(
                                                                                        chapterId = chapter.id,
                                                                                        language = userLanguagePreference,
                                                                                        originalContent = htmlContent,
                                                                                        translatedContent = translation
                                                                                    )
                                                                                }
                                                                            },
                                                                            onFailure = { error ->
                                                                                isTranslating = false
                                                                                Toast.makeText(
                                                                                    context,
                                                                                    "Translation failed: ${error.message}",
                                                                                    Toast.LENGTH_SHORT
                                                                                ).show()
                                                                            }
                                                                        )
                                                                    } else {
                                                                        // Content is very long, translate in chunks but display all at once
                                                                        val chunks = splitHtmlIntoChunks(htmlContent, maxChunkSize)
                                                                        val translatedChunks = mutableListOf<String>()
                                                                        
                                                                        for (chunk in chunks) {
                                                                            gemmaService.translateText(chunk, userLanguagePreference).fold(
                                                                                onSuccess = { translation ->
                                                                                    translatedChunks.add(translation)
                                                                                },
                                                                                onFailure = { error ->
                                                                                    // If any chunk fails, show error and stop
                                                                                    isTranslating = false
                                                                                    Toast.makeText(
                                                                                        context,
                                                                                        "Translation failed: ${error.message}",
                                                                                        Toast.LENGTH_SHORT
                                                                                    ).show()
                                                                                    return@launch
                                                                                }
                                                                            )
                                                                        }
                                                                        
                                                                        // Display complete translated content at once
                                                                        val fullTranslation = translatedChunks.joinToString("")
                                                                        translatedContent = fullTranslation
                                                                        isTranslated = true
                                                                        isTranslating = false
                                                                        
                                                                        // Cache the complete translation
                                                                        coroutineScope.launch {
                                                                            translationCacheService.cacheTranslation(
                                                                                chapterId = chapter.id,
                                                                                language = userLanguagePreference,
                                                                                originalContent = htmlContent,
                                                                                translatedContent = fullTranslation
                                                                            )
                                                                        }
                                                                    }
                                                                }
                                                            } catch (e: Exception) {
                                                                isTranslating = false
                                                                Toast.makeText(
                                                                    context,
                                                                    "Translation error: ${e.message}",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                    } else {
                                                        // Switch back to original content
                                                        isTranslated = false
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    enabled = !isTranslating
                                )
                            }
                            
                            // Chat option
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.chat_24dp_ffffff_fill0_wght400_grad0_opsz24),
                                            contentDescription = null,
                                            tint = TextPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "Chat",
                                            color = TextPrimary,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                },
                                onClick = {
                                    showToolsMenu = false
                                    uiState.chapter?.let { chapter ->
                                        onNavigateToChat(chapter.id, null)
                                    }
                                }
                            )
                            
                            // Revision option
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.table_lamp_24dp_ffffff_fill0_wght400_grad0_opsz24),
                                            contentDescription = null,
                                            tint = TextPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "Revision",
                                            color = TextPrimary,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                },
                                onClick = {
                                    showToolsMenu = false
                                    uiState.chapter?.let { chapter ->
                                        onNavigateToRevision(chapter.id, chapter.title)
                                    }
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DarkBackground
                ),
                windowInsets = WindowInsets(0.dp)
            )
        },
        floatingActionButton = {
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
                            
                            // Translation status bar
                            if (isTranslating) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(20.dp),
                                            color = SecondaryBlue,
                                            strokeWidth = 2.dp
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Translating to $userLanguagePreference...",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextPrimary
                                        )
                                    }
                                }
                            } else if (isTranslated) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    colors = CardDefaults.cardColors(containerColor = SecondaryBlue.copy(alpha = 0.1f)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.translate_24dp_ffffff_fill0_wght400_grad0_opsz24),
                                            contentDescription = "Translated",
                                            tint = SecondaryBlue,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = "Content translated to $userLanguagePreference",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = SecondaryBlue,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            // Content
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Removed duplicate chapter header and description since HTML content already contains proper headings

                                item {
                                    // Display chapter content using HtmlRenderer
                                    val parsedContent = remember(chapter.content, isTranslated, translatedContent) {
                                        if (isTranslated && translatedContent.isNotEmpty()) {
                                            // Create a new ChapterContent with translated HTML
                                            ContentParser.parseChapterContent(chapter.content)?.copy(
                                                htmlContent = translatedContent
                                            )
                                        } else {
                                            ContentParser.parseChapterContent(chapter.content)
                                        }
                                    }

                                    if (parsedContent != null) {
                                        HtmlRenderer(
                                            content = parsedContent,
                                            modifier = Modifier.fillMaxWidth(),
                                            onParagraphSelected = { /* No longer needed */ },
                                            onTextSelected = { selectedText ->
                                                onNavigateToChat(chapter.id, selectedText)
                                            },
                                            isSelectionMode = false
                                        )
                                    } else {
                                        Text(
                                            text = "Unable to load chapter content",
                                            color = TextSecondary,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(16.dp)
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
    }
}

/**
 * Helper function to split HTML content into chunks while preserving structure
 */
private fun splitHtmlIntoChunks(htmlContent: String, maxChunkSize: Int): List<String> {
    val chunks = mutableListOf<String>()
    
    try {
        // Parse HTML to preserve structure
        val doc = org.jsoup.Jsoup.parse(htmlContent)
        val elements = doc.body().children()
        
        var currentChunk = StringBuilder()
        
        for (element in elements) {
            val elementHtml = element.outerHtml()
            
            // If adding this element would exceed chunk size, start a new chunk
            if (currentChunk.length + elementHtml.length > maxChunkSize && currentChunk.isNotEmpty()) {
                chunks.add(currentChunk.toString())
                currentChunk = StringBuilder()
            }
            
            // If single element is too large, split it further
            if (elementHtml.length > maxChunkSize) {
                // Add current chunk if not empty
                if (currentChunk.isNotEmpty()) {
                    chunks.add(currentChunk.toString())
                    currentChunk = StringBuilder()
                }
                
                // Split large element by sentences or paragraphs
                val text = element.text()
                val sentences = text.split(". ", "? ", "! ")
                var sentenceChunk = StringBuilder()
                val tagName = element.tagName()
                
                for (sentence in sentences) {
                    val sentenceWithTag = "<$tagName>$sentence.</$tagName>"
                    
                    if (sentenceChunk.length + sentenceWithTag.length > maxChunkSize && sentenceChunk.isNotEmpty()) {
                        chunks.add(sentenceChunk.toString())
                        sentenceChunk = StringBuilder()
                    }
                    
                    sentenceChunk.append(sentenceWithTag as String)
                }
                
                if (sentenceChunk.isNotEmpty()) {
                    chunks.add(sentenceChunk.toString())
                }
            } else {
                currentChunk.append(elementHtml as String)
            }
        }
        
        // Add remaining content
        if (currentChunk.isNotEmpty()) {
            chunks.add(currentChunk.toString())
        }
        
    } catch (e: Exception) {
        // Fallback: simple text-based chunking
        val text = htmlContent.replace(Regex("<[^>]*>"), " ").trim()
        var startIndex = 0
        
        while (startIndex < text.length) {
            val endIndex = minOf(startIndex + maxChunkSize, text.length)
            val chunk = text.substring(startIndex, endIndex)
            chunks.add(chunk)
            startIndex = endIndex
        }
    }
    
    // Ensure we always return at least one chunk
    if (chunks.isEmpty()) {
        chunks.add(htmlContent.take(maxChunkSize))
    }
    
    return chunks
}







