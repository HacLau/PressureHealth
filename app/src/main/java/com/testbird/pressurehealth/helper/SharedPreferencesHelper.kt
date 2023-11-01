package com.testbird.pressurehealth.helper

import android.content.Context
import com.testbird.pressurehealth.appContext
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedPreferencesHelper<T>(
    private val name: String,
    private val defaultValue: T
) : ReadWriteProperty<Any?, T> {
    private val sp by lazy {
        appContext.getSharedPreferences("PressureHealth", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (defaultValue) {
            is Int -> sp.getInt(name, defaultValue) as T
            is Float -> sp.getFloat(name, defaultValue) as T
            is Long -> sp.getLong(name, defaultValue) as T
            is Boolean -> sp.getBoolean(name, defaultValue) as T
            is String -> sp.getString(name, defaultValue) as T
            else -> throw IllegalArgumentException("type is error")
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        sp.edit().apply {
            when (value) {
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                is Boolean -> putBoolean(name, value)
                is String -> putString(name, value)
                else -> throw IllegalArgumentException("type is error")
            }
        }.apply()
    }
}