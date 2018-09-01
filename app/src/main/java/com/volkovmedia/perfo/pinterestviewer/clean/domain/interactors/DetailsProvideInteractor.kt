package com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.domain.ROOT_URL
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.PinterestRepository
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.requestDocument

class DetailsProvideInteractor(private val repository: PinterestRepository) {

    fun requestDetails(item: FeedItem): FeedItemDetails {
        val url = ROOT_URL.dropLast(1) + item.fullPageUrl
        return repository.getFeedItemDetails(url)
    }

}