package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.pinterest

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.ChannelPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.DetailsPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.page.PageRepository
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.PinterestRepository
import org.koin.standalone.KoinComponent

class NetworkPinterestDataSource(private val pageDataSource: PageRepository,
                                 private val channelParser: ChannelPageParser,
                                 private val feedParser: FeedPageParser,
                                 private val detailsParser: DetailsPageParser) : PinterestRepository, KoinComponent {

    override fun getFeedItems(url: String): RequestResult<List<FeedItem>> {
        return feedParser.extractData(url)
    }

    override fun getFeedItemDetails(url: String): RequestResult<FeedItemDetails> {
        return detailsParser.extractData(url)
    }

    override fun getChannelDetails(url: String): RequestResult<ChannelDetails> {
        return channelParser.extractData(url)
    }

    private fun <T> PageParser<T>.extractData(url: String): RequestResult<T> {
        val result = pageDataSource.getPageSource(url)

        return when(result) {
            is RequestResult.Data -> request(result.data)
            is RequestResult.Error -> RequestResult.Error(result.throwable)
        }
    }

}