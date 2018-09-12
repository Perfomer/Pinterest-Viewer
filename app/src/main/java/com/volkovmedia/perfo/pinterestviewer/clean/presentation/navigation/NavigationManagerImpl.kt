package com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.PinterestNavigator.Companion.SCREEN_CATEGORIES
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.PinterestNavigator.Companion.SCREEN_CHANNEL
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.PinterestNavigator.Companion.SCREEN_FEED
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.PinterestNavigator.Companion.SCREEN_FEED_ITEM_DETAILS
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.PinterestNavigator.Companion.SCREEN_PHOTO
import ru.terrakok.cicerone.Router

class NavigationManagerImpl(private val router: Router) : NavigationManager {

    override fun back() {
        router.exit()
    }

    override fun dropToRoot() {
        router.backTo(null)
    }

    override fun toRoot() {
        router.newRootScreen(SCREEN_FEED)
    }

    override fun toFeed(url: String) {
        SCREEN_FEED.forward(url)
    }

    override fun toChannel(channel: Channel) {
        SCREEN_CHANNEL.forward(channel)
    }

    override fun toFeedItemDetails(item: FeedItem) {
        SCREEN_FEED_ITEM_DETAILS.forward(item)
    }

    override fun toCategories() {
        SCREEN_CATEGORIES.forward()
    }

    override fun toPhoto(photoUrl: String, fullPhotoUrl: String) {
        SCREEN_PHOTO.forward(photoUrl to fullPhotoUrl)
    }

    private fun String.forward(data: Any? = null) {
        router.navigateTo(this, data)
    }

}