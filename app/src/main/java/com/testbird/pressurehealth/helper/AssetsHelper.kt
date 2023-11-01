package com.testbird.pressurehealth.helper

import com.testbird.pressurehealth.appContext
import java.nio.charset.Charset
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object AssetsHelper {
   fun getJsonFromAssets(fileName: String): String {
        kotlin.runCatching {
            return appContext.assets.open(fileName).let {
                val buffer = ByteArray(it.available())
                it.read(buffer)
                it.close()
                String(buffer, Charset.defaultCharset())
            }
        }
        return ""
    }




}