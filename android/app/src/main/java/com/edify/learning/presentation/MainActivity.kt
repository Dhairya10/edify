package com.edify.learning.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
import com.edify.learning.presentation.components.TranslationViewModel
import com.edify.learning.data.service.TranslationCacheService
import com.edify.learning.presentation.revision.RevisionScreen
import com.edify.learning.presentation.revision.QuestionScreen
import com.edify.learning.presentation.revision.QuestionHistoryScreen
import com.edify.learning.presentation.quest.QuestScreen
import com.edify.learning.presentation.quest.QuestDetailScreen
import com.edify.learning.presentation.developer.DeveloperModeScreen
import com.edify.learning.presentation.onboarding.OnboardingScreen
import com.edify.learning.presentation.onboarding.OnboardingViewModel
import com.edify.learning.presentation.profile.ProfileScreen
import com.edify.learning.presentation.profile.ProfileViewModel
import com.edify.learning.ui.theme.EdifyTheme
import com.edify.learning.utils.DeveloperMode
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
    
    // Check onboarding status
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()
    var hasCompletedOnboarding by remember { mutableStateOf<Boolean?>(null) }
    
    LaunchedEffect(Unit) {
        hasCompletedOnboarding = onboardingViewModel.checkOnboardingStatus()
    }
    
    // Routes that should show bottom navigation
    val bottomNavRoutes = buildList {
        add("library")
        add("notes")
        add("quest")
        add("profile")
        // Dev Mode is now shown on home screen instead of bottom nav
    }
    val showBottomNav = currentRoute in bottomNavRoutes
    
    // Show onboarding if not completed, otherwise show main app
    when (hasCompletedOnboarding) {
        null -> {
            // Loading state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
        false -> {
            // Show onboarding
            OnboardingScreen(
                onOnboardingComplete = {
                    hasCompletedOnboarding = true
                }
            )
        }
        true -> {
            // Show main app
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
                
                // Refresh the greeting every time user navigates to home screen
                LaunchedEffect(Unit) {
                    viewModel.refreshGreeting()
                }
                
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
                NotesScreen(
                    onNavigateToLibrary = {
                        navController.navigate("library") {
                            popUpTo("library") { inclusive = false }
                        }
                    }
                )
            }
            
            composable("quest") {
                QuestScreen(
                    onNavigateToQuestDetail = { questId ->
                        navController.navigate("quest_detail/$questId")
                    },
                    onNavigateToLibrary = {
                        navController.navigate("library") {
                            popUpTo("library") { inclusive = false }
                        }
                    }
                )
            }
            
            // Developer Mode Screen (only available when enabled - accessed from home screen now)
            if (DeveloperMode.ENABLED) {
                composable("developer") {
                    DeveloperModeScreen()
                }
            }
            
            // Profile Screen
            composable("profile") {
                val profileViewModel: ProfileViewModel = hiltViewModel()
                ProfileScreen(
                    viewModel = profileViewModel
                )
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
                val translationViewModel: TranslationViewModel = hiltViewModel()
                val gemmaService = translationViewModel.gemmaService
                val translationCacheService: TranslationCacheService = hiltViewModel<TranslationViewModel>().translationCacheService
                
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
                    },
                    onNavigateToRevision = { chapterId, chapterTitle ->
                        navController.navigate("revision/$chapterId?title=${chapterTitle}")
                    },
                    gemmaService = gemmaService,
                    translationCacheService = translationCacheService
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
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToQuestion = { questionIndex ->
                        navController.navigate("revision/$chapterId/question/$questionIndex?title=$chapterTitle")
                    }
                )
            }
            
            composable("revision/{chapterId}/question/{questionIndex}?title={title}") { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getString("chapterId") ?: ""
                val questionIndex = backStackEntry.arguments?.getString("questionIndex")?.toIntOrNull() ?: 0
                val chapterTitle = backStackEntry.arguments?.getString("title") ?: "Chapter"
                
                QuestionScreen(
                    chapterId = chapterId,
                    questionIndex = questionIndex,
                    chapterTitle = chapterTitle,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToHistory = {
                        navController.navigate("revision/$chapterId/question/$questionIndex/history?title=$chapterTitle")
                    }
                )
            }
            
            composable("revision/{chapterId}/question/{questionIndex}/history?title={title}") { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getString("chapterId") ?: ""
                val questionIndex = backStackEntry.arguments?.getString("questionIndex")?.toIntOrNull() ?: 0
                val chapterTitle = backStackEntry.arguments?.getString("title") ?: "Chapter"
                
                QuestionHistoryScreen(
                    chapterId = chapterId,
                    questionIndex = questionIndex,
                    chapterTitle = chapterTitle,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable("quest_detail/{questId}") { backStackEntry ->
                val questId = backStackEntry.arguments?.getString("questId") ?: ""
                
                QuestDetailScreen(
                    questId = questId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
            }
        }
    }
}
