package com.volkovmedia.perfo.pinterestviewer.clean.domain.repository

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import org.jsoup.nodes.Document

interface PinterestRepository {

    fun getFeedItems(url: String): RequestResult<List<FeedItem>>

    fun getFeedItemDetails(url: String): RequestResult<FeedItemDetails>

    fun getChannelDetails(url: String): RequestResult<ChannelDetails>

}