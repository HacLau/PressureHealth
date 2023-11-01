package com.testbird.pressurehealth.helper

import java.util.Calendar

object DateHelper {
    fun getFormatTime(mill: Long):String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = mill
        return "${calendar.get(Calendar.MONTH).month()} ${calendar.get(Calendar.DATE)},${calendar.get(Calendar.YEAR)}"
    }
}