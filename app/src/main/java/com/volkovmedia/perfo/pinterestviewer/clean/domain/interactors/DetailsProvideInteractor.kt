package com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.PinterestRepository

class DetailsProvideInteractor(private val repository: PinterestRepository) {

    fun provideBundle(item: FeedItem): FeedItemDetails {
        return repository.getFeedItemDetails(PageRequest(item.fullPageUrl))
    }

}