package com.testbird.pressurehealth.helper

import android.content.Context
import android.util.Log
import android.widget.Toast

fun String.log(){
    Log.e("StringHelper",this)
}

fun String.toast(context: Context){
    Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
}