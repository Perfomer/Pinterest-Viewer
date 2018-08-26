package com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors

import android.graphics.drawable.Drawable
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.ImageRepository

class ImageProvideInteractor(private val repository: ImageRepository) {

    fun provideBitmap(url: String): Drawable {
        return repository.loadDrawable(url)
    }

}