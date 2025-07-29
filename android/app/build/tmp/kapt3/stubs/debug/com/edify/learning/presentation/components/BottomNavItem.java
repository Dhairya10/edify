package com.edify.learning.presentation.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u000f\u0010\u0011\u0012B\'\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f\u0082\u0001\u0004\u0013\u0014\u0015\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/edify/learning/presentation/components/BottomNavItem;", "", "route", "", "title", "selectedIconResId", "", "unselectedIconResId", "(Ljava/lang/String;Ljava/lang/String;II)V", "getRoute", "()Ljava/lang/String;", "getSelectedIconResId", "()I", "getTitle", "getUnselectedIconResId", "Developer", "Library", "Notes", "Quest", "Lcom/edify/learning/presentation/components/BottomNavItem$Developer;", "Lcom/edify/learning/presentation/components/BottomNavItem$Library;", "Lcom/edify/learning/presentation/components/BottomNavItem$Notes;", "Lcom/edify/learning/presentation/components/BottomNavItem$Quest;", "app_debug"})
public abstract class BottomNavItem {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    private final int selectedIconResId = 0;
    private final int unselectedIconResId = 0;
    
    private BottomNavItem(java.lang.String route, java.lang.String title, int selectedIconResId, int unselectedIconResId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    public final int getSelectedIconResId() {
        return 0;
    }
    
    public final int getUnselectedIconResId() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/edify/learning/presentation/components/BottomNavItem$Developer;", "Lcom/edify/learning/presentation/components/BottomNavItem;", "()V", "app_debug"})
    public static final class Developer extends com.edify.learning.presentation.components.BottomNavItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.edify.learning.presentation.components.BottomNavItem.Developer INSTANCE = null;
        
        private Developer() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/edify/learning/presentation/components/BottomNavItem$Library;", "Lcom/edify/learning/presentation/components/BottomNavItem;", "()V", "app_debug"})
    public static final class Library extends com.edify.learning.presentation.components.BottomNavItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.edify.learning.presentation.components.BottomNavItem.Library INSTANCE = null;
        
        private Library() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/edify/learning/presentation/components/BottomNavItem$Notes;", "Lcom/edify/learning/presentation/components/BottomNavItem;", "()V", "app_debug"})
    public static final class Notes extends com.edify.learning.presentation.components.BottomNavItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.edify.learning.presentation.components.BottomNavItem.Notes INSTANCE = null;
        
        private Notes() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/edify/learning/presentation/components/BottomNavItem$Quest;", "Lcom/edify/learning/presentation/components/BottomNavItem;", "()V", "app_debug"})
    public static final class Quest extends com.edify.learning.presentation.components.BottomNavItem {
        @org.jetbrains.annotations.NotNull()
        public static final com.edify.learning.presentation.components.BottomNavItem.Quest INSTANCE = null;
        
        private Quest() {
        }
    }
}