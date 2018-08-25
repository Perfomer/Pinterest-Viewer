package com.volkovmedia.perfo.pinterestviewer.data.repository.datasource

import com.volkovmedia.perfo.pinterestviewer.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.data.parsers.ChannelPageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.DetailsPageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.FeedPageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.data.repository.PinterestRepository

class NetworkPinterestDataSource: PinterestRepository {

    private val channelParser = ChannelPageParser()
    private val feedParser = FeedPageParser()
    private val detailsParser = DetailsPageParser()

    override fun getFeedItems(pageRequest: PageRequest): List<FeedItem> {
        return feedParser.request(pageRequest)
    }

    override fun getFeedItemDetails(pageRequest: PageRequest): FeedItemDetails {
        return detailsParser.request(pageRequest)
    }

    override fun getChannel(pageRequest: PageRequest): Channel {
        return channelParser.request(pageRequest)
    }

}