package com.edify.learning.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.edify.learning.presentation.components.BottomNavigationBar
import com.edify.learning.presentation.home.HomeScreen
import com.edify.learning.presentation.home.HomeViewModel
import com.edify.learning.presentation.subject.SubjectScreen
import com.edify.learning.presentation.subject.SubjectViewModel
import com.edify.learning.presentation.chapter.ChapterScreen
import com.edify.learning.presentation.chapter.ChapterViewModel
import com.edify.learning.presentation.notes.NotesScreen
import com.edify.learning.presentation.chat.ChatScreen
import com.edify.learning.presentation.chat.ChatViewModel
import com.edify.learning.presentation.revision.RevisionScreen
import com.edify.learning.presentation.profile.ProfileScreen
import com.edify.learning.ui.theme.EdifyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdifyTheme {
                EdifyApp()
            }
        }
    }
}

@Composable
fun EdifyApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Routes that should show bottom navigation
    val bottomNavRoutes = listOf("library", "notes", "profile")
    val showBottomNav = currentRoute in bottomNavRoutes
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomNav) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigateToTab = { route ->
                        navController.navigate(route) {
                            // Pop up to the start destination to avoid building up a large stack
                            popUpTo("library") {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "library",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Bottom Navigation Screens
            composable("library") {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToSubject = { subjectId ->
                        navController.navigate("subject/$subjectId")
                    },
                    onNavigateToChapter = { chapterId ->
                        navController.navigate("chapter/$chapterId")
                    }
                )
            }
            
            composable("notes") {
                NotesScreen()
            }
            
            composable("profile") {
                ProfileScreen()
            }
            
            // Detail Screens (without bottom navigation)
            composable("subject/{subjectId}") { backStackEntry ->
                val subjectId = backStackEntry.arguments?.getString("subjectId") ?: ""
                val viewModel: SubjectViewModel = hiltViewModel()
                
                LaunchedEffect(subjectId) {
                    viewModel.loadSubject(subjectId)
                }
                
                SubjectScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToChapter = { chapterId ->
                        navController.navigate("chapter/$chapterId")
                    }
                )
            }
            
            composable("chapter/{chapterId}") { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getString("chapterId") ?: ""
                val viewModel: ChapterViewModel = hiltViewModel()
                
                LaunchedEffect(chapterId) {
                    viewModel.loadChapter(chapterId)
                }
                
                ChapterScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToNotes = { chapterId ->
                        navController.navigate("notes/$chapterId")
                    },
                    onNavigateToChat = { chapterId, selectedText ->
                        navController.navigate("chat/$chapterId?selectedText=$selectedText")
                    }
                )
            }
            
            composable("notes/{chapterId}") { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getString("chapterId") ?: ""
                // Use the unified NotesScreen - it will show all notes with subject filtering
                NotesScreen()
            }
            
            composable("chat/{chapterId}?selectedText={selectedText}") { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getString("chapterId") ?: ""
                val selectedText = backStackEntry.arguments?.getString("selectedText")
                val viewModel: ChatViewModel = hiltViewModel()
                
                ChatScreen(
                    viewModel = viewModel,
                    chapterId = chapterId,
                    selectedText = selectedText,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable("revision/{chapterId}?title={title}") { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getString("chapterId") ?: ""
                val chapterTitle = backStackEntry.arguments?.getString("title") ?: "Chapter"
                
                RevisionScreen(
                    chapterId = chapterId,
                    chapterTitle = chapterTitle,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
