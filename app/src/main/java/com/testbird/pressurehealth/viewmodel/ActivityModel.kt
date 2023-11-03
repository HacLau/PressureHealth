package com.testbird.pressurehealth.viewmodel

import androidx.lifecycle.ViewModel
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.appContext
import com.testbird.pressurehealth.database.RecordDatabase
import com.testbird.pressurehealth.helper.AssetsHelper
import com.testbird.pressurehealth.helper.DateHelper
import com.testbird.pressurehealth.helper.two
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.model.FilterRecordType
import com.testbird.pressurehealth.model.InfoEntity
import com.testbird.pressurehealth.model.RecordEntity
import com.testbird.pressurehealth.model.SettingEntity
import com.testbird.pressurehealth.model.infoImageList
import org.json.JSONArray


class ActivityModel : ViewModel() {
    var filterRecordType: String = FilterRecordType.recent
    val infoList: List<InfoEntity>
        get() {
            val infoJson: String = AssetsHelper.getJsonFromAssets("info.json")
            return mutableListOf<InfoEntity>().apply {
                kotlin.runCatching {
                    val jsonArray = JSONArray(infoJson)
                    for (i in 0 until jsonArray.length()) {
                        jsonArray.getJSONObject(i).let {
                            add(InfoEntity(it.getString("title"), it.getString("content"), it.getString("from"), infoImageList.random()))
                        }
                    }
                }
            }
        }
    val settingList: List<SettingEntity>
        get() {
            return mutableListOf<SettingEntity>().apply {
//                add(SettingEntity(appContext.getString(R.string.title_alarm)))
//                add(SettingEntity(appContext.getString(R.string.title_language)))
                add(SettingEntity(appContext.getString(R.string.title_share)))
                add(SettingEntity(appContext.getString(R.string.title_privacy)))
                add(SettingEntity(appContext.getString(R.string.title_policy)))
//                add(SettingEntity(appContext.getString(R.string.title_contact), appContext.getString(R.string.title_mail)))
            }
        }

    val recordList: MutableList<RecordEntity>
        get() {
            return RecordDatabase.getDatabase().recordDao().queryAll()
        }

    val numberPickerDataList: MutableList<String>
        get() {
            return mutableListOf<String>().apply {
                (20..300).forEach {
                    add("$it")
                }
            }
        }

    val yearPickerDataList: MutableList<String>
        get() {
            return mutableListOf<String>().apply {
                (2022..2024).forEach {
                    add(it.two())
                }
            }
        }
    val monthPickerDataList: MutableList<String>
        get() {
            return mutableListOf<String>().apply {
                (1..12).forEach {
                    add(it.two())
                }
            }
        }


    val hourPickerDataList: MutableList<String>
        get() {
            return mutableListOf<String>().apply {
                (0..23).forEach {
                    add(it.two())
                }
            }
        }
    val minutePickerDataList: MutableList<String>
        get() {
            return mutableListOf<String>().apply {
                (0..60).forEach {
                    add(it.two())
                }
            }
        }

}