package com.testbird.pressurehealth.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.testbird.pressurehealth.databinding.LayoutFilterRecordItemBinding

class FilterRecordAdapter(
    val context: Context,
    val list:MutableList<String>
) : BaseAdapter() {
    override fun getCount(): Int = list.size


    override fun getItem(position: Int): Any = list[position]


    override fun getItemId(position: Int): Long = position.toLong()


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = LayoutFilterRecordItemBinding.inflate(LayoutInflater.from(context))
        binding.text1.text = list[position]
        return binding.root
    }

}