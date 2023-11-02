package com.testbird.pressurehealth.view.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.databinding.LayoutSetTimeBinding
import com.testbird.pressurehealth.helper.DateHelper
import com.testbird.pressurehealth.helper.toast
import com.testbird.pressurehealth.helper.two

class TimeSettingPop(
    val context: Context,
    val time: Long,
    val yearList: MutableList<String>,
    val monthList: MutableList<String>,
    val hourList: MutableList<String>,
    val minuteList: MutableList<String>
) : PopupWindow(context) {
    var cancel: () -> Unit = {}
    var confirm: (Long) -> Unit = {}
    private lateinit var binding: LayoutSetTimeBinding
    private lateinit var dateArray: IntArray

    init {
        height = ViewGroup.LayoutParams.MATCH_PARENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isFocusable = true
        background.alpha = 160
        animationStyle = R.style.pop_anim_style
        initView()
        initData()
    }

    private fun initData() {
        dateArray = DateHelper.getCurrentDateArrayByMill(time)
        binding.npYear.setData(yearList, yearList.indexOf(dateArray[0].two()))
        binding.npMonth.setData(monthList, monthList.indexOf(dateArray[1].two()))
        binding.npHour.setData(hourList, hourList.indexOf(dateArray[3].two()))
        binding.npMinute.setData(minuteList, minuteList.indexOf(dateArray[4].two()))
    }

    private fun initView() {
        binding = LayoutSetTimeBinding.inflate(LayoutInflater.from(context))
        binding.popCancel.setOnClickListener {
            dismiss()
            cancel.invoke()
        }
        binding.popConfirm.setOnClickListener {
            DateHelper.getCurrentDateMillByArray(dateArray).let {
                if (it > System.currentTimeMillis()) {
                    "You choice time is exceed current time,please choice again".toast(context)
                    return@setOnClickListener
                } else {
                    dismiss()
                    confirm.invoke(it)
                }
            }
        }

        binding.npYear.onSelect = {
            dateArray[0] = it.toInt()
            setDay(getDayPickerDataList(dateArray[0], dateArray[1]))
        }
        binding.npMonth.onSelect = {
            dateArray[1] = it.toInt()
            setDay(getDayPickerDataList(dateArray[0], dateArray[1]))
        }
        binding.npDay.onSelect = {
            dateArray[2] = it.toInt()
        }
        binding.npHour.onSelect = {
            dateArray[3] = it.toInt()
        }
        binding.npMinute.onSelect = {
            dateArray[4] = it.toInt()
        }
    }

    private fun getDayPickerDataList(year: Int, month: Int): MutableList<String> {
        return mutableListOf<String>().apply {
            (1..DateHelper.getCurrentYearMonthHaveDay(year, month)).forEach {
                add(it.two())
            }
        }
    }

    private fun setDay(list: MutableList<String>) {
        binding.npDay.setData(list, if (list.size > dateArray[2]) list.size - 1 else list.indexOf(dateArray[2].two()))
    }

}