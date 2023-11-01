package com.testbird.pressurehealth.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testbird.pressurehealth.databinding.LayoutInfoItemBinding
import com.testbird.pressurehealth.databinding.LayoutRecordItemBinding
import com.testbird.pressurehealth.databinding.LayoutRecordItemChartBinding
import com.testbird.pressurehealth.databinding.LayoutRecordItemFirstBinding
import com.testbird.pressurehealth.databinding.LayoutSettingAlarmBinding
import com.testbird.pressurehealth.databinding.LayoutSettingItemBinding
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.model.AppMainEntity
import com.testbird.pressurehealth.model.ItemType
import com.testbird.pressurehealth.model.infoImageList

class DataAdapter(
    private val context: Context,
    private val list: MutableList<AppMainEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ItemType.mainItem.ordinal->{
                InformationVH(LayoutInfoItemBinding.inflate(LayoutInflater.from(context)))
            }
            ItemType.recordTop.ordinal->{
                RecordTopVH(LayoutRecordItemFirstBinding.inflate(LayoutInflater.from(context)))
            }
            ItemType.recordChart.ordinal->{
                RecordChartVH(LayoutRecordItemChartBinding.inflate(LayoutInflater.from(context)))
            }
            ItemType.recordItem.ordinal->{
                RecordItemVH(LayoutRecordItemBinding.inflate(LayoutInflater.from(context)))
            }
            ItemType.infoItem.ordinal->{
                InformationVH(LayoutInfoItemBinding.inflate(LayoutInflater.from(context)))
            }
            ItemType.settingTop.ordinal->{
                MineTopVH(LayoutSettingAlarmBinding.inflate(LayoutInflater.from(context)))
            }
            ItemType.settingItem.ordinal->{
                MineItemVH(LayoutSettingItemBinding.inflate(LayoutInflater.from(context)))
            }
            else -> {
                InformationVH(LayoutInfoItemBinding.inflate(LayoutInflater.from(context)))
            }
        }
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ItemType.mainItem.ordinal->{
                if (holder is InformationVH){
                    list[position].info.let {entity->
                        holder.binding.let {
                            it.title.text = entity?.title
                            it.content.text = entity?.content
                            it.image.setImageResource(infoImageList.random())
                        }
                    }
                }
            }
            ItemType.recordTop.ordinal->{

            }
            ItemType.recordChart.ordinal->{

            }
            ItemType.recordItem.ordinal->{

            }
            ItemType.infoItem.ordinal->{

            }
            ItemType.settingTop.ordinal->{
                if (holder is MineTopVH){
                    list[position].setting.let {entity->
                        holder.binding.let {
                            it.title.text = entity?.title
                        }
                    }
                }
            }
            ItemType.settingItem.ordinal->{
                if (holder is MineItemVH){
                    list[position].setting.let {entity->
                        holder.binding.let {
                            "title = ${entity?.title} content = ${entity?.content}".log()
                            it.title.text = entity?.title
                            if (entity?.content.isNullOrBlank())
                                it.next.visibility = View.VISIBLE
                            else
                                it.next.visibility = View.GONE
                            it.content.text = entity?.content
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

    inner class RecordTopVH(bind: LayoutRecordItemFirstBinding):RecyclerView.ViewHolder(bind.root){
        val binding = bind
    }
    inner class RecordChartVH(bind: LayoutRecordItemChartBinding):RecyclerView.ViewHolder(bind.root){
        val binding = bind
    }
    inner class RecordItemVH(bind: LayoutRecordItemBinding):RecyclerView.ViewHolder(bind.root){
        val binding = bind
    }
    inner class InformationVH(bind: LayoutInfoItemBinding):RecyclerView.ViewHolder(bind.root){
        val binding = bind
    }
    inner class MineTopVH(bind: LayoutSettingAlarmBinding):RecyclerView.ViewHolder(bind.root){
        val binding = bind
    }
    inner class MineItemVH(bind: LayoutSettingItemBinding):RecyclerView.ViewHolder(bind.root){
        val binding = bind
    }
}