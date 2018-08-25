package com.volkovmedia.perfo.pinterestviewer.presentation.feed.adapter.pagination

import android.support.v7.util.DiffUtil
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItem

class PhotoDiffUtilCallback : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean = (oldItem === newItem)
    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean = (oldItem == newItem)
}