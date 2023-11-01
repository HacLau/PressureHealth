package com.testbird.pressurehealth.view

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.adapter.DataAdapter
import com.testbird.pressurehealth.adapter.ItemDecoration
import com.testbird.pressurehealth.adapter.MainAdapter
import com.testbird.pressurehealth.base.BaseActivity
import com.testbird.pressurehealth.databinding.ActivityMainBinding
import com.testbird.pressurehealth.databinding.LayoutInfoBinding
import com.testbird.pressurehealth.databinding.LayoutMainBinding
import com.testbird.pressurehealth.databinding.LayoutRecordBinding
import com.testbird.pressurehealth.databinding.LayoutSettingBinding
import com.testbird.pressurehealth.helper.DateHelper
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.model.AppMainEntity
import com.testbird.pressurehealth.model.ContentType
import com.testbird.pressurehealth.model.ItemType
import com.testbird.pressurehealth.viewmodel.ActivityModel

class MainActivity : BaseActivity<ActivityMainBinding, ActivityModel>() {
    override fun initView() {
        binding.rg.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rb_home -> binding.mainVp.currentItem = 0
                R.id.rb_record -> binding.mainVp.currentItem = 1
                R.id.rb_info -> binding.mainVp.currentItem = 2
                R.id.rb_mine -> binding.mainVp.currentItem = 3
            }
        }
        binding.mainVp.adapter = MainAdapter(
            listOf(
                getHomeView(),
                getRecordView(),
                getInfoView(),
                getSettingView()
            )
        )
        binding.mainVp.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.rbHome.isChecked = true
                        binding.mainTitle.text = DateHelper.getFormatTime(System.currentTimeMillis())
                    }

                    1 -> {
                        binding.rbRecord.isChecked = true
                        binding.mainTitle.text = getString(R.string.title_record)
                    }

                    2 -> {
                        binding.rbInfo.isChecked = true
                        binding.mainTitle.text = getString(R.string.title_info)
                    }

                    3 -> {
                        binding.rbMine.isChecked = true
                        binding.mainTitle.text = getString(R.string.title_setting)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun getHomeView(): View {
        val bind = LayoutMainBinding.inflate(LayoutInflater.from(this))
        bind.btnAdd.setOnClickListener {
            startRecordNewActivity(ContentType.new)
        }

        bind.mainRv.addItemDecoration(ItemDecoration(6))
        bind.mainRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bind.mainRv.adapter = DataAdapter(this, mutableListOf<AppMainEntity>().apply {
            viewModel.infoList.subList(
                0, if (viewModel.infoList.size > 5) 5 else viewModel.infoList.size
            ).forEach {
                add(AppMainEntity(itemType = ItemType.mainItem.ordinal, info = it))
            }
        })
        return bind.root
    }

    private fun getRecordView(): View {
        val bind = LayoutRecordBinding.inflate(LayoutInflater.from(this))
        bind.recordNew.setOnClickListener {
            startRecordNewActivity(ContentType.new)
        }
        bind.recordMore.setOnClickListener {
            startRecordNewActivity(ContentType.more)
        }

        bind.recordRv.addItemDecoration(ItemDecoration(6))
        bind.recordRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bind.recordRv.adapter = DataAdapter(this, mutableListOf<AppMainEntity>())
        return bind.root
    }

    private fun getInfoView(): View {
        val bind = LayoutInfoBinding.inflate(LayoutInflater.from(this))
        bind.infoRv.addItemDecoration(ItemDecoration(6))
        bind.infoRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bind.infoRv.adapter = DataAdapter(this, mutableListOf<AppMainEntity>().apply {
            viewModel.infoList.forEach {
                add(AppMainEntity(itemType = ItemType.mainItem.ordinal, info = it))
            }
        })
        return bind.root
    }

    private fun getSettingView(): View {
        val bind = LayoutSettingBinding.inflate(LayoutInflater.from(this))
        bind.settingRv.addItemDecoration(ItemDecoration(6))
        bind.settingRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bind.settingRv.adapter = DataAdapter(this, mutableListOf<AppMainEntity>().apply {
            "settingList.size = ${viewModel.settingList.size}".log()
            viewModel.settingList.forEach {
                add(AppMainEntity(itemType = if (this.size == 0) ItemType.settingTop.ordinal else ItemType.settingItem.ordinal, setting = it))
            }
        })
        return bind.root
    }

    override fun initViewModel(): ActivityModel = ViewModelProvider(this)[ActivityModel::class.java]

    override fun initViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}