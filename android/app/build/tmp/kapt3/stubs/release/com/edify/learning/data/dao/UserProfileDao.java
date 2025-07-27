package com.edify.learning.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000b\u001a\u0004\u0018\u00010\t2\u0006\u0010\f\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ(\u0010\u0011\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/edify/learning/data/dao/UserProfileDao;", "", "deleteById", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllProfiles", "", "Lcom/edify/learning/data/model/UserProfile;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserProfile", "userId", "insertOrUpdate", "userProfile", "(Lcom/edify/learning/data/model/UserProfile;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertOrUpdateUserProfile", "updatePersonalizationStatus", "hasUnlocked", "", "timestamp", "", "(Ljava/lang/String;ZJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
@androidx.room.Dao()
public abstract interface UserProfileDao {
    
    @androidx.room.Query(value = "SELECT * FROM user_profile ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllProfiles(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.edify.learning.data.model.UserProfile>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM user_profile WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.edify.learning.data.model.UserProfile> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrUpdateUserProfile(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.UserProfile userProfile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrUpdate(@org.jetbrains.annotations.NotNull()
    com.edify.learning.data.model.UserProfile userProfile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM user_profile WHERE userId = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE user_profile SET hasUnlockedPersonalizedQuests = :hasUnlocked, updatedAt = :timestamp WHERE userId = :userId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePersonalizationStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, boolean hasUnlocked, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}