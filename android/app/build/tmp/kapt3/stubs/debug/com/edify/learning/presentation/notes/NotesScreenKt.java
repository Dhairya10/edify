package com.edify.learning.presentation.notes;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a0\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a.\u0010\r\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a(\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u001e\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002\u001a\u0010\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\bH\u0002\u00a8\u0006\u001d"}, d2 = {"EmptyNotesState", "", "selectedFilter", "Lcom/edify/learning/presentation/notes/NoteFilter;", "modifier", "Landroidx/compose/ui/Modifier;", "FilterTab", "text", "", "isSelected", "", "onClick", "Lkotlin/Function0;", "FilterTabs", "onFilterChanged", "Lkotlin/Function1;", "NoteCard", "note", "Lcom/edify/learning/data/model/Note;", "onDeleteNote", "NotesScreen", "viewModel", "Lcom/edify/learning/presentation/notes/NotesViewModel;", "onNavigateBack", "formatDate", "timestamp", "", "getDisplayContent", "content", "app_debug"})
public final class NotesScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void NotesScreen(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.notes.NotesViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FilterTabs(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.notes.NoteFilter selectedFilter, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.edify.learning.presentation.notes.NoteFilter, kotlin.Unit> onFilterChanged, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FilterTab(@org.jetbrains.annotations.NotNull()
    java.lang.String text, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void NoteCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.Note note, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteNote, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyNotesState(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.notes.NoteFilter selectedFilter, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.lang.String getDisplayContent(java.lang.String content) {
        return null;
    }
    
    private static final java.lang.String formatDate(long timestamp) {
        return null;
    }
}