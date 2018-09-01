package com.volkovmedia.perfo.pinterestviewer.clean.domain.repository

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import org.jsoup.nodes.Document

interface PinterestRepository {

    fun getFeedItems(url: String): List<FeedItem>

    fun getFeedItemDetails(url: String): FeedItemDetails

    fun getChannelDetails(url: String): ChannelDetails

}