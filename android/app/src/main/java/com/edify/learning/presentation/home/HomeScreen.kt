package com.edify.learning.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edify.learning.R
import java.util.Calendar
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.presentation.home.SearchResult
import com.edify.learning.presentation.home.SearchResultType
import com.edify.learning.ui.theme.*
import com.edify.learning.utils.DeveloperMode
import androidx.compose.foundation.border
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSubject: (String) -> Unit,
    onNavigateToChapter: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val greeting by viewModel.personalizedGreeting.collectAsState()
    val userName by viewModel.userName.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(20.dp)
    ) {
        
        // Welcome Header with enhanced styling
        Text(
            text = greeting,
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Enhanced Search Bar - Dark Theme
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::onSearchQueryChanged,
            placeholder = { Text("Search subjects and chapters...", color = TextSecondary) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextSecondary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(
                        onClick = { viewModel.onSearchQueryChanged("") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear search",
                            tint = TextSecondary
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 28.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkCard,
                unfocusedBorderColor = DarkCard,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = SecondaryBlue,
                focusedContainerColor = DarkCard,
                unfocusedContainerColor = DarkCard
            )
        )
        
        // Search Results or Continue Reading Section
        if (searchQuery.isNotBlank() && uiState.searchResults.isNotEmpty()) {
            Text(
                text = "Search Results",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyColumn {
                items(uiState.searchResults) { searchResult ->
                    SearchResultCard(
                        searchResult = searchResult,
                        onClick = {
                            when (searchResult.type) {
                                SearchResultType.SUBJECT -> onNavigateToSubject(searchResult.id)
                                SearchResultType.CHAPTER -> onNavigateToChapter(searchResult.id)
                            }
                        },
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        } else {
            // Continue Reading Section
            uiState.continueReadingSubject?.let { subject ->
                ContinueReadingCard(
                    subject = subject,
                    lastReadChapter = uiState.lastReadChapter,
                    onContinueReading = { 
                        onNavigateToChapter(subject.lastReadChapterId ?: "")
                    },
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
        
        // Your Subjects Section (only show when not searching)
        if (searchQuery.isBlank()) {
            Text(
                text = "Your Subjects",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
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
        
        // Subjects List (only show when not searching)
        if (searchQuery.isBlank()) {
            LazyColumn {
                items(uiState.subjects) { subject ->
                    SubjectCard(
                        subject = subject,
                        onClick = { onNavigateToSubject(subject.id) },
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContinueReadingCard(
    subject: Subject,
    lastReadChapter: Chapter?,
    onContinueReading: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onContinueReading() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkCard)
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "CONTINUE READING",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(getSubjectColor(subject.name).copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = getSubjectIcon(subject.name)),
                            contentDescription = subject.name,
                            tint = White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = subject.name,
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        if (lastReadChapter != null) {
                            Text(
                                text = lastReadChapter.title,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectCard(
    subject: Subject,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Subject Icon with colored background
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                getSubjectColor(subject.name).copy(alpha = 0.3f),
                                getSubjectColor(subject.name).copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = getSubjectIcon(subject.name)),
                    contentDescription = subject.name,
                    tint = White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Subject Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun SearchResultCard(
    searchResult: SearchResult,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = when (searchResult.type) {
                            SearchResultType.SUBJECT -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                            SearchResultType.CHAPTER -> SecondaryBlue.copy(alpha = 0.1f)
                        }
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = when (searchResult.type) {
                            SearchResultType.SUBJECT -> "Subject"
                            SearchResultType.CHAPTER -> "Chapter"
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = when (searchResult.type) {
                            SearchResultType.SUBJECT -> Color(0xFF4CAF50)
                            SearchResultType.CHAPTER -> SecondaryBlue
                        },
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = searchResult.title,
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = searchResult.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )

            if (searchResult.description.isNotBlank()) {
                Text(
                    text = searchResult.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

// Helper function to get subject icon
@Composable
fun getSubjectIcon(subjectName: String): Int {
    return when (subjectName.lowercase()) {
        "science" -> R.drawable.science_24dp_ffffff_fill1_wght400_grad0_opsz24
        "mathematics" -> R.drawable.functions_24dp_ffffff_fill1_wght400_grad0_opsz24
        "english" -> R.drawable.slab_serif_24dp_ffffff_fill1_wght400_grad0_opsz24
        "social science" -> R.drawable.communities_24dp_ffffff_fill1_wght400_grad0_opsz24
        else -> R.drawable.book_5_24dp_ffffff_fill1_wght400_grad0_opsz24
    }
}

// Helper function to get subject color with dark blue tint
@Composable
fun getSubjectColor(subjectName: String): Color {
    return when (subjectName.lowercase()) {
        "science" -> Color(0xFF4A90E2)
        "mathematics" -> Color(0xFFF5A623)
        "english" -> Color(0xFF50E3C2)
        "social science" -> Color(0xFF7ED321)
        else -> SecondaryBlue
    }.copy(alpha = 0.8f)
}