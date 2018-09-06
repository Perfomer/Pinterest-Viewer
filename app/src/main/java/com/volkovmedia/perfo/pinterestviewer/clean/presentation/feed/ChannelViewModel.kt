package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.ROOT_URL
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.FeedDataProvideInteractor

class ChannelViewModel(private val feedDataProvideInteractor: FeedDataProvideInteractor,
                       channel: Channel)
    : FeedViewModel(feedDataProvideInteractor) {

    init {
        url = channel.url
    }

    val channelDetails: LiveData<ChannelDetails>
        get() = channelLiveData

    private val channelLiveData = MutableLiveData<ChannelDetails>()

    override fun onUrlSet() {
        super.onUrlSet()
        url.request(channelLiveData) { requestChannelDetails(it) }
    }

}