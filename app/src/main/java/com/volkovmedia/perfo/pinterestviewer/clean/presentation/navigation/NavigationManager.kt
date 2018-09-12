package com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem

interface NavigationManager {

    fun back()

    fun dropToRoot()

    fun toRoot()

    fun toFeed(url: String)

    fun toChannel(channel: Channel)

    fun toFeedItemDetails(item: FeedItem)

    fun toCategories()

    fun toPhoto(photoUrl: String, fullPhotoUrl: String)

}