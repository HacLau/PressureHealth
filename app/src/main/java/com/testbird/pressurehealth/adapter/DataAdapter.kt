package com.testbird.pressurehealth.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.databinding.LayoutInfoItemBinding
import com.testbird.pressurehealth.databinding.LayoutRecordItemBinding
import com.testbird.pressurehealth.databinding.LayoutRecordItemChartBinding
import com.testbird.pressurehealth.databinding.LayoutRecordItemFirstBinding
import com.testbird.pressurehealth.databinding.LayoutSettingAlarmBinding
import com.testbird.pressurehealth.databinding.LayoutSettingItemBinding
import com.testbird.pressurehealth.helper.DateHelper
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.model.AppMainEntity
import com.testbird.pressurehealth.model.InfoEntity
import com.testbird.pressurehealth.model.ItemType
import com.testbird.pressurehealth.model.RecordEntity
import com.testbird.pressurehealth.model.SettingEntity
import com.testbird.pressurehealth.model.infoImageList

class DataAdapter(
    private val context: Context,
    private var list: MutableList<AppMainEntity>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    public var onInfoItemClick: (InfoEntity?) -> Unit = {}
    public var onRecordItemClick: (RecordEntity?) -> Unit = {}
    public var onSettingItemClick: (SettingEntity?) -> Unit = {}
    @SuppressLint("NotifyDataSetChanged")
    fun setList(data:MutableList<AppMainEntity>){
        list = data
        this.notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.mainItem.type -> {
                InformationVH(LayoutInfoItemBinding.inflate(LayoutInflater.from(context)))
            }

            ItemType.recordTop.type -> {
                RecordTopVH(LayoutRecordItemFirstBinding.inflate(LayoutInflater.from(context)))
            }

            ItemType.recordChart.type -> {
                RecordChartVH(LayoutRecordItemChartBinding.inflate(LayoutInflater.from(context)))
            }

            ItemType.recordItem.type -> {
                RecordItemVH(LayoutRecordItemBinding.inflate(LayoutInflater.from(context)))
            }

            ItemType.infoItem.type -> {
                InformationVH(LayoutInfoItemBinding.inflate(LayoutInflater.from(context)))
            }

            ItemType.settingTop.type -> {
                MineTopVH(LayoutSettingAlarmBinding.inflate(LayoutInflater.from(context)))
            }

            ItemType.settingItem.type -> {
                MineItemVH(LayoutSettingItemBinding.inflate(LayoutInflater.from(context)))
            }

            else -> {
                InformationVH(LayoutInfoItemBinding.inflate(LayoutInflater.from(context)))
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.mainItem.type -> {
                if (holder is InformationVH) {
                    list[position].info.let { entity ->
                        holder.binding.let {
                            it.title.text = entity?.title
                            it.content.text = entity?.content
                            it.image.setImageResource(entity?.image?:R.mipmap.ic_degree_0)
                        }
                        holder.binding.root.setOnClickListener {
                            onInfoItemClick.invoke(entity)
                        }
                    }

                }
            }

            ItemType.recordTop.type -> {
                if (holder is RecordTopVH) {
                    list[position].recordTop.let { entity ->
                        holder.binding.let {
                            it.sysNumber.text = "${entity?.sys}"
                            it.diasNumber.text = "${entity?.dia}"
                        }
                    }
                }
            }

            ItemType.recordChart.type -> {
                if (holder is RecordChartVH) {
                    list[position].recordChart.let { chartList ->
                        var maxNumber: Int = chartList[0].sys
                        var minNumber: Int = chartList[0].dia
                        chartList.forEach {
                            if (maxNumber - it.sys < 0) {
                                maxNumber = it.sys
                            }
                            if (it.dia - minNumber < 0) {
                                minNumber = it.dia
                            }
                        }
                        holder.binding.let {
                            it.numMax.text = "$maxNumber"
                            it.numTop.text = "${minNumber + (maxNumber - minNumber) / 4 * 3}"
                            it.numCenter.text = "${minNumber + (maxNumber - minNumber) / 4 * 2}"
                            it.numBottom.text = "${minNumber + (maxNumber - minNumber) / 4 * 1}"
                            it.numMin.text = "$minNumber"
                        }
                    }
                }
            }

            ItemType.recordItem.type -> {
                if (holder is RecordItemVH) {
                    list[position].record.let { entity ->
                        holder.binding.let {
                            it.title.text = when (entity?.level) {
                                0 -> context.getString(R.string.title_h0)
                                1 -> context.getString(R.string.title_h1)
                                2 -> context.getString(R.string.title_h2)
                                3 -> context.getString(R.string.title_h3)
                                4 -> context.getString(R.string.title_h4)
                                5 -> context.getString(R.string.title_h5)
                                else -> context.getString(R.string.title_h0)
                            }
                            it.sysNumber.text = "${entity?.sys}"
                            it.diasNumber.text = "${entity?.dia}"
                            it.time.text = entity?.time?.let { time -> DateHelper.getFormatTimeRecordItem(time) }
                            it.levelImage.setImageResource(
                                when (entity?.level) {
                                    0 -> R.mipmap.ic_degree_0
                                    1 -> R.mipmap.ic_degree_1
                                    2 -> R.mipmap.ic_degree_2
                                    3 -> R.mipmap.ic_degree_3
                                    4 -> R.mipmap.ic_degree_4
                                    5 -> R.mipmap.ic_degree_5
                                    else -> R.mipmap.ic_degree_0
                                }
                            )
                        }
                        holder.binding.root.setOnClickListener {
                            onRecordItemClick.invoke(entity)
                        }
                    }

                }
            }

            ItemType.infoItem.type -> {
                if (holder is InformationVH) {
                    list[position].info.let { entity ->
                        holder.binding.let {
                            it.title.text = entity?.title
                            it.content.text = entity?.content
                            it.image.setImageResource(infoImageList.random())
                        }
                        holder.binding.root.setOnClickListener {
                            onInfoItemClick.invoke(entity)
                        }
                    }

                }
            }

            ItemType.settingTop.type -> {
                if (holder is MineTopVH) {
                    list[position].setting.let { entity ->
                        holder.binding.let {
                            it.title.text = entity?.title
                        }
                        holder.binding.root.setOnClickListener {
                            onSettingItemClick.invoke(entity)
                        }
                    }

                }
            }

            ItemType.settingItem.type -> {
                if (holder is MineItemVH) {
                    list[position].setting.let { entity ->
                        holder.binding.let {
                            "title = ${entity?.title} content = ${entity?.content}".log()
                            it.title.text = entity?.title
                            if (entity?.content.isNullOrBlank())
                                it.next.visibility = View.VISIBLE
                            else
                                it.next.visibility = View.GONE
                            it.content.text = entity?.content
                        }
                        holder.binding.root.setOnClickListener {
                            onSettingItemClick.invoke(entity)
                        }
                    }

                }
            }

            else -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].itemType
    }

    inner class RecordTopVH(bind: LayoutRecordItemFirstBinding) : RecyclerView.ViewHolder(bind.root) {
        val binding = bind
    }

    inner class RecordChartVH(bind: LayoutRecordItemChartBinding) : RecyclerView.ViewHolder(bind.root) {
        val binding = bind
    }

    inner class RecordItemVH(bind: LayoutRecordItemBinding) : RecyclerView.ViewHolder(bind.root) {
        val binding = bind
    }

    inner class InformationVH(bind: LayoutInfoItemBinding) : RecyclerView.ViewHolder(bind.root) {
        val binding = bind
    }

    inner class MineTopVH(bind: LayoutSettingAlarmBinding) : RecyclerView.ViewHolder(bind.root) {
        val binding = bind
    }

    inner class MineItemVH(bind: LayoutSettingItemBinding) : RecyclerView.ViewHolder(bind.root) {
        val binding = bind
    }
}