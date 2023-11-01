package com.testbird.pressurehealth.helper

fun Boolean.yes(funTrue: () -> Unit) {
    if (this)
        funTrue.invoke()
}

fun Boolean.no(funFalse: () -> Unit) {
    if (this.not())
        funFalse.invoke()
}

fun Boolean.or(funTrue: () -> Unit, funFalse: () -> Unit) {
    if (this)
        yes(funTrue)
    else
        no(funFalse)
}