package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.channel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.FeedDataProvideInteractor
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.FeedViewModel

class ChannelViewModel(feedDataProvideInteractor: FeedDataProvideInteractor,
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