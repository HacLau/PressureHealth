package com.testbird.pressurehealth.view

import android.content.res.ColorStateList
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.adapter.DataAdapter
import com.testbird.pressurehealth.adapter.ItemDecoration
import com.testbird.pressurehealth.base.BaseActivity
import com.testbird.pressurehealth.database.RecordDatabase
import com.testbird.pressurehealth.databinding.ActivityContentBinding
import com.testbird.pressurehealth.helper.DateHelper
import com.testbird.pressurehealth.helper.dp2px
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.helper.toast
import com.testbird.pressurehealth.model.AppMainEntity
import com.testbird.pressurehealth.model.Contacts
import com.testbird.pressurehealth.model.ContentType
import com.testbird.pressurehealth.model.InfoEntity
import com.testbird.pressurehealth.model.ItemType
import com.testbird.pressurehealth.model.RecordEntity
import com.testbird.pressurehealth.model.RecordEntityTop
import com.testbird.pressurehealth.view.ui.TimeSettingPop
import com.testbird.pressurehealth.viewmodel.ActivityModel

class ContentActivity : BaseActivity<ActivityContentBinding, ActivityModel>() {
    private lateinit var currentType: String
    private lateinit var recordEntity: RecordEntity
    override fun initView() {
        binding.back.setOnClickListener {
            finish()
        }
        currentType = intent.getStringExtra(Contacts.pageType) ?: ""
        when (currentType) {
            ContentType.new.type, ContentType.edit.type -> initNewAndEditRecord()
            ContentType.more.type -> initMoreRecord()
            ContentType.web.type -> initWebView()
            ContentType.content.type -> initContent()
            else -> {
                throw IllegalAccessException("current type is empty")
            }
        }
        binding.title.text = intent.getStringExtra(Contacts.title)
    }

    override fun onResume() {
        super.onResume()
        if (currentType == ContentType.more.type) {
            getMoreRecordData()
        }
    }

    private fun getMoreRecordData() {
        (binding.moreCl.moreRv.adapter as DataAdapter).setList(mutableListOf<AppMainEntity>().apply {
            var sys = 0
            var dia = 0
            viewModel.recordList.forEach {
                sys += it.sys
                dia += it.dia
                add(AppMainEntity(itemType = ItemType.recordItem.type, record = it))
            }
        })
    }

    private fun initNewAndEditRecord() {
        binding.recordNewCl.root.visibility = View.VISIBLE
        recordEntity = intent.getParcelableExtra<RecordEntity>(Contacts.recordEntity) ?: RecordEntity(
            1,
            getString(R.string.title_h1),
            System.currentTimeMillis(),
            90,
            60
        )
        setPageData()
        binding.recordNewCl.npSys.onSelect = ::sysSelected
        binding.recordNewCl.npDias.onSelect = ::diasSelected

        binding.recordNewCl.npSys.onMove = ::sysSelected
        binding.recordNewCl.npDias.onMove = ::diasSelected

        binding.recordNewCl.recordSave.setOnClickListener {
            saveRecordData()
        }
        binding.recordNewCl.llTime.setOnClickListener {
            "click ll time".log()
            TimeSettingPop(
                context = this,
                time = recordEntity.time,
                yearList = viewModel.yearPickerDataList,
                monthList = viewModel.monthPickerDataList,
                hourList = viewModel.hourPickerDataList,
                minuteList = viewModel.minutePickerDataList
            ).apply {
                this.confirm = {
                    recordEntity.time = it
                    binding.recordNewCl.recordTime.text = DateHelper.getFormatTimeRecordItem(recordEntity.time)
                }
            }.showAtLocation(binding.parentContent, Gravity.BOTTOM, 0, 0)
        }
    }

    private fun saveRecordData() {
        if (recordEntity.sys < recordEntity.dia) {
            "Systolic must be > Diastolic".toast(this)
            return
        }
        when (currentType) {
            ContentType.edit.type -> RecordDatabase.getDatabase().recordDao().update(recordEntity)
            ContentType.new.type -> RecordDatabase.getDatabase().recordDao().insert(recordEntity)
        }
        finish()
    }

    private fun sysSelected(value: String) {
        recordEntity.sys = value.toInt()
        setLevelData()
    }

    private fun setLevelData() {
        recordEntity.level =
            if (recordEntity.sys > 180 || recordEntity.dia > 120)
                5
            else if (recordEntity.sys >= 140 || recordEntity.dia >= 90)
                4
            else if (recordEntity.sys >= 130 || recordEntity.dia >= 80)
                3
            else if (recordEntity.sys >= 120 && recordEntity.dia >= 60)
                2
            else if (recordEntity.sys >= 90 && recordEntity.dia >= 60)
                1
            else if (recordEntity.sys < 90 || recordEntity.dia < 60)
                0
            else 0

        setPageData()
    }

    private fun diasSelected(value: String) {
        recordEntity.dia = value.toInt()
        setLevelData()
    }


    private fun setPageData() {
        binding.recordNewCl.recordTime.text = DateHelper.getFormatTimeRecordItem(recordEntity.time)
        viewModel.numberPickerDataList.let {
            binding.recordNewCl.npSys.setData(it, it.indexOf("${recordEntity.sys}"))
            binding.recordNewCl.npDias.setData(it, it.indexOf("${recordEntity.dia}"))
        }
        var title = R.string.title_h0
        var content = R.string.content_h0
        var des = R.string.des_h0
        var color = R.color.result_0
        when (recordEntity.level) {
            0 -> {
                title = R.string.title_h0
                content = R.string.content_h0
                des = R.string.des_h0
                color = R.color.result_0
            }

            1 -> {
                title = R.string.title_h1
                content = R.string.content_h1
                des = R.string.des_h1
                color = R.color.result_1
            }

            2 -> {
                title = R.string.title_h2
                content = R.string.content_h2
                des = R.string.des_h2
                color = R.color.result_2
            }

            3 -> {
                title = R.string.title_h3
                content = R.string.content_h3
                des = R.string.des_h3
                color = R.color.result_3

            }

            4 -> {
                title = R.string.title_h4
                content = R.string.content_h4
                des = R.string.des_h4
                color = R.color.result_4

            }

            5 -> {
                title = R.string.title_h5
                content = R.string.content_h5
                des = R.string.des_h5
                color = R.color.result_5
            }
        }

        binding.recordNewCl.levelScale.translationX = (10f + 17.2f * recordEntity.level + 36f * recordEntity.level).dp2px(this)
        ColorStateList.valueOf(ContextCompat.getColor(this, color)).let {
            binding.recordNewCl.levelTitle.setTextColor(it)
            binding.recordNewCl.levelContent.setTextColor(it)
            binding.recordNewCl.caleSys.imageTintList = it
            binding.recordNewCl.caleDias.imageTintList = it
            binding.recordNewCl.levelScale.imageTintList = it
        }
        binding.recordNewCl.levelTitle.text = getString(title)
        binding.recordNewCl.levelContent.text = getString(content)
        binding.recordNewCl.levelDes.text = getString(des)
    }

    private fun initContent() {
        binding.contentCl.root.visibility = View.VISIBLE
        intent.getParcelableExtra<InfoEntity>(Contacts.infoEntity)?.let {
            binding.contentCl.contentTitle.text = it.title
            binding.contentCl.contentText.text = "\t\t${it.content}"
            binding.contentCl.contentFrom.text = it.from
            binding.contentCl.contentImage.setImageResource(it.image)
        }
        binding.contentCl.back.setOnClickListener {
            finish()
        }
    }

    private fun initWebView() {
        binding.webCl.root.visibility = View.VISIBLE
        intent.getStringExtra(Contacts.url)?.let {
            binding.webCl.webView.loadUrl(it)
        }
    }

    private fun initNoRecord() {
        binding.recordNoCl.root.visibility = View.VISIBLE
        binding.recordNoCl.recordNoGo.setOnClickListener {
            startContentActivity(type = ContentType.edit, title = getString(R.string.title_record_new))
        }
    }

    private fun initHaveRecord() {
        binding.moreCl.root.visibility = View.VISIBLE
        binding.moreCl.moreRv.addItemDecoration(ItemDecoration(12))
        binding.moreCl.moreRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.moreCl.moreRv.adapter = DataAdapter(this, mutableListOf()).apply {
            onRecordItemClick = {
                startContentActivity(type = ContentType.edit, title = "Edit Record", recordEntity = it)
            }
        }
    }

    private fun initMoreRecord() {
        if (viewModel.recordList.isEmpty())
            initNoRecord()
        else
            initHaveRecord()
    }


    override fun initViewModel(): ActivityModel = ViewModelProvider(this)[ActivityModel::class.java]

    override fun initViewBinding(): ActivityContentBinding = ActivityContentBinding.inflate(layoutInflater)

}