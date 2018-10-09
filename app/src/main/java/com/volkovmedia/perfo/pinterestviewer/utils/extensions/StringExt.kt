package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.volkovmedia.perfo.pinterestviewer.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

val String.fileExtension: String
    get() = takeLastWhile { it != '.' }

fun String.parseTime(format: String): Long {
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    return formatter.parse(this).time
}

fun String.correctRoot(): String {
    return if (!startsWith("http")) BuildConfig.ROOT_URL.dropLast(1) + this
    else this
}

@Suppress("DEPRECATION")
fun String.toSpan(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT);
    } else {
        Html.fromHtml(this);
    }
}