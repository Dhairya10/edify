package com.edify.learning.presentation.developer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\u001aH\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052&\u0010\u0006\u001a\"\u0012\u0004\u0012\u00020\u0003\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n0\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001aR\u0010\u000b\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\f\u001a\u0004\u0018\u00010\r2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052&\u0010\u0006\u001a\"\u0012\u0004\u0012\u00020\u0003\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n0\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a,\u0010\u000e\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001ab\u0010\u0011\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u00132\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00072\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00132\u0006\u0010\u0015\u001a\u00020\tH\u0002\u00a8\u0006\u0016"}, d2 = {"AddRecordDialog", "", "table", "Lcom/edify/learning/presentation/developer/DatabaseTable;", "onDismiss", "Lkotlin/Function0;", "onSave", "Lkotlin/Function2;", "", "", "", "EditRecordDialog", "record", "Lcom/edify/learning/presentation/developer/DatabaseRecord;", "RecordCard", "onEdit", "onDelete", "TableDataDialog", "data", "", "getFieldsForTable", "entityClass", "app_release"})
public final class DeveloperModeDialogsKt {
    
    @androidx.compose.runtime.Composable()
    public static final void TableDataDialog(@org.jetbrains.annotations.Nullable()
    com.edify.learning.presentation.developer.DatabaseTable table, @org.jetbrains.annotations.NotNull()
    java.util.List<com.edify.learning.presentation.developer.DatabaseRecord> data, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.edify.learning.presentation.developer.DatabaseTable, ? super com.edify.learning.presentation.developer.DatabaseRecord, kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.edify.learning.presentation.developer.DatabaseTable, ? super com.edify.learning.presentation.developer.DatabaseRecord, kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RecordCard(@org.jetbrains.annotations.NotNull()
    com.edify.learning.presentation.developer.DatabaseRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AddRecordDialog(@org.jetbrains.annotations.Nullable()
    com.edify.learning.presentation.developer.DatabaseTable table, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.edify.learning.presentation.developer.DatabaseTable, ? super java.util.Map<java.lang.String, ? extends java.lang.Object>, kotlin.Unit> onSave) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EditRecordDialog(@org.jetbrains.annotations.Nullable()
    com.edify.learning.presentation.developer.DatabaseTable table, @org.jetbrains.annotations.Nullable()
    com.edify.learning.presentation.developer.DatabaseRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.edify.learning.presentation.developer.DatabaseTable, ? super java.util.Map<java.lang.String, ? extends java.lang.Object>, kotlin.Unit> onSave) {
    }
    
    private static final java.util.List<java.lang.String> getFieldsForTable(java.lang.String entityClass) {
        return null;
    }
}