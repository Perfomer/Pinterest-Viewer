package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.pinterest

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.MutablePinterestRepository
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.PinterestRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class GeneralPinterestDataSource(private val cache: CachePinterestDataSource,
                                 private val network: NetworkPinterestDataSource)
    : PinterestRepository {

    override fun getFeedItems(url: String): RequestResult<List<FeedItem>> {
        return extractData({ getFeedItems(url) }, { putFeedItems(url, it) })
    }

    override fun getCategories(url: String): RequestResult<List<Category>> {
        return extractData({ getCategories(url) }, { putCategories(url, it) })
    }

    override fun getFeedItemDetails(url: String): RequestResult<FeedItemDetails> {
        return extractData({ getFeedItemDetails(url) }, { putFeedItemDetails(url, it) })
    }

    override fun getChannelDetails(url: String): RequestResult<ChannelDetails> {
        return extractData({ getChannelDetails(url) }, { putChannelDetails(url, it) })
    }

    override fun getPageTitle(url: String): RequestResult<String> {
        return extractData({ getPageTitle(url) }, { putPageTitle(url, it) })
    }

    private fun <T> extractData(request: PinterestRepository.() -> RequestResult<T>,
                                caching: MutablePinterestRepository.(RequestResult<T>) -> Unit): RequestResult<T> {
        val cachedData = request(cache)

        return when (cachedData) {
            is RequestResult.Data -> cachedData
            is RequestResult.Error -> request(network).apply { caching(cache, this) }
        }
    }

}