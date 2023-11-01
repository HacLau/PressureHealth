package com.testbird.pressurehealth.helper

object ContactHelper {
    var isLauncherStart: Boolean by SharedPreferencesHelper("isLauncherStart", false)
    var isLauncherStep: Boolean by SharedPreferencesHelper("isLauncherStep", false)
    var activityInBackTime: Long by SharedPreferencesHelper("activityInBackTime", 0L)
}