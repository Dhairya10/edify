package com.edify.learning.presentation.revision

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.edify.learning.data.model.RevisionSubmission
import com.edify.learning.presentation.components.MarkdownText
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionHistoryScreen(
    chapterId: String,
    questionIndex: Int,
    chapterTitle: String,
    onNavigateBack: () -> Unit,
    viewModel: RevisionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(chapterId, questionIndex) {
        viewModel.loadExercises(chapterId)
        viewModel.loadRevisionHistory(chapterId, questionIndex)
    }
    
    val revisionSubmissions = uiState.revisionSubmissions[questionIndex] ?: emptyList()
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        text = "Question ${questionIndex + 1} History",
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
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary,
                    navigationIconContentColor = TextPrimary
                ),
                windowInsets = WindowInsets(0.dp)
            )
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
            revisionSubmissions.isEmpty() -> {
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
                            text = "📝",
                            style = MaterialTheme.typography.displayMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No submission history yet",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Submit your answer to see feedback history here",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "${revisionSubmissions.size} submission${if (revisionSubmissions.size == 1) "" else "s"}",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    
                    items(revisionSubmissions.sortedByDescending { it.submittedAt }) { submission ->
                        HistoryItem(submission = submission)
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
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        shape = RoundedCornerShape(12.dp)
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
                    text = "Submitted",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
                Text(
                    text = java.text.SimpleDateFormat("MMM dd, HH:mm", java.util.Locale.getDefault())
                        .format(java.util.Date(submission.submittedAt)),
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
            }
            
            // Show user response
            if (!submission.userTextResponse.isNullOrBlank() || !submission.userImageUri.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                
                Divider(
                    color = DarkSurface,
                    thickness = 1.dp
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Your Response",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Show text response
                if (!submission.userTextResponse.isNullOrBlank()) {
                    Text(
                        text = submission.userTextResponse,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                }
                
                // Show image response
                if (!submission.userImageUri.isNullOrBlank()) {
                    if (!submission.userTextResponse.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        AsyncImage(
                            model = submission.userImageUri,
                            contentDescription = "Your submitted image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            
            if (submission.isEvaluated && !submission.gemmaFeedback.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                
                Divider(
                    color = DarkSurface,
                    thickness = 1.dp
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Feedback",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                MarkdownText(
                    markdown = submission.gemmaFeedback,
                    color = TextPrimary,
                    fontSize = 14.sp
                )
            } else if (!submission.isEvaluated) {
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = SecondaryBlue,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Evaluating your response...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Evaluation completed but no feedback available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}