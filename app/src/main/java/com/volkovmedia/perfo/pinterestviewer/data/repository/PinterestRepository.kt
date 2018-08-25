package com.volkovmedia.perfo.pinterestviewer.data.repository

import com.volkovmedia.perfo.pinterestviewer.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.data.parsers.base.PageRequest

interface PinterestRepository {

    fun getFeedItems(pageRequest: PageRequest): List<FeedItem>

    fun getFeedItemDetails(pageRequest: PageRequest): FeedItemDetails

    fun getChannel(pageRequest: PageRequest): Channel

}