package com.edify.learning.data.model

data class RevisionQuestion(
    val question: String,
    val answer: String
)

data class ChapterRevisionData(
    val chapterId: String,
    val chapterTitle: String,
    val questions: List<RevisionQuestion>
)