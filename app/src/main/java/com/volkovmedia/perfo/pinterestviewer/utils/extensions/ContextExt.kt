package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.widget.Toast

fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.toast(@StringRes textRes: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textRes, length).show()
}

@ColorInt
fun Context.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}