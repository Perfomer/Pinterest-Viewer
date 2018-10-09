package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.channel

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseViewHolder
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.channel.followers.FollowersAdapter
import kotlinx.android.synthetic.main.channel_fragment.view.*
import kotlinx.android.synthetic.main.channel_header.view.*

class ChannelHeaderViewHolder(itemView: View,
                              onFollowClickListener: () -> Unit) : BaseViewHolder(itemView) {

    private val authorSubtitle by lazy { itemView.channel_author }

    private val channelName by lazy { itemView.channel_name }
    private val channelAvatarText by lazy { itemView.channel_avatar_text }

    private val followersCount by lazy { itemView.channel_followers_count }
    private val itemsCount by lazy { itemView.channel_items_count }

    private val followersAdapter by lazy { FollowersAdapter() }

    init {
        itemView.channel_followers.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = followersAdapter
        }

        itemView.channel_follow_button.setOnClickListener { onFollowClickListener() }
    }

    fun bind(channel: Channel, channelDetails: ChannelDetails) {
//        authorSubtitle.text = context.getString(R.string.channel_created_by, channelDetails.author.name).toSpan()
//
//        channelName.text = channel.name
//        channelAvatarText.text = channel.name

        followersCount.text = channelDetails.followersCount.toString()
        itemsCount.text = channelDetails.pinCount.toString()

        followersAdapter.submitList(channelDetails.followers)
    }

}