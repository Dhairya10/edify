package com.edify.learning.presentation.revision

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.res.painterResource
import com.edify.learning.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.edify.learning.data.model.Exercise
import com.edify.learning.data.model.UserResponse
import com.edify.learning.data.model.RevisionSubmission
import com.edify.learning.data.model.FeedbackCategory
import com.edify.learning.ui.theme.*
import androidx.compose.ui.graphics.Color
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevisionScreen(
    chapterId: String,
    chapterTitle: String,
    onNavigateBack: () -> Unit,
    viewModel: RevisionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(chapterId) {
        viewModel.loadExercises(chapterId)
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = White,
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "Revision: $chapterTitle",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = White,
                    titleContentColor = Black,
                    navigationIconContentColor = Black
                ),
                windowInsets = WindowInsets(0.dp)
            )
        }
    ) { paddingValues ->
        val errorMessage = uiState.error
        
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = White)
                }
            }
            errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "❌ Error Loading Exercises",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadExercises(chapterId) },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Text("Retry", color = White)
                        }
                    }
                }
            }
            uiState.exercises.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No exercises available for this chapter",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SecondaryBlue.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "📝 Exercise Mode",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = White
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Answer the questions below. You can type your response or upload an image of your work.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Progress: ${uiState.completedCount}/${uiState.exercises.size} completed",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = White
                                )
                            }
                        }
                    }
                    
                    itemsIndexed(uiState.exercises) { index, exercise ->
                        ExerciseCard(
                            exercise = exercise,
                            exerciseIndex = index,
                            userResponse = uiState.userResponses[index],
                            revisionSubmissions = uiState.revisionSubmissions[index] ?: emptyList(),
                            isEvaluating = uiState.isEvaluating,
                            onResponseChanged = { response ->
                                viewModel.updateUserResponse(chapterId, index, response)
                            },
                            onSubmitForEvaluation = {
                                viewModel.submitForEvaluation(chapterId, index)
                            },
                            onShowHistory = {
                                viewModel.loadRevisionHistory(chapterId, index)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    exerciseIndex: Int,
    userResponse: UserResponse?,
    revisionSubmissions: List<RevisionSubmission>,
    isEvaluating: Boolean,
    onResponseChanged: (UserResponse) -> Unit,
    onSubmitForEvaluation: () -> Unit,
    onShowHistory: () -> Unit
) {
    var textResponse by remember(userResponse) { 
        mutableStateOf(userResponse?.textResponse ?: "") 
    }
    var imageUri by remember(userResponse) { 
        mutableStateOf(userResponse?.imageUri?.let { Uri.parse(it) }) 
    }
    var showHistory by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val hasResponse = textResponse.isNotBlank() || imageUri != null
    val latestSubmission = revisionSubmissions.maxByOrNull { it.submittedAt }
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            try {
                // Copy image to app's internal storage
                val inputStream = context.contentResolver.openInputStream(selectedUri)
                val fileName = "exercise_${exerciseIndex}_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                
                imageUri = Uri.fromFile(file)
                
                // Update response with image
                val response = UserResponse(
                    id = userResponse?.id ?: 0,
                    chapterId = "", // Will be set in ViewModel
                    exerciseIndex = exerciseIndex,
                    textResponse = textResponse.takeIf { it.isNotBlank() },
                    imageUri = file.absolutePath
                )
                onResponseChanged(response)
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Question header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${exerciseIndex + 1}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = White
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
                    focusedBorderColor = White,
                    focusedLabelColor = White,
                    cursorColor = White
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
                        contentColor = White
                    )
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Upload image",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Upload Image",
                        color = White
                    )
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
            
            // Submission and history section
            if (hasResponse) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Submit for evaluation button
                    Button(
                        onClick = onSubmitForEvaluation,
                        enabled = !isEvaluating,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                    ) {
                        if (isEvaluating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Evaluating...", color = White)
                        } else {
                            Icon(
                                Icons.Default.Send,
                                contentDescription = "Submit for evaluation",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Submit for Review", color = White)
                        }
                    }
                    
                    // History button
                    if (revisionSubmissions.isNotEmpty()) {
                        OutlinedButton(
                            onClick = { 
                                showHistory = !showHistory
                                if (showHistory) onShowHistory()
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = White)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.history_24dp_ffffff_fill0_wght400_grad0_opsz24),
                                contentDescription = "View history",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("History (${revisionSubmissions.size})", color = White)
                        }
                    }
                }
            }
            
            // Show latest feedback if available
            latestSubmission?.let { submission ->
                if (submission.isEvaluated && submission.gemmaFeedback != null && submission.gemmaGrade != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = when (submission.gemmaGrade) {
                                FeedbackCategory.EXCELLENT -> Color(0xFF45B7D1).copy(alpha = 0.1f)
                                FeedbackCategory.GOOD_JOB -> Color(0xFF4ECDC4).copy(alpha = 0.1f)
                                FeedbackCategory.NEEDS_IMPROVEMENT -> Color(0xFFFF6B6B).copy(alpha = 0.1f)
                            }
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Latest Feedback:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextSecondary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = when (submission.gemmaGrade) {
                                            FeedbackCategory.EXCELLENT -> Color(0xFF45B7D1)
                                            FeedbackCategory.GOOD_JOB -> Color(0xFF4ECDC4)
                                            FeedbackCategory.NEEDS_IMPROVEMENT -> Color(0xFFFF6B6B)
                                        }
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = submission.gemmaGrade.displayName,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = White,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = submission.gemmaFeedback,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }
            
            // History dialog/expansion
            if (showHistory && revisionSubmissions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "Submission History",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        revisionSubmissions.sortedByDescending { it.submittedAt }.forEach { submission ->
                            HistoryItem(submission = submission)
                            if (submission != revisionSubmissions.last()) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(submission: RevisionSubmission) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Submitted ${java.text.SimpleDateFormat("MMM dd, HH:mm", java.util.Locale.getDefault()).format(java.util.Date(submission.submittedAt))}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
                
                if (submission.isEvaluated && submission.gemmaGrade != null) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when (submission.gemmaGrade) {
                                FeedbackCategory.EXCELLENT -> Color(0xFF45B7D1)
                                FeedbackCategory.GOOD_JOB -> Color(0xFF4ECDC4)
                                FeedbackCategory.NEEDS_IMPROVEMENT -> Color(0xFFFF6B6B)
                            }
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = submission.gemmaGrade.displayName,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Text(
                        text = "Evaluating...",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }
            }
            
            if (submission.isEvaluated && !submission.gemmaFeedback.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = submission.gemmaFeedback,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary
                )
            }
        }
    }
}
