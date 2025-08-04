package com.edify.learning.presentation.revision

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.edify.learning.data.model.Exercise
import com.edify.learning.data.model.UserResponse
import com.edify.learning.data.model.RevisionSubmission
import com.edify.learning.presentation.components.MarkdownText
import com.edify.learning.ui.theme.*
import android.util.Log
import java.io.File
import java.io.FileOutputStream

private const val TAG = "QuestionScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    chapterId: String,
    questionIndex: Int,
    chapterTitle: String,
    onNavigateBack: () -> Unit,
    onNavigateToHistory: () -> Unit,
    viewModel: RevisionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(chapterId) {
        viewModel.loadExercises(chapterId)
    }
    
    val exercise = uiState.exercises.getOrNull(questionIndex)
    val userResponse = uiState.userResponses[questionIndex]
    val revisionSubmissions = uiState.revisionSubmissions[questionIndex] ?: emptyList()
    
    var textResponse by remember(userResponse) { 
        mutableStateOf(userResponse?.textResponse ?: "") 
    }
    var imageUri by remember(userResponse) { 
        mutableStateOf(userResponse?.imageUri?.let { Uri.parse(it) }) 
    }
    
    // Track current session feedback - reset when screen is re-entered
    var currentSessionFeedback by remember { mutableStateOf<String?>(null) }
    
    // Observe new submissions to capture feedback
    LaunchedEffect(revisionSubmissions.size) {
        if (revisionSubmissions.isNotEmpty()) {
            val latestSubmission = revisionSubmissions.maxByOrNull { it.submittedAt }
            if (latestSubmission?.isEvaluated == true && latestSubmission.gemmaFeedback != null) {
                currentSessionFeedback = latestSubmission.gemmaFeedback
            }
        }
    }
    
    val hasResponse = textResponse.isNotBlank() || imageUri != null
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            try {
                // Copy image to app's internal storage
                val inputStream = context.contentResolver.openInputStream(selectedUri)
                val fileName = "exercise_${questionIndex}_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                
                imageUri = Uri.fromFile(file)
                Log.d(TAG, "Image response changed for exercise $questionIndex: ${file.absolutePath}")
                
                // Update response with image
                val response = UserResponse(
                    id = userResponse?.id ?: 0,
                    chapterId = "", // Will be set in ViewModel
                    exerciseIndex = questionIndex,
                    textResponse = textResponse.takeIf { it.isNotBlank() },
                    imageUri = file.absolutePath
                )
                viewModel.updateUserResponse(chapterId, questionIndex, response)
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "Question ${questionIndex + 1}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(
                            painter = painterResource(id = R.drawable.history_24dp_ffffff_fill0_wght400_grad0_opsz24),
                            contentDescription = "View history",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary,
                    navigationIconContentColor = TextPrimary
                ),
                windowInsets = WindowInsets(0.dp)
            )
        },
        bottomBar = {
            if (hasResponse) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Button(
                        onClick = {
                            Log.d(TAG, "Submit for evaluation clicked for exercise $questionIndex")
                            viewModel.submitForEvaluation(chapterId, questionIndex)
                        },
                        enabled = !uiState.isEvaluating,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = White),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        if (uiState.isEvaluating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = DarkBackground,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Evaluating...", color = DarkBackground)
                        } else {
                            Text("Submit for Review", color = DarkBackground)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
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
            exercise == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Question not found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Question text
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = DarkCard),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Question",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextSecondary
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = exercise.question,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextPrimary
                            )
                        }
                    }
                    
                    // Text response input
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = DarkCard),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Your Answer",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextSecondary
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            OutlinedTextField(
                                value = textResponse,
                                onValueChange = { newText ->
                                    Log.d(TAG, "Text response changed for exercise $questionIndex")
                                    textResponse = newText
                                    val response = UserResponse(
                                        id = userResponse?.id ?: 0,
                                        chapterId = "", // Will be set in ViewModel
                                        exerciseIndex = questionIndex,
                                        textResponse = newText.takeIf { it.isNotBlank() },
                                        imageUri = userResponse?.imageUri
                                    )
                                    viewModel.updateUserResponse(chapterId, questionIndex, response)
                                },
                                label = { Text("Type your answer here...") },
                                placeholder = { Text("Write your detailed answer...") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 120.dp),
                                minLines = 5,
                                maxLines = 12,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = TextPrimary,
                                    focusedLabelColor = TextPrimary,
                                    cursorColor = TextPrimary
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Image upload button
                            OutlinedButton(
                                onClick = { imagePickerLauncher.launch("image/*") },
                                modifier = Modifier.fillMaxWidth(1.25f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = TextPrimary
                                )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.image_24dp_ffffff_fill0_wght400_grad0_opsz24),
                                    contentDescription = "Upload image",
                                    modifier = Modifier.size(16.dp),
                                    tint = TextPrimary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (imageUri != null) "Change Image" else "Upload Image",
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                    
                    // Show uploaded image
                    imageUri?.let { uri ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = DarkCard),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Uploaded Image",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextSecondary
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Uploaded response image",
                                    modifier = Modifier
                                        .wrapContentWidth(Alignment.Start),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                    
                    // Show current session feedback if available
                    currentSessionFeedback?.let { feedback ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = SecondaryBlue.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Feedback",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextSecondary
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                MarkdownText(
                                    markdown = feedback,
                                    color = TextPrimary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                    
                    // Add bottom spacing for the fixed submit button
                    if (hasResponse) {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}