package com.edify.learning.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Exercise
import com.edify.learning.data.model.RevisionQuestion
import com.edify.learning.data.model.ChapterRevisionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.jsonArray
import java.io.IOException

object ContentLoader {
    private val json = Json.Default
    
    suspend fun loadAllSubjects(context: Context): List<Subject> = withContext(Dispatchers.IO) {
        val subjects = mutableListOf<Subject>()
        
        // Mathematics
        val mathChapters = loadChaptersFromDirectory(context, "maths")
        if (mathChapters.isNotEmpty()) {
            subjects.add(
                Subject(
                    id = "math_001",
                    name = "Mathematics",
                    description = "Advanced Mathematics for Students",
                    color = "#5ea9ec",
                    iconRes = "ic_math",
                    totalChapters = mathChapters.size,
                    completedChapters = 0,
                    lastReadChapterId = null
                )
            )
        }
        
        // Science
        val scienceChapters = loadChaptersFromDirectory(context, "science")
        if (scienceChapters.isNotEmpty()) {
            subjects.add(
                Subject(
                    id = "science_001",
                    name = "Science",
                    description = "Physics, Chemistry, and Biology",
                    color = "#28a745",
                    iconRes = "ic_science",
                    totalChapters = scienceChapters.size,
                    completedChapters = 0,
                    lastReadChapterId = null
                )
            )
        }
        
        // English
        val englishChapters = loadChaptersFromDirectory(context, "english")
        if (englishChapters.isNotEmpty()) {
            subjects.add(
                Subject(
                    id = "english_001",
                    name = "English",
                    description = "Language and Literature",
                    color = "#dc3545",
                    iconRes = "ic_english",
                    totalChapters = englishChapters.size,
                    completedChapters = 0,
                    lastReadChapterId = null
                )
            )
        }
        
        // Remove Social Science as it's no longer available
        
        subjects
    }
    
    suspend fun loadChaptersForSubject(context: Context, subjectId: String): List<Chapter> = withContext(Dispatchers.IO) {
        val directory = when (subjectId) {
            "math_001" -> "maths"
            "science_001" -> "science"
            "english_001" -> "english"
            else -> return@withContext emptyList()
        }
        
        loadChaptersFromDirectory(context, directory, subjectId)
    }
    
    private fun loadChaptersFromDirectory(context: Context, directoryPath: String, subjectId: String? = null): List<Chapter> {
        val chapters = mutableListOf<Chapter>()
        
        try {
            println("ContentLoader: Starting to load chapters from directory: $directoryPath")
            val assetManager = context.assets
            val files = assetManager.list(directoryPath)?.filter { it.endsWith(".json") }?.sorted()
            
            if (files.isNullOrEmpty()) {
                println("ContentLoader: No JSON files found in directory $directoryPath")
                println("ContentLoader: Available directories in assets: ${assetManager.list("")?.joinToString()}")
                return emptyList()
            }
            
            println("ContentLoader: Found ${files.size} files in $directoryPath: ${files.joinToString()}")
            
            files.forEachIndexed { index, fileName ->
                try {
                    println("ContentLoader: Loading file $fileName")
                    val content = assetManager.open("$directoryPath/$fileName").bufferedReader().use { it.readText() }
                    println("ContentLoader: File content length: ${content.length} characters")
                    val filename = fileName.substringBeforeLast(".")
                    
                    // Parse JSON to get chapter info
                    val jsonObject = json.parseToJsonElement(content).jsonObject
                    val chapterId = jsonObject["id"]?.jsonPrimitive?.content ?: filename
                    val subject = jsonObject["subject"]?.jsonPrimitive?.content ?: ""
                    val chapterTitle = jsonObject["title"]?.jsonPrimitive?.content ?: "Untitled Chapter"
                    val chapterDescription = "Chapter content"
                    val estimatedTime = estimateReadingTime(content)
                    
                    println("ContentLoader: Using title from JSON: '$chapterTitle' for file $fileName")
                    
                    val actualSubjectId = subjectId ?: when (subject) {
                        "maths" -> "math_001"
                        "science" -> "science_001"
                        "english" -> "english_001"
                        else -> "unknown_001"
                    }
                    
                    val chapter = Chapter(
                        id = chapterId,
                        subjectId = actualSubjectId,
                        title = chapterTitle,
                        description = chapterDescription,
                        content = content,
                        chapterNumber = index + 1,
                        estimatedReadingTime = estimatedTime
                    )
                    
                    chapters.add(chapter)
                    println("ContentLoader: Successfully loaded chapter: $chapterTitle")
                    println("ContentLoader: Chapter content preview: ${content.take(200)}...")
                    
                } catch (e: Exception) {
                    println("ContentLoader: Error loading chapter from $fileName: ${e.message}")
                    e.printStackTrace()
                }
            }
        } catch (e: IOException) {
            println("ContentLoader: Error accessing directory $directoryPath: ${e.message}")
            e.printStackTrace()
        }
        
        println("ContentLoader: Loaded ${chapters.size} chapters from $directoryPath")
        return chapters
    }
    
    // Helper functions removed as they're no longer needed with the new JSON structure
    
    private fun estimateReadingTime(content: String): Int {
        try {
            val jsonObject = json.parseToJsonElement(content).jsonObject
            val htmlContent = jsonObject["content"]?.jsonObject?.get("html_content")?.jsonPrimitive?.content
            
            htmlContent?.let { html ->
                // Remove HTML tags and count words
                val cleanText = html.replace(Regex("<[^>]*>"), " ")
                val wordCount = cleanText.split("\\s+".toRegex()).filter { it.isNotBlank() }.size
                // Average reading speed: 200 words per minute
                return (wordCount / 200).coerceAtLeast(1)
            }
        } catch (e: Exception) {
            // Ignore parsing errors
        }
        
        return 15 // Default reading time
    }
    
    fun loadExercisesForChapter(context: Context, chapterId: String): List<Exercise> {
        try {
            println("ContentLoader: Loading exercises for chapterId: '$chapterId'")
            
            // Determine the correct directory based on chapter ID pattern
            val directoryAndFilename = getDirectoryAndFilename(context, chapterId)
            if (directoryAndFilename == null) {
                println("ContentLoader: Could not find file for chapter '$chapterId'")
                return emptyList()
            }
            val (directory, filename) = directoryAndFilename
            println("ContentLoader: Looking for filename: '$filename' in directory: '$directory'")
            
            try {
                val assetManager = context.assets
                val inputStream = assetManager.open("$directory/$filename")
                val content = inputStream.bufferedReader().use { it.readText() }
                
                println("ContentLoader: Found file '$filename' in directory: '$directory'")
                println("ContentLoader: Content length: ${content.length} characters")
                
                val exercises = parseExercisesFromContent(content)
                println("ContentLoader: Parsed ${exercises.size} exercises from '$filename'")
                
                if (exercises.isNotEmpty()) {
                    println("ContentLoader: First exercise: '${exercises.first().question.take(100)}...'")
                }
                
                return exercises
                
            } catch (e: Exception) {
                println("ContentLoader: File not found: '$directory/$filename' - ${e.message}")
                return emptyList()
            }
            
        } catch (e: Exception) {
            println("ContentLoader: ERROR loading exercises for chapter '$chapterId': ${e.message}")
            e.printStackTrace()
            return emptyList()
        }
    }
    
    private fun getDirectoryAndFilename(context: Context, chapterId: String): Pair<String, String>? {
        // Since chapter IDs are UUIDs, we need to search all directories to find the matching file
        val directories = listOf("english", "science", "maths", "social_science")
        
        for (directory in directories) {
            try {
                val files = context.assets.list(directory) ?: continue
                for (file in files) {
                    if (file.endsWith(".json")) {
                        try {
                            val content = context.assets.open("$directory/$file").bufferedReader().use { it.readText() }
                            val jsonObject = json.parseToJsonElement(content).jsonObject
                            val fileChapterId = jsonObject["id"]?.jsonPrimitive?.content
                            
                            if (fileChapterId == chapterId) {
                                println("ContentLoader: Found chapter $chapterId in $directory/$file")
                                return Pair(directory, file)
                            }
                        } catch (e: Exception) {
                            // Skip files that can't be parsed
                            continue
                        }
                    }
                }
            } catch (e: Exception) {
                continue
            }
        }
        
        println("ContentLoader: Could not find file for chapter ID: $chapterId")
        return null
    }
    
    suspend fun loadChapterSummaryForChat(context: Context, chapterId: String): String = withContext(Dispatchers.IO) {
        try {
            val directoryAndFilename = getDirectoryAndFilename(context, chapterId)
            if (directoryAndFilename == null) {
                return@withContext "Unable to find chapter content for this discussion."
            }
            val (directory, filename) = directoryAndFilename
            val content = context.assets.open("$directory/$filename").bufferedReader().use { it.readText() }
            
            val jsonObject = json.parseToJsonElement(content).jsonObject
            val contentObject = jsonObject["content"]?.jsonObject
            val summaryObject = contentObject?.get("summary")?.jsonObject
            
            // Use root-level title from JSON, fallback to summary title
            val rootTitle = jsonObject["title"]?.jsonPrimitive?.content ?: ""
            val summaryTitle = summaryObject?.get("title")?.jsonPrimitive?.content ?: ""
            val actualTitle = rootTitle.ifBlank { summaryTitle }
            
            if (summaryObject != null) {
                val keyConcepts = summaryObject["key_concepts"]?.jsonArray?.joinToString(", ") { 
                    it.jsonPrimitive.content 
                } ?: ""
                val summary = summaryObject["summary"]?.jsonPrimitive?.content ?: ""
                
                return@withContext buildString {
                    if (actualTitle.isNotBlank()) {
                        appendLine("Chapter Title: $actualTitle")
                        appendLine()
                    }
                    if (keyConcepts.isNotBlank()) {
                        appendLine("Key Concepts: $keyConcepts")
                        appendLine()
                    }
                    if (summary.isNotBlank()) {
                        appendLine("Chapter Summary:")
                        appendLine(summary)
                    }
                }
            }
            
            // Fallback: extract basic info from the chapter
            val htmlContent = contentObject?.get("html_content")?.jsonPrimitive?.content ?: ""
            val cleanText = htmlContent.replace(Regex("<[^>]*>"), " ").replace(Regex("\\s+"), " ")
            val firstParagraph = cleanText.take(300)
            return@withContext "Chapter Content Overview:\n$firstParagraph"
            
        } catch (e: Exception) {
            println("ContentLoader: Error loading chapter summary for '$chapterId': ${e.message}")
            return@withContext "Unable to load chapter context for this discussion."
        }
    }
    
    private fun parseExercisesFromContent(content: String): List<Exercise> {
        try {
            println("ContentLoader: Starting to parse exercises from content")
            val jsonObject = json.parseToJsonElement(content).jsonObject
            println("ContentLoader: JSON object keys: ${jsonObject.keys}")
            
            // Questions are nested under content.questions, not exercises
            val contentObject = jsonObject["content"]?.jsonObject
            println("ContentLoader: Content object found: ${contentObject != null}")
            
            if (contentObject != null) {
                println("ContentLoader: Content object keys: ${contentObject.keys}")
            }
            
            val questionsArray = contentObject?.get("questions")?.jsonArray
            println("ContentLoader: Questions array found: ${questionsArray != null}")
            println("ContentLoader: Questions array size: ${questionsArray?.size ?: 0}")
            
            val exercises = mutableListOf<Exercise>()
            questionsArray?.forEach { questionElement ->
                val questionObj = questionElement.jsonObject
                val question = questionObj["question"]?.jsonPrimitive?.content ?: ""
                val answer = questionObj["answer"]?.jsonPrimitive?.content ?: ""
                
                println("ContentLoader: Found question - Question: '${question.take(50)}...', Answer: '${answer.take(50)}...'")
                
                if (question.isNotBlank()) {
                    exercises.add(Exercise(question = question, answer = answer))
                }
            }
            
            println("ContentLoader: Parsed ${exercises.size} exercises")
            return exercises
            
        } catch (e: Exception) {
            println("ContentLoader: Error parsing exercises: ${e.message}")
            e.printStackTrace()
            return emptyList()
        }
    }
    
    fun loadRevisionQuestionsForChapter(context: Context, chapterId: String): List<RevisionQuestion> {
        try {
            println("ContentLoader: Loading revision questions for chapter: $chapterId")
            val directoryAndFilename = getDirectoryAndFilename(context, chapterId)
            if (directoryAndFilename == null) {
                println("ContentLoader: Could not find file for chapter '$chapterId'")
                return emptyList()
            }
            val (directory, filename) = directoryAndFilename
            println("ContentLoader: Directory: $directory, Filename: $filename")
            
            val content = context.assets.open("$directory/$filename").bufferedReader().use { it.readText() }
            println("ContentLoader: Successfully loaded content, length: ${content.length}")
            
            val jsonObject = json.parseToJsonElement(content).jsonObject
            val contentObject = jsonObject["content"]?.jsonObject
            println("ContentLoader: Content object found: ${contentObject != null}")
            
            val questionsArray = contentObject?.get("questions")?.jsonArray
            println("ContentLoader: Questions array found: ${questionsArray != null}, size: ${questionsArray?.size ?: 0}")
            
            val revisionQuestions = mutableListOf<RevisionQuestion>()
            questionsArray?.forEach { questionElement ->
                val questionObj = questionElement.jsonObject
                val question = questionObj["question"]?.jsonPrimitive?.content ?: ""
                val answer = questionObj["answer"]?.jsonPrimitive?.content ?: ""
                
                println("ContentLoader: Found question: '${question.take(50)}...', answer length: ${answer.length}")
                
                if (question.isNotBlank() && answer.isNotBlank()) {
                    revisionQuestions.add(RevisionQuestion(question = question, answer = answer))
                }
            }
            
            println("ContentLoader: Loaded ${revisionQuestions.size} revision questions for chapter $chapterId")
            return revisionQuestions
            
        } catch (e: Exception) {
            println("ContentLoader: Error loading revision questions for '$chapterId': ${e.message}")
            e.printStackTrace()
            return emptyList()
        }
    }
    
    suspend fun loadRevisionDataForSubject(context: Context, subjectId: String): List<ChapterRevisionData> = withContext(Dispatchers.IO) {
        try {
            println("ContentLoader: Loading revision data for subject: $subjectId")
            val chapters = loadChaptersForSubject(context, subjectId)
            println("ContentLoader: Found ${chapters.size} chapters for subject $subjectId")
            
            val revisionData = mutableListOf<ChapterRevisionData>()
            
            chapters.forEach { chapter ->
                println("ContentLoader: Processing chapter: ${chapter.id} - ${chapter.title}")
                val questions = loadRevisionQuestionsForChapter(context, chapter.id)
                println("ContentLoader: Chapter ${chapter.id} has ${questions.size} questions")
                
                if (questions.isNotEmpty()) {
                    revisionData.add(
                        ChapterRevisionData(
                            chapterId = chapter.id,
                            chapterTitle = chapter.title,
                            questions = questions
                        )
                    )
                    println("ContentLoader: Added chapter ${chapter.title} to revision data")
                } else {
                    println("ContentLoader: Skipping chapter ${chapter.title} - no questions found")
                }
            }
            
            println("ContentLoader: Total revision data chapters: ${revisionData.size}")
            return@withContext revisionData
            
        } catch (e: Exception) {
            println("ContentLoader: Error loading revision data for subject '$subjectId': ${e.message}")
            e.printStackTrace()
            return@withContext emptyList()
        }
    }
    
    fun loadImageFromUri(context: Context, uriString: String): Bitmap? {
        return try {
            val uri = Uri.parse(uriString)
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            println("ContentLoader: Error loading image from URI '$uriString': ${e.message}")
            null
        }
    }
}
