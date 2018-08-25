package com.volkovmedia.perfo.pinterestviewer.presentation.feed.adapter.pagination

import android.arch.paging.PositionalDataSource
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.data.parsers.FeedPageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.FeedPageParser.Companion.PAGE_SIZE
import com.volkovmedia.perfo.pinterestviewer.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.domain.ROOT_URL
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class FeedDataSource() : PositionalDataSource<FeedItem>() {

    private val pageParser = FeedPageParser()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<FeedItem>) {
        val blockingOperation = async {
            pageParser.request(PageRequest(ROOT_URL))
        }

        launch {
            val result = blockingOperation.await()
            callback.onResult(result, params.requestedStartPosition)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<FeedItem>) {
        val blockingOperation = async {
            pageParser.request(PageRequest(ROOT_URL, params.startPosition / PAGE_SIZE))
        }

        launch {
            val result = blockingOperation.await()
            callback.onResult(result)
        }
    }

}