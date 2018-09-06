package com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors

import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.PinterestRepository

class FeedDataProvideInteractor(private val pinterestRepository: PinterestRepository) {

    fun requestFeedItems(url: String) = pinterestRepository.getFeedItems(url)

    fun requestChannelDetails(url: String) = pinterestRepository.getChannelDetails(url)

    fun requestPageTitle(url: String) = pinterestRepository.getPageTitle(url)

}