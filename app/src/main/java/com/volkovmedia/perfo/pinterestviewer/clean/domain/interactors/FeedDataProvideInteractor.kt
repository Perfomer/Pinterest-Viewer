package com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors

import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.entity.FeedRequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.PinterestRepository

class FeedDataProvideInteractor(private val repository: PinterestRepository) {

    fun requestChannelDetails(url: String) = repository.getChannelDetails(url)

    fun requestPageTitle(url: String) = repository.getPageTitle(url)

    fun requestMoreFeedItems(url: String, page: Int): FeedRequestResult {
        val actualUrl = if (page == 1) url else PageRequest(url, page).getPagedUrl()

        val items = repository.getFeedItems(actualUrl)

        val list = when (items) {
            is RequestResult.Data -> items.data
            is RequestResult.Error -> emptyList()
        }

        return FeedRequestResult(list, page)
    }

}