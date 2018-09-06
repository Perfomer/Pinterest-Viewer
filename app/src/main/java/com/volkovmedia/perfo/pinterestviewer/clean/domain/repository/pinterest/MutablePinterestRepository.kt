package com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult

interface MutablePinterestRepository : PinterestRepository {

    fun putFeedItems(url: String, data: RequestResult<List<FeedItem>>)

    fun putCategories(url: String, data: RequestResult<List<Category>>)

    fun putFeedItemDetails(url: String, data: RequestResult<FeedItemDetails>)

    fun putChannelDetails(url: String, data: RequestResult<ChannelDetails>)

    fun putPageTitle(url: String, data: RequestResult<String>)

}