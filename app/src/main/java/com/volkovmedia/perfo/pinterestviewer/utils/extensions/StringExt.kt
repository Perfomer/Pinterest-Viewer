package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.parseTime(format: String): Long {
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    return formatter.parse(this).time
}

val String.fileExtension: String
    get() = takeLastWhile { it != '.' }