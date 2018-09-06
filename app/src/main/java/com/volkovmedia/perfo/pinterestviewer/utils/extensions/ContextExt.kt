package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.toast(@StringRes textRes: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textRes, length).show()
}