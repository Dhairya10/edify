package com.edify.learning.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Chapter
import com.edify.learning.presentation.home.SearchResult
import com.edify.learning.presentation.home.SearchResultType
import com.edify.learning.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSubject: (String) -> Unit,
    onNavigateToChapter: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(16.dp)
    ) {
        // Welcome Header
        Text(
            text = "Welcome back,",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        Text(
            text = "Alex",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Search Bar
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SecondaryBlue,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = SecondaryBlue
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
        colors = CardDefaults.cardColors(containerColor = PrimaryBlue),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Continue Reading",
                style = MaterialTheme.typography.labelMedium,
                color = White.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = lastReadChapter?.let { chapter ->
                    "Chapter ${chapter.chapterNumber}: ${chapter.title}"
                } ?: "Continue your studies",
                style = MaterialTheme.typography.titleMedium,
                color = White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Text(
                text = subject.name,
                style = MaterialTheme.typography.bodyMedium,
                color = White.copy(alpha = 0.8f)
            )
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
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Subject Icon/Color
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(android.graphics.Color.parseColor(subject.color))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = subject.name.first().toString(),
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Subject Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = subject.description,
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (searchResult.type) {
                        SearchResultType.SUBJECT -> "Subject"
                        SearchResultType.CHAPTER -> "Chapter"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = SecondaryBlue,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(
                            color = SecondaryBlue.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = searchResult.title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = searchResult.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
            
            if (searchResult.description.isNotBlank()) {
                Text(
                    text = searchResult.description,
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
