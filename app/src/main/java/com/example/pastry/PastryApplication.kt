package com.example.pastry

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PastryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}