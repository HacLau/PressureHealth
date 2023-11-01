package com.testbird.pressurehealth

import android.app.Application
import com.testbird.pressurehealth.helper.AppLifecycleHelper

lateinit var appContext:App
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
        registerActivityLifecycleCallbacks(AppLifecycleHelper())
    }
}