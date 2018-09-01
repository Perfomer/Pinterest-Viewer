package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination

import android.arch.paging.DataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.FeedDataProvideInteractor
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeedDataSourceFactory(private val url: String) : DataSource.Factory<Int, FeedItem>(), KoinComponent {

    private val feedDataProvideInteractor: FeedDataProvideInteractor by inject()

    override fun create(): DataSource<Int, FeedItem> = FeedDataSource(feedDataProvideInteractor, url)

}