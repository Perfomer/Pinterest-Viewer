package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.ChannelPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.DetailsPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.PinterestRepository
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.requestPageSource
import org.jsoup.nodes.Document

class NetworkPinterestDataSource : PinterestRepository {

    private val channelParser = ChannelPageParser()
    private val feedParser = FeedPageParser()
    private val detailsParser = DetailsPageParser()

    override fun getFeedItems(url: String): List<FeedItem> {
        return feedParser.request(url.requestPageSource())
    }

    override fun getFeedItemDetails(url: String): FeedItemDetails {
        return detailsParser.request(url.requestPageSource())
    }

    override fun getChannelDetails(url: String): ChannelDetails {
        return channelParser.request(url.requestPageSource())
    }

}