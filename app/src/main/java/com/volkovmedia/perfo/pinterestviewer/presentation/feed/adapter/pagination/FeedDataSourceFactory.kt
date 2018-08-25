package com.volkovmedia.perfo.pinterestviewer.presentation.feed.adapter.pagination

import android.arch.paging.DataSource
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItem

class FeedDataSourceFactory : DataSource.Factory<Int, FeedItem>() {
    override fun create(): DataSource<Int, FeedItem> = FeedDataSource()
}