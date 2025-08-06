package com.edify.learning.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.edify.learning.data.service.QuestGenerationService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyQuestWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val questGenerationService: QuestGenerationService
) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "daily_quest_generation"
        private const val TAG = "DailyQuestWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            println("$TAG: Starting daily quest generation at ${System.currentTimeMillis()}")
            
            // Use the exact same quest generation logic that was previously triggered reactively
            questGenerationService.checkAndGenerateQuests()
            
            println("$TAG: Daily quest generation completed successfully")
            Result.success()
        } catch (e: Exception) {
            println("$TAG: Daily quest generation failed: ${e.message}")
            e.printStackTrace()
            
            // WorkManager will automatically retry failed jobs
            Result.failure()
        }
    }
}