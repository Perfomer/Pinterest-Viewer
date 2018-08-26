package com.volkovmedia.perfo.pinterestviewer.clean.presentation.photo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.ImageProvideInteractor
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class PhotoViewModel(private val interactor: ImageProvideInteractor) : ViewModel() {

    val preview: LiveData<Drawable>
        get() = previewLiveData

    val full: LiveData<Drawable>
        get() = fullLiveData

    private val previewLiveData = MutableLiveData<Drawable>()

    private val fullLiveData = MutableLiveData<Drawable>()

    fun setUrls(previewUrl: String, fullUrl: String) {
        val previewBitmap = async { interactor.provideBitmap(previewUrl) }
        val fullBitmap = async { interactor.provideBitmap(fullUrl) }

        launch(UI) {
            previewLiveData.postValue(previewBitmap.await())
            fullLiveData.postValue(fullBitmap.await())
        }
    }

}