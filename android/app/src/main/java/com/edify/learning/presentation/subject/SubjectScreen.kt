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
                containerColor = DarkSurface,
                titleContentColor = TextPrimary,
                navigationIconContentColor = TextPrimary
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Mode Toggle removed - showing only chapters
            
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
            
            // Always show Learning mode content in the UI
            // Revision mode removed from UI but code remains intact
            LearningModeContent(
                chapters = uiState.chapters,
                onChapterClick = onNavigateToChapter
            )
        }
    }
}

@Composable
fun ModeToggle(
    selectedMode: SubjectMode,
    onModeChanged: (SubjectMode) -> Unit,
    modifier: Modifier = Modifier
) {
    // Only showing Learning mode tab, Revision mode hidden from UI
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BackgroundGray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp),
    ) {
        ModeToggleButton(
            text = "Learning",
            isSelected = true,  // Always selected as it's the only option
            onClick = { onModeChanged(SubjectMode.LEARNING) },
            modifier = Modifier.fillMaxWidth()
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
    exercises: List<ChapterExercises>,
    userResponses: Map<String, UserResponse>,
    onResponseChanged: (String, Int, UserResponse) -> Unit
) {
    var selectedExercise by remember { mutableStateOf<Triple<ChapterExercises, Int, Exercise>?>(null) }
    var expandedChapters by remember { mutableStateOf(setOf<String>()) }
    
    Column {
        // Header
        Text(
            text = "Exercises",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (exercises.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📚 No Exercises Available",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextSecondary
                    )
                    Text(
                        text = "Exercises will appear here when available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                exercises.forEach { chapterExercises ->
                    item {
                        CollapsibleChapterCard(
                            chapterExercises = chapterExercises,
                            isExpanded = expandedChapters.contains(chapterExercises.chapterId),
                            onToggleExpanded = { chapterId ->
                                expandedChapters = if (expandedChapters.contains(chapterId)) {
                                    expandedChapters - chapterId
                                } else {
                                    expandedChapters + chapterId
                                }
                            },
                            userResponses = userResponses,
                            onExerciseClick = { exercise, exerciseIndex ->
                                selectedExercise = Triple(chapterExercises, exerciseIndex, exercise)
                            }
                        )
                    }
                }
            }
        }
    }
    
    // Exercise Modal
    selectedExercise?.let { (chapterExercises, exerciseIndex, exercise) ->
        val responseKey = "${chapterExercises.chapterId}_$exerciseIndex"
        val userResponse = userResponses[responseKey]
        
        ExerciseModal(
            exercise = exercise,
            exerciseIndex = exerciseIndex,
            chapterTitle = chapterExercises.chapterTitle,
            userResponse = userResponse,
            onDismiss = { selectedExercise = null },
            onResponseChanged = { response ->
                onResponseChanged(chapterExercises.chapterId, exerciseIndex, response)
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
                                    // Extract first 100 characters with ellipsis
                                    if (summary.length > 100) {
                                        summary.substring(0, 100) + "..."
                                    } else {
                                        summary
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
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Image upload section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Or upload an image:",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                
                OutlinedButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PrimaryBlue
                    )
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Upload image",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Upload Image")
                }
            }
            
            // Show uploaded image
            imageUri?.let { uri ->
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    AsyncImage(
                        model = uri,
                        contentDescription = "Uploaded response image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
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
                                        contentColor = PrimaryBlue
                                    )
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = "Upload image",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Upload Image")
                                }
                            }
                        }

                        // Show uploaded image
                        imageUri?.let { uri ->
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 200.dp), // Reduced max height
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
