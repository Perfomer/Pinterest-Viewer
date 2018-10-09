package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.volkovmedia.perfo.pinterestviewer.utils.GlideApp
import com.volkovmedia.perfo.pinterestviewer.utils.GlideRequest
import com.volkovmedia.perfo.pinterestviewer.utils.createRandomGrayColorDrawable

fun ImageView.load(url: String, withPlaceHolder: Boolean = false, circleCrop: Boolean = false, centerInside: Boolean = false) {
    GlideApp.with(context.applicationContext)
            .load(url)
            .applySettings(withPlaceHolder, circleCrop, centerInside)
            .into(this)
}

fun ImageView.load(drawable: Drawable, withPlaceHolder: Boolean = false, circleCrop: Boolean = false, centerInside: Boolean = false) {
    GlideApp.with(context.applicationContext)
            .load(drawable)
            .applySettings(withPlaceHolder, circleCrop, centerInside)
            .into(this)
}

fun String.loadImage(context: Context, withPlaceHolder: Boolean = false, circleCrop: Boolean = false, centerInside: Boolean = false): Drawable {
    val requestManager = GlideApp.with(context.applicationContext)

    val requestBuilder = when (fileExtension) {
        "gif" -> requestManager.asGif()
        else -> requestManager.asDrawable()
    }

    return requestBuilder.load(this)
            .applySettings(withPlaceHolder, circleCrop, centerInside)
            .error(ColorDrawable(Color.RED))
            .submit()
            .get()
}

private fun GlideRequest<out Drawable>.applySettings(withPlaceHolder: Boolean, circleCrop: Boolean, centerInside: Boolean): GlideRequest<out Drawable> {
    if (withPlaceHolder) placeholder(createRandomGrayColorDrawable())
    if (circleCrop) circleCrop()
    if (centerInside) centerInside()

    return this
}