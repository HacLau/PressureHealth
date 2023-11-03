package com.testbird.pressurehealth.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.databinding.LayoutRecordChartItemBinding
import com.testbird.pressurehealth.helper.DateHelper
import com.testbird.pressurehealth.helper.dp2px
import com.testbird.pressurehealth.model.RecordEntity

class ChartAdapter(
    private val context: Context,
    private val list: List<RecordEntity>,
    private val max: Int,
    private val min: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemVH(LayoutRecordChartItemBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemVH) {
            list[position].let {
                holder.binding.time.text = DateHelper.getFormatTimeRecordChartItem(it.time)
                holder.binding.diaSys.text = "${it.dia}-${it.sys}"

                val h = (it.sys - it.dia) / (max - min).toFloat()
                val translationY = (((max + min) / 2.0f - (it.sys + it.dia) / 2.0f) / (max - min).toFloat())
                holder.binding.level.layoutParams.height = if (it.sys <= it.dia) {
                    1
                } else (106 * h).toInt().dp2px(context)
                holder.binding.level.translationY = (translationY * 106).dp2px(context)
                holder.binding.diaSys.translationY = (translationY * 106).dp2px(context)

                holder.binding.time.translationY = (0.61f * 106).dp2px(context)
                var color = R.color.result_0

                when (it.level) {
                    0 -> {
                        color = R.color.result_0
                    }

                    1 -> {
                        color = R.color.result_1
                    }

                    2 -> {
                        color = R.color.result_2
                    }

                    3 -> {
                        color = R.color.result_3
                    }

                    4 -> {
                        color = R.color.result_4
                    }

                    5 -> {
                        color = R.color.result_5
                    }
                }
                ColorStateList.valueOf(context.getColor(color)).let {
                    holder.binding.level.imageTintList = it
                    holder.binding.diaSys.setTextColor(it)
                }


            }


        }
    }

    inner class ItemVH(bind: LayoutRecordChartItemBinding) : RecyclerView.ViewHolder(bind.root) {
        val binding = bind
    }
}