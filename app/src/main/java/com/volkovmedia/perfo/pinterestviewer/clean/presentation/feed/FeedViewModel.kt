package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.FeedDataProvideInteractor
import kotlinx.coroutines.experimental.launch

open class FeedViewModel protected constructor(private val feedDataProvideInteractor: FeedDataProvideInteractor) : ViewModel() {

    val items: LiveData<List<FeedItem>>
        get() = itemsLiveData

    val title: LiveData<String>
        get() = titleLiveData

    var url: String = ""
        protected set(value) {
            field = value
            launch { onUrlSet() }
        }

    private val itemsLiveData = MutableLiveData<List<FeedItem>>()

    private val titleLiveData = MutableLiveData<String>()

    constructor(feedInteractor: FeedDataProvideInteractor, url: String) : this(feedInteractor) {
        this.url = url
    }

    @CallSuper
    protected open fun onUrlSet() {
        if (url.isBlank()) return

        url.request(itemsLiveData) { requestFeedItems(it) }
        url.request(titleLiveData) { requestPageTitle(it) }
    }

    protected fun <T> String.request(liveData: MutableLiveData<T>,
                                     request: FeedDataProvideInteractor.(String) -> RequestResult<T>) {
        val result = feedDataProvideInteractor.request(this)

        when (result) {
            is RequestResult.Data -> liveData.postValue(result.data)
            is RequestResult.Error -> result.throwable
        }
    }

}