package com.testbird.pressurehealth.model

import android.os.Parcelable
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.appContext
import kotlinx.parcelize.Parcelize

data class AppMainEntity(
    var itemType: Int,
    var isShow: Boolean = true,
    var record: RecordEntity? = null,
    var info: InfoEntity? = null,
    var setting: SettingEntity? = null
)

enum class ContentType(type: String) {
    new("NewRecord"), more("moreRecord"), content("contentRecord"), web("webView")
}

object Contacts {
    val pageType = "pageType"
    val infoEntity = "infoEntity"
    val recordEntity = "recordEntity"
    val url = "url"
}

enum class ItemType(number: Int) {
    mainItem(1),
    recordTop(2),
    recordChart(3),
    recordItem(4),
    infoItem(5),
    settingTop(6),
    settingItem(7)
}

@Parcelize
data class InfoEntity(
    var title: String,
    var content: String,
    var image: String
) : Parcelable

@Parcelize
data class RecordEntity(
    var level: Int,
    var title: String,
    var time: Long,
    var sys: Int,
    var dia: Int
) : Parcelable

data class SettingEntity(
    var title: String,
    var content: String? = null
)

val infoImageList = mutableListOf<Int>(
    R.mipmap.ic_news_image1,
    R.mipmap.ic_news_image2,
    R.mipmap.ic_news_image3,
    R.mipmap.ic_news_image4,

    )

val mineList = mutableListOf<AppMainEntity>(
    AppMainEntity(itemType = ItemType.settingTop.ordinal, setting = SettingEntity(appContext.getString(R.string.title_alarm))),
    AppMainEntity(itemType = ItemType.settingItem.ordinal, setting = SettingEntity(appContext.getString(R.string.title_language))),
    AppMainEntity(itemType = ItemType.settingItem.ordinal, setting = SettingEntity(appContext.getString(R.string.title_alarm))),
    AppMainEntity(itemType = ItemType.settingItem.ordinal, setting = SettingEntity(appContext.getString(R.string.title_alarm))),
    AppMainEntity(itemType = ItemType.settingItem.ordinal, setting = SettingEntity(appContext.getString(R.string.title_alarm))),
    AppMainEntity(itemType = ItemType.settingItem.ordinal, setting = SettingEntity(appContext.getString(R.string.title_alarm)))
)



