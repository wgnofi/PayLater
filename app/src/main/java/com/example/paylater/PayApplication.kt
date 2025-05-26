package com.example.paylater

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PayApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}