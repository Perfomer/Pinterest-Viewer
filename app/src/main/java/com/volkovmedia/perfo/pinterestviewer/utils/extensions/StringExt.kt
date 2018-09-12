package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import com.bumptech.glide.Glide
import com.volkovmedia.perfo.pinterestviewer.clean.domain.ROOT_URL
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.*

val String.fileExtension: String
    get() = takeLastWhile { it != '.' }

fun String.parseTime(format: String): Long {
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    return formatter.parse(this).time
}

fun String.requestDocument(): Document = Jsoup.connect(this).get()

fun String.requestPageSource(): String = requestDocument().html()

fun String.correctRoot(): String {
    return if (!startsWith("http")) ROOT_URL.dropLast(1) + this
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