package com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.categories.CategoriesFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.channel.ChannelFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.details.DetailsFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.QueryFeedFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.photo.PhotoFragment
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.toast
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import ru.terrakok.cicerone.android.SupportFragmentNavigator

class PinterestNavigator(fragmentManager: FragmentManager,
                         containerId: Int)
    : SupportFragmentNavigator(fragmentManager, containerId), KoinComponent {

    @Suppress("UNCHECKED_CAST")
    override fun createFragment(screenKey: String, data: Any?): Fragment? = when (screenKey) {
        SCREEN_FEED -> {
            when (data) {
                is String -> QueryFeedFragment.newInstance(data)
                else -> QueryFeedFragment()
            }
        }
        SCREEN_CHANNEL -> {
            ChannelFragment.newInstance(data as Channel)
        }
        SCREEN_FEED_ITEM_DETAILS -> {
            DetailsFragment.newInstance(data as FeedItem)
        }
        SCREEN_CATEGORIES -> {
            CategoriesFragment.newInstance()
        }
        SCREEN_PHOTO -> {
            val arguments = data as Pair<String, String>
            PhotoFragment.newInstance(arguments.first, arguments.second)
        }
        else -> null
    }

    override fun showSystemMessage(message: String?) {
        message?.let { get<Context>().toast(it) }
    }

    override fun exit() {
        System.exit(0)
    }

    companion object {

        const val SCREEN_FEED = "feed"
        const val SCREEN_CHANNEL = "channel"
        const val SCREEN_FEED_ITEM_DETAILS = "feed_item_details"
        const val SCREEN_CATEGORIES = "categories"
        const val SCREEN_PHOTO = "photo"

    }

}