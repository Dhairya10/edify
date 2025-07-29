package com.edify.learning.data.util

import android.content.Context
import com.edify.learning.data.model.Chapter
import com.edify.learning.data.model.Subject
import com.edify.learning.data.model.Exercise
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
        
        // Social Science
        val socialChapters = loadChaptersFromDirectory(context, "social_science")
        if (socialChapters.isNotEmpty()) {
            subjects.add(
                Subject(
                    id = "social_001",
                    name = "Social Science",
                    description = "History and Geography",
                    color = "#003c63",
                    iconRes = "ic_social",
                    totalChapters = socialChapters.size,
                    completedChapters = 0,
                    lastReadChapterId = null
                )
            )
        }
        
        subjects
    }
    
    suspend fun loadChaptersForSubject(context: Context, subjectId: String): List<Chapter> = withContext(Dispatchers.IO) {
        val directory = when (subjectId) {
            "math_001" -> "maths"
            "science_001" -> "science"
            "english_001" -> "english"
            "social_001" -> "social_science"
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
                    
                    // Parse JSON to get subject info and title
                    val jsonObject = json.parseToJsonElement(content).jsonObject
                    val subject = jsonObject["subject"]?.jsonPrimitive?.content ?: ""
                    
                    // Extract chapter title from JSON, fallback to generated title
                    val chapterTitle = jsonObject["title"]?.jsonPrimitive?.content 
                        ?: generateChapterTitle(filename, content)
                    val chapterDescription = extractDescription(content)
                    val estimatedTime = estimateReadingTime(content)
                    
                    println("ContentLoader: Using title from JSON: '$chapterTitle' for file $fileName")
                    
                    val actualSubjectId = subjectId ?: when (subject) {
                        "maths" -> "math_001"
                        "science" -> "science_001"
                        "english" -> "english_001"
                        "social_science" -> "social_001"
                        else -> "unknown_001"
                    }
                    
                    val chapter = Chapter(
                        id = filename,
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
    
    private fun generateChapterTitle(filename: String, content: String): String {
        try {
            val jsonObject = json.parseToJsonElement(content).jsonObject
            val markdownText = jsonObject["content"]?.jsonObject?.get("markdown_text")?.jsonPrimitive?.content
            
            // Look for the first meaningful header in markdown (skip image references)
            markdownText?.let { markdown ->
                val lines = markdown.split("\n")
                for (line in lines) {
                    val trimmed = line.trim()
                    // Look for headers that are not just image references
                    if (trimmed.startsWith("#") && !trimmed.startsWith("![]") && trimmed.length > 2) {
                        val headerText = trimmed.replaceFirst("^#+\\s*".toRegex(), "").trim()
                        if (headerText.isNotBlank() && headerText.length > 3) {
                            return headerText
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("ContentLoader: Error extracting title from $filename: ${e.message}")
        }
        
        // Fallback: create a meaningful title from filename
        val chapterNumber = filename.takeLast(3).toIntOrNull() ?: filename.takeLast(2).toIntOrNull()
        return if (chapterNumber != null) {
            "Chapter $chapterNumber"
        } else {
            filename.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }
    
    private fun extractDescription(content: String): String {
        try {
            val jsonObject = json.parseToJsonElement(content).jsonObject
            val markdownText = jsonObject["content"]?.jsonObject?.get("markdown_text")?.jsonPrimitive?.content
            
            markdownText?.let { markdown ->
                // Find first paragraph after headers
                val lines = markdown.split("\n").map { it.trim() }
                for (line in lines) {
                    if (line.isNotBlank() && 
                        !line.startsWith("#") && 
                        !line.startsWith("![") &&
                        line.length > 20) {
                        return line.take(100) + if (line.length > 100) "..." else ""
                    }
                }
            }
        } catch (e: Exception) {
            // Ignore parsing errors
        }
        
        return "Chapter content"
    }
    
    private fun estimateReadingTime(content: String): Int {
        try {
            val jsonObject = json.parseToJsonElement(content).jsonObject
            val markdownText = jsonObject["content"]?.jsonObject?.get("markdown_text")?.jsonPrimitive?.content
            
            markdownText?.let { markdown ->
                val wordCount = markdown.split("\\s+".toRegex()).size
                // Average reading speed: 200 words per minute
                return (wordCount / 200).coerceAtLeast(1)
            }
        } catch (e: Exception) {
            // Ignore parsing errors
        }
        
        return 30 // Default reading time
    }
    
    fun loadExercisesForChapter(context: Context, chapterId: String): List<Exercise> {
        try {
            println("ContentLoader: Loading exercises for chapterId: '$chapterId'")
            
            // Determine the correct directory based on chapter ID pattern
            val (directory, filename) = getDirectoryAndFilename(chapterId)
            if (directory == "english" && !chapterId.startsWith("jeff") && 
                !chapterId.startsWith("jesc") && !chapterId.startsWith("jemh") && 
                !chapterId.startsWith("jess")) {
                // This means we hit the default fallback case
                return emptyList()
            }
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
    
    private fun getDirectoryAndFilename(chapterId: String): Pair<String, String> {
        val directory = when {
            chapterId.startsWith("jeff") -> "english"
            chapterId.startsWith("jesc") -> "science"
            chapterId.startsWith("jemh") -> "maths"
            chapterId.startsWith("jess") -> "social_science"
            else -> {
                println("ContentLoader: Unknown chapter ID pattern: '$chapterId'")
                "english" // default fallback
            }
        }
        
        val filename = if (chapterId.endsWith(".json")) chapterId else "$chapterId.json"
        return Pair(directory, filename)
    }
    
    suspend fun loadChapterSummaryForChat(context: Context, chapterId: String): String = withContext(Dispatchers.IO) {
        try {
            val (directory, filename) = getDirectoryAndFilename(chapterId)
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
            val markdownText = contentObject?.get("markdown_text")?.jsonPrimitive?.content ?: ""
            val firstParagraph = markdownText.split("\n\n").take(3).joinToString("\n\n")
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
            
            // Exercises are nested under content.exercises, not at root level
            val contentObject = jsonObject["content"]?.jsonObject
            println("ContentLoader: Content object found: ${contentObject != null}")
            
            if (contentObject != null) {
                println("ContentLoader: Content object keys: ${contentObject.keys}")
            }
            
            val exercisesArray = contentObject?.get("exercises")?.jsonArray
            println("ContentLoader: Exercises array found: ${exercisesArray != null}")
            println("ContentLoader: Exercises array size: ${exercisesArray?.size ?: 0}")
            
            val exercises = mutableListOf<Exercise>()
            exercisesArray?.forEach { exerciseElement ->
                val exerciseObj = exerciseElement.jsonObject
                val question = exerciseObj["question"]?.jsonPrimitive?.content ?: ""
                val answer = exerciseObj["answer"]?.jsonPrimitive?.content ?: ""
                
                println("ContentLoader: Found exercise - Question: '${question.take(50)}...', Answer: '${answer.take(50)}...'")
                
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
}
