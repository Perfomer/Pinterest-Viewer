package com.volkovmedia.perfo.pinterestviewer.clean.presentation.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.DetailsProvideInteractor
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class DetailsViewModel(private val detailsProvideInteractor: DetailsProvideInteractor) : ViewModel() {

    val item: LiveData<FeedItem>
        get() = itemLiveData

    val details: LiveData<FeedItemDetails>
        get() = detailsLiveData

    private val itemLiveData = MutableLiveData<FeedItem>()

    private val detailsLiveData = MutableLiveData<FeedItemDetails>()

    fun setItem(item: FeedItem) {
        itemLiveData.postValue(item)

        val bundle = async { detailsProvideInteractor.provideBundle(item) }
        launch(UI) { detailsLiveData.postValue(bundle.await()) }
    }

}