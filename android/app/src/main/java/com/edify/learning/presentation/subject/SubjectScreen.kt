package com.edify.learning.presentation.subject

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.model.Exercise
import com.edify.learning.data.model.UserResponse
import com.edify.learning.data.model.RevisionResponse
import com.edify.learning.data.model.RevisionQuestion
import com.edify.learning.data.model.ChapterRevisionData
import com.edify.learning.data.model.FeedbackCategory
import com.edify.learning.ui.theme.*
import java.io.File
import java.io.FileOutputStream

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
            .background(DarkBackground)
    ) {
        // Top App Bar
        CenterAlignedTopAppBar(
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
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = DarkSurface,
                titleContentColor = TextPrimary,
                navigationIconContentColor = TextPrimary
            ),
            windowInsets = WindowInsets(0.dp)
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Mode Toggle
            ModeToggle(
                selectedMode = selectedMode,
                onModeChanged = viewModel::onModeChanged,
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
                    colors = CardDefaults.cardColors(containerColor = DarkCard)
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
                    RevisionModeContent(
                        revisionData = uiState.revisionData,
                        revisionResponses = uiState.revisionResponses,
                        isSubmittingAnswer = uiState.isSubmittingAnswer,
                        onResponseChanged = viewModel::updateRevisionResponse,
                        onSubmitAnswer = viewModel::submitRevisionResponseForReview
                    )
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
    ) {
        ModeToggleButton(
            text = "Learn",
            isSelected = selectedMode == SubjectMode.LEARNING,
            onClick = { onModeChanged(SubjectMode.LEARNING) },
            modifier = Modifier.weight(1f)
        )
        ModeToggleButton(
            text = "Revise",
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
                color = if (isSelected) White else Color.Transparent
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Black else TextSecondary,
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
        modifier = Modifier.padding(bottom = 12.dp)
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
fun RevisionModeContent(
    revisionData: List<ChapterRevisionData>,
    revisionResponses: Map<String, RevisionResponse>,
    isSubmittingAnswer: Boolean,
    onResponseChanged: (String, Int, String, String?) -> Unit,
    onSubmitAnswer: (String, Int, String, String?) -> Unit
) {
    var selectedQuestion by remember { mutableStateOf<Triple<ChapterRevisionData, Int, RevisionQuestion>?>(null) }
    var expandedChapters by remember { mutableStateOf(setOf<String>()) }
    
    Column {
        // Header
        Text(
            text = "Revision Questions",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (revisionData.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📚 No Revision Questions Available",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary
                    )
                    Text(
                        text = "Revision questions will appear here when available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                revisionData.forEach { chapterData ->
                    item {
                        RevisionChapterCard(
                            chapterData = chapterData,
                            isExpanded = expandedChapters.contains(chapterData.chapterId),
                            onToggleExpanded = { chapterId ->
                                expandedChapters = if (expandedChapters.contains(chapterId)) {
                                    expandedChapters - chapterId
                                } else {
                                    expandedChapters + chapterId
                                }
                            },
                            revisionResponses = revisionResponses,
                            onQuestionClick = { question, questionIndex ->
                                selectedQuestion = Triple(chapterData, questionIndex, question)
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Revision Question Modal
    selectedQuestion?.let { (chapterData, questionIndex, question) ->
        val responseKey = "${chapterData.chapterId}_${questionIndex}"
        val revisionResponse = revisionResponses[responseKey]
        
        RevisionQuestionModal(
            question = question,
            questionIndex = questionIndex,
            chapterTitle = chapterData.chapterTitle,
            revisionResponse = revisionResponse,
            isSubmittingAnswer = isSubmittingAnswer,
            onDismiss = { selectedQuestion = null },
            onResponseChanged = { userAnswer, imageUri ->
                onResponseChanged(chapterData.chapterId, questionIndex, userAnswer, imageUri)
            },
            onSubmitAnswer = { userAnswer, imageUri ->
                onSubmitAnswer(chapterData.chapterId, questionIndex, userAnswer, imageUri)
            }
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
                    
                    // Extract summary from chapter content JSON
                    val chapterSummary = remember(chapter.id, chapter.content) {
                        try {
                            val jsonContent = chapter.content
                            if (jsonContent.isNotBlank()) {
                                val jsonObject = org.json.JSONObject(jsonContent)
                                val contentObject = jsonObject.optJSONObject("content")
                                val summaryObject = contentObject?.optJSONObject("summary")
                                val summary = summaryObject?.optString("summary")
                                
                                if (!summary.isNullOrBlank()) {
                                    // Remove HTML tags and extract first 100 characters
                                    val cleanSummary = summary.replace(Regex("<[^>]*>"), "").trim()
                                    if (cleanSummary.length > 100) {
                                        cleanSummary.substring(0, 100) + "..."
                                    } else {
                                        cleanSummary
                                    }
                                } else {
                                    chapter.description
                                }
                            } else {
                                chapter.description
                            }
                        } catch (e: Exception) {
                            // Fallback to description if JSON parsing fails
                            chapter.description
                        }
                    }
                    
                    Text(
                        text = chapterSummary,
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
fun CollapsibleChapterCard(
    chapterExercises: ChapterExercises,
    isExpanded: Boolean,
    onToggleExpanded: (String) -> Unit,
    userResponses: Map<String, UserResponse>,
    onExerciseClick: (Exercise, Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Chapter Header - Clickable to expand/collapse
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpanded(chapterExercises.chapterId) }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = chapterExercises.chapterTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${chapterExercises.exercises.size} exercises",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = TextPrimary
                )
            }
            
            // Exercise List - Only shown when expanded
            if (isExpanded) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chapterExercises.exercises.forEachIndexed { exerciseIndex, exercise ->
                        val responseKey = "${chapterExercises.chapterId}_$exerciseIndex"
                        val userResponse = userResponses[responseKey]
                        val isCompleted = userResponse != null && 
                            (userResponse.textResponse?.isNotBlank() == true || userResponse.imageUri != null)
                        
                        ExerciseQuestionCard(
                            exercise = exercise,
                            exerciseIndex = exerciseIndex,
                            isCompleted = isCompleted,
                            onClick = { onExerciseClick(exercise, exerciseIndex) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseQuestionCard(
    exercise: Exercise,
    exerciseIndex: Int,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) SuccessColor.copy(alpha = 0.2f) else DarkCard
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Question ${exerciseIndex + 1}",
                    style = MaterialTheme.typography.labelMedium,
                    color = SecondaryBlue,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = exercise.question.take(100) + if (exercise.question.length > 100) "..." else "",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary,
                    maxLines = 2
                )
            }
            
            if (isCompleted) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = SuccessColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun SubjectExerciseCard(
    exercise: Exercise,
    exerciseIndex: Int,
    chapterTitle: String,
    userResponse: UserResponse?,
    onResponseChanged: (UserResponse) -> Unit
) {
    val context = LocalContext.current
    var textResponse by remember(userResponse) { 
        mutableStateOf(userResponse?.textResponse ?: "")
    }
    var imageUri by remember(userResponse) {
        mutableStateOf(userResponse?.imageUri?.let { Uri.parse(it) })
    }
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Save image to internal storage
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val fileName = "exercise_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                
                val savedUri = Uri.fromFile(file)
                imageUri = savedUri
                
                val response = UserResponse(
                    id = userResponse?.id ?: 0,
                    chapterId = "", // Will be set in ViewModel
                    exerciseIndex = exerciseIndex,
                    textResponse = textResponse.takeIf { it.isNotBlank() },
                    imageUri = savedUri.toString()
                )
                onResponseChanged(response)
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Question header with completion indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${exerciseIndex + 1}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                
                if (userResponse != null && (userResponse.textResponse?.isNotBlank() == true || userResponse.imageUri != null)) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Question text
            Text(
                text = exercise.question,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Text response input
            OutlinedTextField(
                value = textResponse,
                onValueChange = { newText ->
                    textResponse = newText
                    val response = UserResponse(
                        id = userResponse?.id ?: 0,
                        chapterId = "", // Will be set in ViewModel
                        exerciseIndex = exerciseIndex,
                        textResponse = newText.takeIf { it.isNotBlank() },
                        imageUri = userResponse?.imageUri
                    )
                    onResponseChanged(response)
                },
                label = { Text("Your answer") },
                placeholder = { Text("Type your answer here...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp),
                minLines = 3,
                maxLines = 8,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SecondaryBlue,
                    focusedLabelColor = SecondaryBlue,
                    cursorColor = SecondaryBlue,
                    unfocusedBorderColor = TextSecondary,
                    unfocusedLabelColor = TextSecondary
                )
            )
            

        }
    }
}

@Composable
fun ExerciseModal(
    exercise: Exercise,
    exerciseIndex: Int,
    chapterTitle: String,
    userResponse: UserResponse?,
    onDismiss: () -> Unit,
    onResponseChanged: (UserResponse) -> Unit
) {
    val context = LocalContext.current
    var textResponse by remember(userResponse) { 
        mutableStateOf(userResponse?.textResponse ?: "")
    }
    var imageUri by remember(userResponse) {
        mutableStateOf(userResponse?.imageUri?.let { Uri.parse(it) })
    }
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Save image to internal storage
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val fileName = "exercise_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                
                val savedUri = Uri.fromFile(file)
                imageUri = savedUri
                
                val response = UserResponse(
                    id = userResponse?.id ?: 0,
                    chapterId = "", // Will be set in ViewModel
                    exerciseIndex = exerciseIndex,
                    textResponse = textResponse.takeIf { it.isNotBlank() },
                    imageUri = savedUri.toString()
                )
                onResponseChanged(response)
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    Dialog(
        onDismissRequest = { /* Do nothing - only close with X button */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .heightIn(max = 600.dp), // Set maximum height for the modal
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight() // Fill the available height within the constraint
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Question ${exerciseIndex + 1}",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = chapterTitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp)) // Reduced spacing

                // Main content - scrollable
                Box(
                    modifier = Modifier
                        .weight(1f) // Take available space
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp) // Reduced spacing
                    ) {
                        item {
                            // Question text
                            Text(
                                text = exercise.question,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextPrimary,
                                lineHeight = 24.sp
                            )
                        }

                        item {
                            // Text response input with character counter
                            Column {
                                OutlinedTextField(
                                    value = textResponse,
                                    onValueChange = { newText ->
                                        if (newText.length <= 5000) {
                                            textResponse = newText
                                            val response = UserResponse(
                                                id = userResponse?.id ?: 0,
                                                chapterId = "", // Will be set in ViewModel
                                        exerciseIndex = exerciseIndex,
                                        textResponse = newText.takeIf { it.isNotBlank() },
                                        imageUri = userResponse?.imageUri
                                    )
                                    onResponseChanged(response)
                                }
                            },
                            label = { Text("Your answer") },
                            placeholder = { Text("Type your answer here...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 240.dp),
                            minLines = 8,
                            maxLines = 15,
                                )
                                // Character counter
                                Text(
                                    text = "${textResponse.length}/5000",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (textResponse.length > 4500) ErrorColor else TextSecondary,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }


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

@Composable
fun RevisionChapterCard(
    chapterData: ChapterRevisionData,
    isExpanded: Boolean,
    onToggleExpanded: (String) -> Unit,
    revisionResponses: Map<String, RevisionResponse>,
    onQuestionClick: (RevisionQuestion, Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Chapter Header - Clickable to expand/collapse
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpanded(chapterData.chapterId) }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = chapterData.chapterTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${chapterData.questions.size} questions",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = TextPrimary
                )
            }
            
            // Question List - Only shown when expanded
            if (isExpanded) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    chapterData.questions.forEachIndexed { questionIndex, question ->
                        val responseKey = "${chapterData.chapterId}_$questionIndex"
                        val revisionResponse = revisionResponses[responseKey]
                        val isCompleted = revisionResponse?.isSubmitted == true
                        
                        RevisionQuestionCard(
                            question = question,
                            questionIndex = questionIndex,
                            isCompleted = isCompleted,
                            revisionResponse = revisionResponse,
                            onClick = { onQuestionClick(question, questionIndex) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RevisionQuestionCard(
    question: RevisionQuestion,
    questionIndex: Int,
    isCompleted: Boolean,
    revisionResponse: RevisionResponse?,
    onClick: () -> Unit
) {
    val feedbackCategory = revisionResponse?.feedbackCategory?.let { 
        FeedbackCategory.valueOf(it) 
    }
    
    val cardColor = when {
        !isCompleted -> DarkCard
        feedbackCategory == FeedbackCategory.EXCELLENT -> SuccessColor.copy(alpha = 0.2f)
        feedbackCategory == FeedbackCategory.GOOD_JOB -> SecondaryBlue.copy(alpha = 0.2f)
        feedbackCategory == FeedbackCategory.NEEDS_IMPROVEMENT -> ErrorColor.copy(alpha = 0.2f)
        else -> DarkCard
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${questionIndex + 1}",
                        style = MaterialTheme.typography.labelMedium,
                        color = SecondaryBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    if (isCompleted && feedbackCategory != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "• ${feedbackCategory.displayName}",
                            style = MaterialTheme.typography.labelSmall,
                            color = when (feedbackCategory) {
                                FeedbackCategory.EXCELLENT -> SuccessColor
                                FeedbackCategory.GOOD_JOB -> SecondaryBlue
                                FeedbackCategory.NEEDS_IMPROVEMENT -> ErrorColor
                            },
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Text(
                    text = question.question.take(100) + if (question.question.length > 100) "..." else "",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary,
                    maxLines = 2
                )
            }
            
            if (isCompleted) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = when (feedbackCategory) {
                        FeedbackCategory.EXCELLENT -> SuccessColor
                        FeedbackCategory.GOOD_JOB -> SecondaryBlue
                        FeedbackCategory.NEEDS_IMPROVEMENT -> ErrorColor
                        else -> SuccessColor
                    },
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun RevisionQuestionModal(
    question: RevisionQuestion,
    questionIndex: Int,
    chapterTitle: String,
    revisionResponse: RevisionResponse?,
    isSubmittingAnswer: Boolean,
    onDismiss: () -> Unit,
    onResponseChanged: (String, String?) -> Unit,
    onSubmitAnswer: (String, String?) -> Unit
) {
    val context = LocalContext.current
    var userAnswer by remember(revisionResponse) { 
        mutableStateOf(revisionResponse?.userAnswer ?: "")
    }
    var imageUri by remember(revisionResponse) {
        mutableStateOf(revisionResponse?.imageUri?.let { Uri.parse(it) })
    }
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Save image to internal storage
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val fileName = "revision_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                
                val savedUri = Uri.fromFile(file)
                imageUri = savedUri
                onResponseChanged(userAnswer, savedUri.toString())
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    Dialog(
        onDismissRequest = { /* Do nothing - only close with X button */ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .heightIn(max = 700.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 24.dp, start = 20.dp, end = 20.dp)
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Question ${questionIndex + 1}",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = chapterTitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                // Main content - scrollable
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
                    ) {
                        item {
                            // Question text
                            Text(
                                text = question.question,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextPrimary,
                                lineHeight = 24.sp
                            )
                        }

                        item {
                            // Text response input with character counter
                            Column {
                                OutlinedTextField(
                                    value = userAnswer,
                                    onValueChange = { newText ->
                                        if (newText.length <= 5000) {
                                            userAnswer = newText
                                            onResponseChanged(newText, imageUri?.toString())
                                        }
                                    },
                                    label = { Text("Your answer") },
                                    placeholder = { Text("Type your answer here...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 200.dp),
                                    minLines = 6,
                                    maxLines = 12,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = TextSecondary,
                                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f),
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary,
                                        cursorColor = TextPrimary,
                                        focusedContainerColor = DarkCard,
                                        unfocusedContainerColor = DarkCard,
                                        focusedLabelColor = TextPrimary,
                                        unfocusedLabelColor = TextSecondary
                                    )
                                )
                                // Character counter
                                Text(
                                    text = "${userAnswer.length}/5000",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (userAnswer.length > 4500) ErrorColor else TextSecondary,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }

                        item {
                            // Image upload section
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedButton(
                                    onClick = { imagePickerLauncher.launch("image/*") },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = White
                                    )
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Upload image",
                                        tint = White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Upload Image",
                                        color = White
                                    )
                                }
                            }
                        }

                        // Show uploaded image
                        imageUri?.let { uri ->
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 200.dp),
                                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Uploaded response image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                        
                        // Show feedback if submitted
                        if (revisionResponse?.isSubmitted == true && revisionResponse.gemmaFeedback != null) {
                            item {
                                FeedbackCard(
                                    feedback = revisionResponse.gemmaFeedback,
                                    category = revisionResponse.feedbackCategory?.let { 
                                        FeedbackCategory.valueOf(it) 
                                    } ?: FeedbackCategory.NEEDS_IMPROVEMENT
                                )
                            }
                        }
                        
                        // No submit button here, moved out of LazyColumn

                    }
                }
                
                // Fixed submit button at bottom with padding
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 36.dp, top = 8.dp)
                ) {
                    val canSubmit = userAnswer.isNotBlank() && !isSubmittingAnswer
                    val isAlreadySubmitted = revisionResponse?.isSubmitted == true
                    
                    Button(
                        onClick = { 
                            if (canSubmit) {
                                onSubmitAnswer(userAnswer, imageUri?.toString())
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = canSubmit && !isAlreadySubmitted,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White,
                            contentColor = Black,
                            disabledContainerColor = White,
                            disabledContentColor = Black
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (isSubmittingAnswer) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Getting Feedback...",
                                color = Black
                            )
                        } else if (isAlreadySubmitted) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Submitted",
                                modifier = Modifier.size(16.dp),
                                tint = White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Submitted",
                                color = White
                            )
                        } else {
                            Text(
                                text = "Submit for Review",
                                color = Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeedbackCard(
    feedback: String,
    category: FeedbackCategory
) {
    val categoryColor = when (category) {
        FeedbackCategory.EXCELLENT -> SuccessColor
        FeedbackCategory.GOOD_JOB -> SecondaryBlue
        FeedbackCategory.NEEDS_IMPROVEMENT -> ErrorColor
    }
    
    val categoryIcon = when (category) {
        FeedbackCategory.EXCELLENT -> "🌟"
        FeedbackCategory.GOOD_JOB -> "👍"
        FeedbackCategory.NEEDS_IMPROVEMENT -> "💪"
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = categoryColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = categoryIcon,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = category.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    color = categoryColor,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Clean up feedback text by removing category markers
            val cleanFeedback = feedback
                .replace(Regex("\\[.*?\\]"), "")
                .trim()
            
            Text(
                text = cleanFeedback,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                lineHeight = 20.sp
            )
        }
    }
}
