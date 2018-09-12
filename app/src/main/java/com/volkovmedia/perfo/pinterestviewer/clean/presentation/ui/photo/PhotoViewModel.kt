package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.photo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.RequestOptions
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.loadImage
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    val preview: LiveData<Drawable>
        get() = previewLiveData

    val full: LiveData<Drawable>
        get() = fullLiveData

    private val previewLiveData = MutableLiveData<Drawable>()

    private val fullLiveData = MutableLiveData<Drawable>()

    fun setUrls(previewUrl: String, fullUrl: String) = launch {
        previewLiveData.postValue(previewUrl.loadImage())
        fullLiveData.postValue(fullUrl.loadImage())
    }

    private fun String.loadImage(): Drawable {
        return loadImage(getApplication())
    }

}