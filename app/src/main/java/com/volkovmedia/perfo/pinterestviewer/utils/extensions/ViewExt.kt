package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

var View.isVisible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun ImageView.loadFromUrl(url: String) {
    Glide.with(context.applicationContext)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun ImageView.loadCircleFromUrl(url: String) {
    Glide.with(context.applicationContext)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}