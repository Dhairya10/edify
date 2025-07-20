package com.edify.learning

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EdifyApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
    }
}
