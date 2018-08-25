package com.volkovmedia.perfo.pinterestviewer.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.parseTime(format: String): Long {
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    return formatter.parse(this).time
}