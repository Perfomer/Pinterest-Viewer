package com.volkovmedia.perfo.pinterestviewer.clean.domain.repository

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageRequest

interface PinterestRepository {

    fun getFeedItems(pageRequest: PageRequest): List<FeedItem>

    fun getFeedItemDetails(pageRequest: PageRequest): FeedItemDetails

    fun getChannel(pageRequest: PageRequest): Channel

}