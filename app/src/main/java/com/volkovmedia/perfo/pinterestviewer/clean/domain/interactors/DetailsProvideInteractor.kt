package com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.PinterestRepository

class DetailsProvideInteractor(private val repository: PinterestRepository) {

    fun requestDetails(item: FeedItem): RequestResult<FeedItemDetails> {
        return repository.getFeedItemDetails(item.fullPageUrl)
    }

}