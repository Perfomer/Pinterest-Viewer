package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.channel

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.View
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.FeedFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.FeedType
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.FeedViewModel
import com.volkovmedia.perfo.pinterestviewer.di.PARAM_CHANNEL
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.toSpan
import kotlinx.android.synthetic.main.channel_fragment.*
import org.koin.android.ext.android.get

class ChannelFragment : FeedFragment() {

    override val layoutResource = R.layout.channel_fragment

    override val feedAdapter = ChannelAdapter(::onItemClick, ::onFollowClick).apply { setHasStableIds(true) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val channel = arguments?.getParcelable<Channel>(KEY_CHANNEL)!!

        get<Context>()
        with(get<FeedViewModel>(FeedType.CHANNEL.name) { mapOf(PARAM_CHANNEL to channel) } as ChannelViewModel) {
            channelDetails.observe(this@ChannelFragment, Observer { onChannelsUpdated(channel, it!!) })
            provideViewModel(this)
        }
    }

    private fun onChannelsUpdated(channel: Channel, channelDetails: ChannelDetails) {
        (feedAdapter as? ChannelAdapter)?.setChannel(channel, channelDetails)

        channel_author.text = getString(R.string.channel_created_by, channelDetails.author.name).toSpan()

        channel_name.text = channel.name
        channel_avatar_text.text = channel.name.take(2).trim()
    }

    private fun onFollowClick() {

    }

    companion object {

        private const val KEY_FEED_TYPE = "feed_type"
        private const val KEY_CHANNEL = "channel"

        fun newInstance(channel: Channel) = ChannelFragment().withArguments {
            putInt(KEY_FEED_TYPE, FeedType.CHANNEL.ordinal)
            putParcelable(KEY_CHANNEL, channel)
        }

    }

}