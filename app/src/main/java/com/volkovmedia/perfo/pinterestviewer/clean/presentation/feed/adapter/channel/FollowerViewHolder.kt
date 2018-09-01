package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.channel

import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.User
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.base.BaseViewHolder
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.load
import kotlinx.android.synthetic.main.feed_item_channel_item_follower.view.*

class FollowerViewHolder(itemView: View) : BaseViewHolder(itemView) {

    private val avatar by lazy { itemView.follower_avatar }
    private val name by lazy { itemView.follower_name }

    fun bind(user: User) {
        avatar.load(user.avatarUrl) { requestOptions = RequestOptions.circleCropTransform() }
        name.text = user.name
    }

}