package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.channel

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.channel.followers.FollowersAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.FeedFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.FeedType
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.FeedViewModel
import com.volkovmedia.perfo.pinterestviewer.di.PARAM_CHANNEL
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.toSpan
import kotlinx.android.synthetic.main.channel_fragment.*
import kotlinx.android.synthetic.main.channel_header.*
import org.koin.android.ext.android.get

class ChannelFragment : FeedFragment() {

    override val layoutResource = R.layout.channel_fragment

    private val followersAdapter by lazy { FollowersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initSpecificViews() {
        val channel = arguments!!.getParcelable<Channel>(KEY_CHANNEL)

        general_toolbar.title = channel.name

        channel_followers.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = followersAdapter
        }

        channel_follow_button.setOnClickListener { onFollowClick() }

        with(get<FeedViewModel>(FeedType.CHANNEL.name) { mapOf(PARAM_CHANNEL to channel) } as ChannelViewModel) {
            channelDetails.observe(this@ChannelFragment, Observer { onChannelsUpdated(channel, it!!) })
            viewModel = this
        }

    }

    private fun onChannelsUpdated(channel: Channel, channelDetails: ChannelDetails) {
        channel_author.text = getString(R.string.channel_created_by, channelDetails.author.name).toSpan()

        channel_name.text = channel.name
        channel_avatar_text.text = channel.name

        channel_followers_count.text = channelDetails.followersCount.toString()
        channel_items_count.text = channelDetails.pinCount.toString()

        followersAdapter.submitList(channelDetails.followers)
    }

    private fun onFollowClick() {

    }

    override fun onResume() {
        super.onResume()

        general_appbar.setExpanded(false, false)
    }

    companion object {

        private const val KEY_FEED_TYPE = "feed_type"
        private const val KEY_URL = "url"
        private const val KEY_CHANNEL = "channel"

        fun newInstance(channel: Channel) = ChannelFragment().withArguments {
            putInt(KEY_FEED_TYPE, FeedType.CHANNEL.ordinal)
            putParcelable(KEY_CHANNEL, channel)
        }

    }

}