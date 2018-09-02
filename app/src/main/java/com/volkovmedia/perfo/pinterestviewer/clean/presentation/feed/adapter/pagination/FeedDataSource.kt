package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination

import android.arch.paging.PositionalDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser.Companion.PAGE_SIZE
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
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

        when (feedItems) {
            is RequestResult.Data -> callback.onResult(feedItems.data, params.requestedStartPosition)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<FeedItem>) {
        val startPosition = params.startPosition

        rangeLoadingListener(true)

        val result = if (startPosition >= PAGE_SIZE) {
            val request = PageRequest(url, startPosition / PAGE_SIZE)
            val feedItems = feedDataProvideInteractor.requestFeedItems(request.getPagedUrl())

            when (feedItems) {
                is RequestResult.Data -> feedItems.data
                is RequestResult.Error -> emptyList()
            }
        } else {
            emptyList()
        }

        rangeLoadingListener(false)

        callback.onResult(result)
    }

}