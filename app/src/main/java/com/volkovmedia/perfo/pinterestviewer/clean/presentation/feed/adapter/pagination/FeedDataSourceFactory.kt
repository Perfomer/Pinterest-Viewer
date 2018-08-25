package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination

import android.arch.paging.DataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem

class FeedDataSourceFactory : DataSource.Factory<Int, FeedItem>() {
    override fun create(): DataSource<Int, FeedItem> = FeedDataSource()
}