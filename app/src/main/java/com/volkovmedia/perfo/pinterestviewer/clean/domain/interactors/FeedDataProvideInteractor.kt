package com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors

import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.PinterestRepository
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.requestDocument
import kotlinx.coroutines.experimental.async

class FeedDataProvideInteractor(private val pinterestRepository: PinterestRepository) {

    fun requestFeedItems(url: String) = pinterestRepository.getFeedItems(url)

    fun requestChannelDetails(url: String) = pinterestRepository.getChannelDetails(url)

}