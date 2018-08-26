package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*


fun Float.toDp(context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.getResources().displayMetrics).toInt()
}

fun Long.toDateString(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.ENGLISH)
    return format.format(date)
}