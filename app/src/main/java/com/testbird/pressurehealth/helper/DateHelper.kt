package com.testbird.pressurehealth.helper

import java.util.Calendar

object DateHelper {
    fun getFormatTimeMain(mill: Long): String {
        return Calendar.getInstance().apply {
            timeInMillis = mill
        }.let {
            "${it.get(Calendar.MONTH).month()} ${it.get(Calendar.DATE).two()},${it.get(Calendar.YEAR)}"
        }
    }

    fun getFormatTimeRecordItem(mill: Long): String {
        if (mill == 0L) {
            return ""
        }
        return Calendar.getInstance().apply {
            timeInMillis = mill
        }.let {
            "${it.get(Calendar.YEAR)}-${(it.get(Calendar.MONTH) + 1).two()}-${it.get(Calendar.DATE).two()} ${
                it.get(Calendar.HOUR_OF_DAY).two()
            }:${it.get(Calendar.MINUTE).two()}"
        }
    }

    fun getFormatTimeRecordChart(mill: Long): String {
        if (mill == 0L) {
            return ""
        }
        return Calendar.getInstance().apply {
            timeInMillis = mill
        }.let {
            "${it.get(Calendar.YEAR)}-${(it.get(Calendar.MONTH) + 1).two()}-${it.get(Calendar.DATE).two()}"
        }
    }

    fun getFormatTimeRecordChartItem(mill: Long): String {
        if (mill == 0L) {
            return ""
        }
        return Calendar.getInstance().apply {
            timeInMillis = mill
        }.let {
            "${(it.get(Calendar.MONTH) + 1)}.${it.get(Calendar.DATE)},${it.get(Calendar.YEAR)}"
        }
    }

    fun getCurrentYearMonthHaveDay(year: Int, month: Int): Int {
        return Calendar.getInstance().apply {
            set(year, month, 1)
            add(Calendar.DATE, -1)
        }.get(Calendar.DATE)
    }

    fun getCurrentDateArrayByMill(time: Long): IntArray {
        return Calendar.getInstance().apply {
            timeInMillis = time
        }.let {
            intArrayOf(
                it.get(Calendar.YEAR),
                it.get(Calendar.MONTH) + 1,
                it.get(Calendar.DATE),
                it.get(Calendar.HOUR_OF_DAY),
                it.get(Calendar.MINUTE)
            )
        }
    }

    fun getCurrentDateMillByArray(array: IntArray): Long {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, array[0])
            set(Calendar.MONTH, array[1] - 1)
            set(Calendar.DATE, array[2])
            set(Calendar.HOUR_OF_DAY, array[3])
            set(Calendar.MINUTE, array[4])
        }.timeInMillis

    }

    fun getTodayMills():Long{
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }

    fun getLast7DaysMills():Long{
        return Calendar.getInstance().apply {
            set(Calendar.DATE, get(Calendar.DATE) - 7)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }

    fun getDayOfWeek(dayOfWeek: Int,weekOffset:Int,firstDayOfWeek: Int = Calendar.SUNDAY):Long{

        if (dayOfWeek > Calendar.SATURDAY || dayOfWeek < Calendar.SUNDAY){
            return 0L
        }
        if (firstDayOfWeek > Calendar.SATURDAY || firstDayOfWeek < Calendar.SUNDAY){
            return 0L
        }
        return Calendar.getInstance().apply {
            this.firstDayOfWeek = firstDayOfWeek
            add(Calendar.WEEK_OF_MONTH,weekOffset)
            set(Calendar.DAY_OF_WEEK,dayOfWeek)
            set(Calendar.HOUR,0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
            set(Calendar.MILLISECOND,0)
        }.timeInMillis

    }

    fun getThisMonthStart():Long{
        return Calendar.getInstance().apply {
            set(Calendar.DATE, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }

    fun getLastMonthStart():Long{
        return Calendar.getInstance().apply {
            set(Calendar.MONTH,get(Calendar.MONTH) - 1)
            set(Calendar.DATE, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
    }
}