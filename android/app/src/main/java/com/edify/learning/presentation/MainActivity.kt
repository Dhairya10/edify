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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edify.learning.presentation.home.HomeScreen
import com.edify.learning.presentation.home.HomeViewModel
import com.edify.learning.presentation.subject.SubjectScreen
import com.edify.learning.presentation.subject.SubjectViewModel
import com.edify.learning.presentation.chapter.ChapterScreen
import com.edify.learning.presentation.chapter.ChapterViewModel
import com.edify.learning.presentation.notes.NotesScreen
import com.edify.learning.presentation.notes.NotesViewModel
import com.edify.learning.presentation.chat.ChatScreen
import com.edify.learning.presentation.chat.ChatViewModel
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
    
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
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
                val viewModel: NotesViewModel = hiltViewModel()
                
                LaunchedEffect(chapterId) {
                    viewModel.loadNotes(chapterId)
                }
                
                NotesScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
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
        }
    }
}
