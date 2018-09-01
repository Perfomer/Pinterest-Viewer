package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.channel

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.User

class FollowersAdapter() : ListAdapter<User, FollowerViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item_channel_item_follower, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffUtilCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.userUrl == newItem.userUrl

        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem

    }

}