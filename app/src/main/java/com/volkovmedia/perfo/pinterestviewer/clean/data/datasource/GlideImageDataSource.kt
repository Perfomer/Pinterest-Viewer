package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.ImageRepository
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.fileExtension
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.loadFromUrl

class GlideImageDataSource(private val context: Context) : ImageRepository {

    override fun loadDrawable(url: String): Drawable {
        val requestManager = Glide.with(context.applicationContext)
        val requestBuilder = when (url.fileExtension) {
            "gif" -> requestManager.asGif()
            else -> requestManager.asDrawable()
        }

        return requestBuilder.load(url)
                .submit()
                .get()
    }

    override fun loadInto(view: ImageView, url: String) {
        view.loadFromUrl(url)
    }

}