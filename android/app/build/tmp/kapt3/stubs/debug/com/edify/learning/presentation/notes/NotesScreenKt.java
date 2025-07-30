package com.edify.learning.presentation.notes;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a,\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a2\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001a.\u0010\u000e\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0018\u0010\u0010\u001a\u00020\u00012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a(\u0010\u0011\u001a\u00020\u00012\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u000e\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0003\u001a\u000e\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0003\u00a8\u0006\u001a"}, d2 = {"EmptySubjectNotesState", "", "selectedFilter", "", "onNavigateToLibrary", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "ImprovedNoteCard", "noteWithSubject", "Lcom/edify/learning/presentation/notes/NoteWithSubjectInfo;", "onDeleteNote", "onNoteClick", "Lkotlin/Function1;", "NoteDetailDialog", "onDismiss", "NotesScreen", "SubjectFilterChip", "subject", "Lcom/edify/learning/data/model/Subject;", "isSelected", "", "onClick", "getFullContent", "content", "getImprovedDisplayContent", "app_debug"})
public final class NotesScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void NotesScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLibrary) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SubjectFilterChip(@org.jetbrains.annotations.Nullable()
    com.edify.learning.data.model.Subject subject, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ImprovedNoteCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.notes.NoteWithSubjectInfo noteWithSubject, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteNote, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.edify.learning.presentation.notes.NoteWithSubjectInfo, kotlin.Unit> onNoteClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptySubjectNotesState(@org.jetbrains.annotations.Nullable()
    java.lang.String selectedFilter, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLibrary, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void NoteDetailDialog(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.notes.NoteWithSubjectInfo noteWithSubject, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteNote) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getFullContent(@org.jetbrains.annotations.NotNull()
    java.lang.String content) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getImprovedDisplayContent(@org.jetbrains.annotations.NotNull()
    java.lang.String content) {
        return null;
    }
}