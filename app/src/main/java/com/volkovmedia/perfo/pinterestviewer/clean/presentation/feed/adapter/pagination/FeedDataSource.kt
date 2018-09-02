package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination

import android.arch.paging.PositionalDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser.Companion.PAGE_SIZE
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.FeedDataProvideInteractor

class FeedDataSource(private val feedDataProvideInteractor: FeedDataProvideInteractor,
                     private val url: String,
                     private val initialLoadingListener: (Boolean) -> Unit,
                     private val rangeLoadingListener: (Boolean) -> Unit)
    : PositionalDataSource<FeedItem>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<FeedItem>) {
        initialLoadingListener(true)

        val feedItems = feedDataProvideInteractor.requestFeedItems(url)

        initialLoadingListener(false)

        callback.onResult(feedItems, params.requestedStartPosition)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<FeedItem>) {
        val startPosition = params.startPosition

        rangeLoadingListener(true)

        val result = if (startPosition >= PAGE_SIZE) {
            val request = PageRequest(url, startPosition / PAGE_SIZE)
            feedDataProvideInteractor.requestFeedItems(request.getPagedUrl())
        } else {
            emptyList()
        }

        rangeLoadingListener(false)

        callback.onResult(result)
    }

}