package com.edify.learning.data.repository

import android.content.Context
import com.edify.learning.data.dao.*
import com.edify.learning.data.model.*
import com.edify.learning.data.service.GemmaService
import com.edify.learning.data.util.ContentLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val subjectDao: SubjectDao,
    private val chapterDao: ChapterDao,
    private val noteDao: NoteDao,
    private val chatDao: ChatDao,
    private val userResponseDao: UserResponseDao,
    private val gemmaService: GemmaService
) {
    
    // Subject operations
    fun getAllSubjects(): Flow<List<Subject>> = subjectDao.getAllSubjects()
    
    suspend fun getSubjectById(id: String): Subject? = subjectDao.getSubjectById(id)
    
    fun searchSubjects(query: String): Flow<List<Subject>> = subjectDao.searchSubjects(query)
    
    fun searchChapters(query: String): Flow<List<Chapter>> = chapterDao.searchChapters(query)
    
    suspend fun insertSubject(subject: Subject) = subjectDao.insertSubject(subject)
    
    suspend fun updateSubjectProgress(subjectId: String) {
        val completedChapters = chapterDao.getCompletedChaptersCount(subjectId)
        subjectDao.updateProgress(subjectId, completedChapters)
    }
    
    suspend fun updateLastReadChapter(subjectId: String, chapterId: String) {
        subjectDao.updateLastReadChapter(subjectId, chapterId)
    }
    
    // Chapter operations
    fun getChaptersBySubject(subjectId: String): Flow<List<Chapter>> = 
        chapterDao.getChaptersBySubject(subjectId)
    
    suspend fun getChapterById(id: String): Chapter? = chapterDao.getChapterById(id)
    
    suspend fun insertChapter(chapter: Chapter) = chapterDao.insertChapter(chapter)
    
    suspend fun updateChapterProgress(chapterId: String, progress: Float) {
        chapterDao.updateReadingProgress(chapterId, progress)
        
        // Mark as completed if progress is 100%
        if (progress >= 1.0f) {
            chapterDao.updateCompletionStatus(chapterId, true)
            
            // Update subject progress
            val chapter = chapterDao.getChapterById(chapterId)
            chapter?.let { updateSubjectProgress(it.subjectId) }
        }
    }
    
    // Note operations
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    
    fun getAllUserNotes(): Flow<List<Note>> = noteDao.getAllUserNotes()
    
    fun getUserNotesBySubject(subjectId: String): Flow<List<Note>> = 
        noteDao.getUserNotesBySubject(subjectId)
    
    fun getNotesByChapter(chapterId: String): Flow<List<Note>> = 
        noteDao.getNotesByChapter(chapterId)
    
    fun getNotesByChapterAndType(chapterId: String, type: NoteType): Flow<List<Note>> = 
        noteDao.getNotesByChapterAndType(chapterId, type)
    
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    
    // Chat operations
    fun getChatMessages(sessionId: String): Flow<List<ChatMessage>> = 
        chatDao.getMessagesBySession(sessionId)
    
    suspend fun insertChatMessage(message: ChatMessage) = chatDao.insertMessage(message)
    
    suspend fun generateGemmaResponse(
        prompt: String,
        context: String? = null,
        isExplanation: Boolean = false
    ): Result<String> {
        val fullPrompt = if (context != null) {
            gemmaService.createEducationalPrompt(context, prompt, isExplanation)
        } else {
            prompt
        }
        
        return gemmaService.generateResponse(fullPrompt)
    }
    
    fun generateGemmaResponseStream(
        prompt: String,
        context: String? = null,
        isExplanation: Boolean = false
    ): Flow<String> {
        val fullPrompt = if (context != null) {
            gemmaService.createEducationalPrompt(context, prompt, isExplanation)
        } else {
            prompt
        }
        
        return gemmaService.generateResponseStream(fullPrompt)
    }
    
    suspend fun initializeGemma(): Result<Unit> = gemmaService.initializeModel()
    
    suspend fun getChapterSummaryForChat(chapterId: String): String {
        return ContentLoader.loadChapterSummaryForChat(context, chapterId)
    }
    
    // Exercise and User Response operations
    fun getExercisesForChapter(chapterId: String): List<Exercise> {
        return ContentLoader.loadExercisesForChapter(context, chapterId)
    }
    
    fun getUserResponsesForChapter(chapterId: String): Flow<List<UserResponse>> {
        return userResponseDao.getResponsesForChapter(chapterId)
    }
    
    suspend fun getUserResponse(chapterId: String, exerciseIndex: Int): UserResponse? {
        return userResponseDao.getResponse(chapterId, exerciseIndex)
    }
    
    suspend fun saveUserResponse(response: UserResponse) {
        userResponseDao.insertOrUpdateResponse(response)
    }
    
    suspend fun deleteUserResponse(response: UserResponse) {
        userResponseDao.deleteResponse(response)
    }
    
    suspend fun getResponseCountForChapter(chapterId: String): Int {
        return userResponseDao.getResponseCountForChapter(chapterId)
    }
    
    // Refresh chapter titles from JSON files
    suspend fun refreshChapterTitles() {
        try {
            println("Repository: Refreshing chapter titles from JSON files")
            val subjects = getAllSubjects().first()
            
            subjects.forEach { subject ->
                println("Repository: Refreshing titles for subject ${subject.name} (${subject.id})")
                val chaptersFromJson = ContentLoader.loadChaptersForSubject(context, subject.id)
                val existingChapters = getChaptersBySubject(subject.id).first()
                
                chaptersFromJson.forEach { jsonChapter ->
                    val existingChapter = existingChapters.find { it.id == jsonChapter.id }
                    if (existingChapter != null && existingChapter.title != jsonChapter.title) {
                        println("Repository: Updating title for chapter ${jsonChapter.id}: '${existingChapter.title}' -> '${jsonChapter.title}'")
                        val updatedChapter = existingChapter.copy(title = jsonChapter.title)
                        chapterDao.updateChapter(updatedChapter)
                    }
                }
            }
            println("Repository: Chapter title refresh completed")
        } catch (e: Exception) {
            println("Repository: Error refreshing chapter titles: ${e.message}")
            e.printStackTrace()
        }
    }

    // Content data initialization from JSON files
    suspend fun initializeContentData() {
        // Check if data already exists
        val existingSubjects = getAllSubjects().first()
        if (existingSubjects.isNotEmpty()) {
            println("Repository: Found ${existingSubjects.size} existing subjects: ${existingSubjects.map { "${it.name} (${it.totalChapters} chapters)" }}")
            
            // Check if this is real content or fallback data by looking at chapter count and content
            val totalChapters = existingSubjects.sumOf { it.totalChapters }
            if (totalChapters < 40) {  // Real content should have many more chapters
                println("Repository: Existing content appears to be fallback data (only $totalChapters chapters), forcing re-initialization")
                // Clear existing data and reload from assets
                subjectDao.deleteAllSubjects()
                chapterDao.deleteAllChapters()
            } else {
                println("Repository: Content already exists with $totalChapters chapters, refreshing titles from JSON")
                refreshChapterTitles()
                return
            }
        }
        
        println("Repository: Starting content initialization from assets")
        
        try {
            // Load subjects from content files
            println("Repository: Loading subjects from ContentLoader")
            val subjects = ContentLoader.loadAllSubjects(context)
            
            if (subjects.isNotEmpty()) {
                println("Repository: Found ${subjects.size} subjects: ${subjects.map { it.name }}")
                subjectDao.insertSubjects(subjects)
                
                // Load chapters for each subject
                subjects.forEach { subject ->
                    println("Repository: Loading chapters for subject ${subject.name} (${subject.id})")
                    val chapters = ContentLoader.loadChaptersForSubject(context, subject.id)
                    if (chapters.isNotEmpty()) {
                        println("Repository: Found ${chapters.size} chapters for ${subject.name}")
                        chapterDao.insertChapters(chapters)
                    } else {
                        println("Repository: No chapters found for ${subject.name}")
                    }
                }
                println("Repository: Content initialization completed successfully")
            } else {
                println("Repository: No subjects found, falling back to sample data")
                initializeFallbackData()
            }
        } catch (e: Exception) {
            println("Repository: Error during content initialization: ${e.message}")
            e.printStackTrace()
            // Fallback to sample data if content loading fails
            initializeFallbackData()
        }
    }
    
    private suspend fun initializeFallbackData() {
        val mathSubject = Subject(
            id = "math_001",
            name = "Mathematics",
            description = "Advanced Mathematics for Students",
            color = "#5ea9ec",
            iconRes = "ic_math",
            totalChapters = 2,
            completedChapters = 0,
            lastReadChapterId = "math_ch1"
        )
        
        val scienceSubject = Subject(
            id = "science_001",
            name = "Science",
            description = "Physics, Chemistry, and Biology",
            color = "#28a745",
            iconRes = "ic_science",
            totalChapters = 1,
            completedChapters = 0
        )
        
        subjectDao.insertSubjects(listOf(mathSubject, scienceSubject))
        
        val mathChapter1 = Chapter(
            id = "math_ch1",
            subjectId = "math_001",
            title = "Chapter 1: Number Systems",
            description = "Understanding different number systems and their properties",
            content = createSampleMathContent(),
            chapterNumber = 1,
            estimatedReadingTime = 45
        )
        
        chapterDao.insertChapters(listOf(mathChapter1))
    }
    
    private fun createSampleMathContent(): String {
        return """
        {
            "contentItems": [
                {
                    "type": "text",
                    "blockId": "ch01-b001",
                    "selectedText": "Remember the difference between rational and irrational numbers.",
                    "startOffset": 0,
                    "endOffset": 65
                },
                {
                    "type": "text",
                    "blockId": "ch01-b002",
                    "selectedText": "Rational numbers can be expressed as fractions, while irrational numbers cannot be expressed as simple fractions.",
                    "startOffset": 0,
                    "endOffset": 112
                }
            ]
        }
        """.trimIndent()
    }
    
    private fun createSamplePolynomialContent(): String {
        return """
        {
            "contentItems": [
                {
                    "type": "text",
                    "blockId": "ch02-b001",
                    "selectedText": "A polynomial is an expression consisting of variables and coefficients, that involves only the operations of addition, subtraction, multiplication, and non-negative integer exponentiation of variables.",
                    "startOffset": 0,
                    "endOffset": 200
                },
                {
                    "type": "text",
                    "blockId": "ch02-b002",
                    "selectedText": "Examples of polynomials include: x² + 2x + 1, 3x³ - 2x + 5, and 7x⁴ + x² - 3x + 9.",
                    "startOffset": 0,
                    "endOffset": 85
                }
            ]
        }
        """.trimIndent()
    }
    
    private fun createSampleScienceContent(): String {
        return """
        {
            "contentItems": [
                {
                    "type": "text",
                    "blockId": "ch01-b001",
                    "selectedText": "Whittaker's five kingdoms are key.",
                    "startOffset": 0,
                    "endOffset": 35
                },
                {
                    "type": "text",
                    "blockId": "ch01-b002",
                    "selectedText": "Aristotle was the earliest to attempt a more scientific basis for classification.",
                    "startOffset": 0,
                    "endOffset": 82
                }
            ]
        }
        """.trimIndent()
    }
}
