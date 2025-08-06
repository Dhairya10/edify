package com.edify.learning

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.edify.learning.data.worker.DailyQuestWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class EdifyApplication : Application(), Configuration.Provider {
    
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    
    override fun onCreate() {
        super.onCreate()
        scheduleDailyQuestGeneration()
    }
    
    private fun scheduleDailyQuestGeneration() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true) // Only run when battery is not low
            .build()

        val dailyWork = PeriodicWorkRequestBuilder<DailyQuestWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .setInitialDelay(calculateDelayUntil6PM(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                DailyQuestWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, // Don't duplicate if already scheduled
                dailyWork
            )
        
        println("EdifyApplication: Daily quest generation scheduled for 6 PM daily")
    }
    
    private fun calculateDelayUntil6PM(): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 18) // 6 PM
            set(Calendar.MINUTE, 0) // 00 minutes
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            
            // If it's already past 6 PM today, schedule for tomorrow
            if (before(now)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        
        val delay = target.timeInMillis - now.timeInMillis
        println("EdifyApplication: Next quest generation in ${delay / (1000 * 60 * 60)} hours")
        return delay
    }
}
