package com.testbird.pressurehealth.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.testbird.pressurehealth.R
import com.testbird.pressurehealth.appContext
import kotlinx.parcelize.Parcelize
import java.util.UUID

data class AppMainEntity(
    var itemType: Int,
    var isShow: Boolean = true,
    var record: RecordEntity? = null,
    var recordTop: RecordEntityTop? = null,
    var recordChart: MutableList<RecordEntity> = mutableListOf(),
    var info: InfoEntity? = null,
    var setting: SettingEntity? = null
)

enum class ContentType(val type: String) {
    new("NewRecord"), more("moreRecord"), content("contentRecord"), web("webView"), edit("edit")
}

object Contacts {
    val title = "title"
    val pageType = "pageType"
    val infoEntity = "infoEntity"
    val recordEntity = "recordEntity"
    val url = "url"
}

enum class ItemType(public val type: Int) {
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
    var from: String,
    @DrawableRes
    var image: Int
) : Parcelable

@Parcelize
data class RecordEntityTop(
    var sys: Int,
    var dia: Int
) : Parcelable

@Entity(tableName = "record")
@Parcelize
data class RecordEntity @Ignore constructor(
    var level: Int = 0,
    var title: String = "",
    var time: Long = 0L,
    var sys: Int = 0,
    var dia: Int = 0,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable{
    constructor() : this(id =0)
}

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
object FilterRecordType{
    val recent = appContext.getString(R.string.record_recent)
    val week = appContext.getString(R.string.record_week)
    val seven = appContext.getString(R.string.record_last_7days)
    val month = appContext.getString(R.string.record_month)
    val lastMonth = appContext.getString(R.string.record_last_month)
    val all = appContext.getString(R.string.record_all)
}




