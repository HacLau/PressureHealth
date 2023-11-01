package com.testbird.pressurehealth.helper

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.testbird.pressurehealth.appContext
import com.testbird.pressurehealth.view.GuideActivity

class AppLifecycleHelper : Application.ActivityLifecycleCallbacks {
    private var runningActivityNumber = 0
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        (runningActivityNumber == 0
                && System.currentTimeMillis() - ContactHelper.activityInBackTime > 5000
                && (activity is GuideActivity).not()
                ).yes {
                appContext.startActivity(Intent(appContext, GuideActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        runningActivityNumber++

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        runningActivityNumber--
        (runningActivityNumber == 0).yes {
            ContactHelper.activityInBackTime = System.currentTimeMillis()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}