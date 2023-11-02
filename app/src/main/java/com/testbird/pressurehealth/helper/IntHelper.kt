package com.testbird.pressurehealth.helper

import android.content.Context

fun Int.month():String{
    return when(this){
        0->{"January"}
        1->{"February"}
        2->{"March"}
        3->{"April"}
        4->{"May"}
        5->{"June"}
        6->{"July"}
        7->{"August"}
        8->{"September"}
        9->{"October"}
        10->{"November"}
        11->{"December"}
        else->{""}
    }
}

fun Int.dp2px(context: Context):Int{
    return (context.resources.displayMetrics.density * this).toInt()
}

fun Float.dp2px(context: Context):Float{
    return (context.resources.displayMetrics.density * this)
}

fun Int.two():String{
    return if (this < 10)
        "0${this}"
    else
        "$this"
}
