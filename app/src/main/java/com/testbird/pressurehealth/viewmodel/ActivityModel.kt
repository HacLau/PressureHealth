package com.testbird.pressurehealth.viewmodel

import androidx.lifecycle.ViewModel
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.appContext
import com.testbird.pressurehealth.helper.AssetsHelper
import com.testbird.pressurehealth.helper.log
import com.testbird.pressurehealth.model.InfoEntity
import com.testbird.pressurehealth.model.RecordEntity
import com.testbird.pressurehealth.model.SettingEntity
import org.json.JSONArray


class ActivityModel : ViewModel() {
    val infoList: List<InfoEntity> = getInfoListFromJson()

    private fun getInfoListFromJson(): List<InfoEntity> {
        val infoJson: String =  AssetsHelper.getJsonFromAssets("info.json")
        "infoJson = ${infoJson}".log()
        return mutableListOf<InfoEntity>().apply {
            kotlin.runCatching {
                val jsonArray = JSONArray(infoJson)
                for (i in 0 until jsonArray.length()) {
                    jsonArray.getJSONObject(i).let {
                        add(InfoEntity(it.getString("title"), it.getString("content"), it.getString("from")))
                    }
                }
            }
        }
    }

    val settingList: List<SettingEntity> = mutableListOf<SettingEntity>().apply {
        add(SettingEntity(appContext.getString(R.string.title_alarm)))
        add(SettingEntity(appContext.getString(R.string.title_language)))
        add(SettingEntity(appContext.getString(R.string.title_share)))
        add(SettingEntity(appContext.getString(R.string.title_privacy)))
        add(SettingEntity(appContext.getString(R.string.title_policy)))
        add(SettingEntity(appContext.getString(R.string.title_contact), appContext.getString(R.string.title_mail)))
    }

    val recordList: MutableList<RecordEntity> = mutableListOf<RecordEntity>().apply {

    }
}