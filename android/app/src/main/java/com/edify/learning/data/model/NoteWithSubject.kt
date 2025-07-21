package com.edify.learning.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class NoteWithSubject(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "chapterId",
        entityColumn = "id",
        entity = Chapter::class
    )
    val chapter: Chapter,
    @Relation(
        parentColumn = "subjectId",
        entityColumn = "id",
        entity = Subject::class
    )
    val subject: Subject
)

// Simplified version for easier use in UI
data class NoteWithSubjectInfo(
    val note: Note,
    val subjectId: String,
    val subjectName: String,
    val subjectColor: String,
    val subjectIcon: String,
    val chapterTitle: String
)
