package com.testbird.pressurehealth.view.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.adapter.FilterRecordAdapter
import com.testbird.pressurehealth.databinding.LayoutFilterRecordBinding
import com.testbird.pressurehealth.model.FilterRecordType

class FilterRecordPop(
    val context: Context
) : PopupWindow(context) {
    var onItemClick:(String)->Unit = {}
    private var binding: LayoutFilterRecordBinding

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        background.alpha = 0
        animationStyle = R.style.pop_anim_style
        binding = LayoutFilterRecordBinding.inflate(LayoutInflater.from(context))
        contentView = binding.root
        initView()
    }

    private fun initView() {
        binding.listView.adapter = FilterRecordAdapter(context,filterRecordArray)
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            dismiss()
            onItemClick.invoke(filterRecordArray[position])
        }
    }
}

val filterRecordArray: MutableList<String> = mutableListOf(
        FilterRecordType.recent,
        FilterRecordType.week,
        FilterRecordType.seven,
        FilterRecordType.month,
        FilterRecordType.lastMonth,
        FilterRecordType.all,
)