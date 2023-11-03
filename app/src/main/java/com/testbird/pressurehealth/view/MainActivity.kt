package com.testbird.pressurehealth.view

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.testbird.pressurehealth.model.FilterRecordType
import com.testbird.pressurehealth.model.ItemType
import com.testbird.pressurehealth.model.RecordEntity
import com.testbird.pressurehealth.model.RecordEntityTop
import com.testbird.pressurehealth.view.ui.FilterRecordPop
import com.testbird.pressurehealth.viewmodel.ActivityModel
import java.util.Calendar

class MainActivity : BaseActivity<ActivityMainBinding, ActivityModel>() {
    private lateinit var adapter: DataAdapter
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
        binding.mainTitle.text = DateHelper.getFormatTimeMain(System.currentTimeMillis())
        binding.mainVp.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                binding.llFilter.visibility = View.GONE
                when (position) {
                    0 -> {
                        binding.rbHome.isChecked = true
                        binding.mainTitle.text = DateHelper.getFormatTimeMain(System.currentTimeMillis())
                    }

                    1 -> {
                        binding.rbRecord.isChecked = true
                        binding.mainTitle.text = getString(R.string.title_record)
                        binding.llFilter.visibility = View.VISIBLE
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
        binding.llFilter.setOnClickListener {
            FilterRecordPop(this).apply {
                onItemClick = {
                    binding.recordFilter.text = it
                    viewModel.filterRecordType = it
                    getRecordList()
                }
            }.showAsDropDown(binding.llFilter)
        }
    }

    private fun getHomeView(): View {
        val bind = LayoutMainBinding.inflate(LayoutInflater.from(this))
        bind.btnAdd.setOnClickListener {
            startContentActivity(ContentType.new, title = getString(R.string.title_record_new)) {
                binding.mainVp.currentItem = 1
            }
        }

        bind.mainRv.addItemDecoration(ItemDecoration(12))
        bind.mainRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bind.mainRv.adapter = DataAdapter(this, mutableListOf<AppMainEntity>().apply {
            viewModel.infoList.subList(
                0, if (viewModel.infoList.size > 5) 5 else viewModel.infoList.size
            ).forEach {
                add(AppMainEntity(itemType = ItemType.infoItem.type, info = it))
            }
        }).apply {
            onInfoItemClick = {
                startContentActivity(type = ContentType.content, title = it?.title ?: "", infoEntity = it)
            }
        }
        return bind.root
    }

    private fun getRecordView(): View {
        val bind = LayoutRecordBinding.inflate(LayoutInflater.from(this))
        bind.recordNew.setOnClickListener {
            startContentActivity(ContentType.new, title = getString(R.string.title_record_new))
        }
        bind.recordMore.setOnClickListener {
            startContentActivity(ContentType.more, title = getString(R.string.title_record_more))
        }

        bind.recordRv.addItemDecoration(ItemDecoration(12))
        bind.recordRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = DataAdapter(this, mutableListOf()).apply {
            onRecordItemClick = {
                startContentActivity(type = ContentType.edit, title = "Edit Record", recordEntity = it)
            }
        }
        bind.recordRv.adapter = adapter
        return bind.root
    }

    private fun getInfoView(): View {
        val bind = LayoutInfoBinding.inflate(LayoutInflater.from(this))
        bind.infoRv.addItemDecoration(ItemDecoration(12))
        bind.infoRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bind.infoRv.adapter = DataAdapter(this, mutableListOf<AppMainEntity>().apply {
            viewModel.infoList.forEach {
                add(AppMainEntity(itemType = ItemType.mainItem.type, info = it))
            }
        }).apply {
            onInfoItemClick = {
                startContentActivity(type = ContentType.content, title = it?.title ?: "", infoEntity = it)
            }
        }
        return bind.root
    }

    private fun getSettingView(): View {
        val bind = LayoutSettingBinding.inflate(LayoutInflater.from(this))
        bind.settingRv.addItemDecoration(ItemDecoration(6))
        bind.settingRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bind.settingRv.adapter = DataAdapter(this, mutableListOf<AppMainEntity>().apply {

            viewModel.settingList.forEach {
                add(
                    AppMainEntity(
                        itemType = if (it.title == getString(R.string.title_alarm)) ItemType.settingTop.type else ItemType.settingItem.type,
                        setting = it
                    )
                )
            }
        }).apply {
            onSettingItemClick = { entity ->
                "entity?.title = ${entity?.title}".log()
                entity?.let {
                    when (it.title) {
                        getString(R.string.title_privacy) -> startContentActivity(
                            ContentType.web,
                            title = getString(R.string.title_privacy),
                            url = "https://www.jianshu.com/p/05bc825fa194"
                        )

                        getString(R.string.title_policy) -> startContentActivity(
                            ContentType.web,
                            title = getString(R.string.title_policy),
                            url = "https://blog.csdn.net/SanSanOtaku/article/details/119932790"
                        )

                        getString(R.string.title_share) -> sharedMySelf()
                    }
                }
            }
        }
        return bind.root
    }

    override fun initViewModel(): ActivityModel = ViewModelProvider(this)[ActivityModel::class.java]

    override fun initViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()
        getRecordList()
    }

    private fun getRecordList() {
        adapter.setList(
            mutableListOf<AppMainEntity>().apply {
                var sys = 0
                var dia = 0

                val list = viewModel.recordList.filter {
                    when (viewModel.filterRecordType) {
                        FilterRecordType.recent -> it.time > DateHelper.getTodayMills()
                        FilterRecordType.week -> it.time < DateHelper.getDayOfWeek(
                            Calendar.SATURDAY,
                            0
                        ) && it.time > DateHelper.getDayOfWeek(Calendar.SUNDAY, 0)

                        FilterRecordType.seven -> it.time > DateHelper.getLast7DaysMills()
                        FilterRecordType.month -> it.time > DateHelper.getThisMonthStart()
                        FilterRecordType.lastMonth -> it.time > DateHelper.getLastMonthStart() && it.time < DateHelper.getThisMonthStart()
                        else -> it.time < System.currentTimeMillis()
                    }
                }
                val size = if (list.size > 5) 5 else list.size
                list.subList(0, size).forEach {
                    sys += it.sys
                    dia += it.dia
                    add(AppMainEntity(itemType = ItemType.recordItem.type, record = it))
                }
                add(
                    0,
                    AppMainEntity(
                        itemType = ItemType.recordTop.type, recordTop = RecordEntityTop(
                            sys = if (size == 0) 0 else sys / size,
                            dia = if (size == 0) 0 else dia / size
                        )
                    )
                )
                if (viewModel.recordList.size > 0)
                    add(1, AppMainEntity(itemType = ItemType.recordChart.type, recordChart = viewModel.recordList))
            }
        )
    }

}