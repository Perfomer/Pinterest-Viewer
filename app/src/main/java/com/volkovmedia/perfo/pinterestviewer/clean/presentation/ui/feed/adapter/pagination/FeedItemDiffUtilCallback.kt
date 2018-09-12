package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.adapter.pagination

import android.support.v7.util.DiffUtil
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem

class FeedItemDiffUtilCallback : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean = (oldItem.imageUrl == newItem.imageUrl)
    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean = (oldItem == newItem)
}