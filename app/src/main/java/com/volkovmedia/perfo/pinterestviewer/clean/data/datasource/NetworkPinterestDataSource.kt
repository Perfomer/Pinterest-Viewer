package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.ChannelPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.DetailsPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.PinterestRepository

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

    override fun getChannel(pageRequest: PageRequest): ChannelDetails {
        return channelParser.request(pageRequest)
    }

}