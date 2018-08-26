package com.volkovmedia.perfo.pinterestviewer.clean.domain.repository

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageRepository {

    fun loadDrawable(url: String): Drawable

    fun loadInto(view: ImageView, url: String)

}