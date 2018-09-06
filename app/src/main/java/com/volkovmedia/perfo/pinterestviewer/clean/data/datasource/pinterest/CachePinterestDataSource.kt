package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.pinterest

import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.MutableDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.MutablePinterestRepository

class CachePinterestDataSource : MutablePinterestRepository, MutableDataSource {

    private val feedItems = mutableMapOf<String, List<FeedItem>>()
    private val categories = mutableMapOf<String, List<Category>>()
    private val feedItemsDetails = mutableMapOf<String, FeedItemDetails>()
    private val channelsDetails = mutableMapOf<String, ChannelDetails>()
    private val pageTitles = mutableMapOf<String, String>()

    override fun getFeedItems(url: String): RequestResult<List<FeedItem>> {
        return feedItems.extractData(url)
    }

    override fun getCategories(url: String): RequestResult<List<Category>> {
        return categories.extractData(url)
    }

    override fun getFeedItemDetails(url: String): RequestResult<FeedItemDetails> {
        return feedItemsDetails.extractData(url)
    }

    override fun getChannelDetails(url: String): RequestResult<ChannelDetails> {
        return channelsDetails.extractData(url)
    }

    override fun getPageTitle(url: String): RequestResult<String> {
        return pageTitles.extractData(url)
    }

    override fun putFeedItems(url: String, data: RequestResult<List<FeedItem>>) {
        feedItems.putData(url, data)
    }

    override fun putCategories(url: String, data: RequestResult<List<Category>>) {
        categories.putData(url, data)
    }

    override fun putFeedItemDetails(url: String, data: RequestResult<FeedItemDetails>) {
        feedItemsDetails.putData(url, data)
    }

    override fun putChannelDetails(url: String, data: RequestResult<ChannelDetails>) {
        channelsDetails.putData(url, data)
    }

    override fun putPageTitle(url: String, data: RequestResult<String>) {
        pageTitles.putData(url, data)
    }

}