package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.adapter

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.utils.DiffUtilItemCallback

class FeedItemDiffUtilCallback(oldItems: List<FeedItem>, newItems: List<FeedItem>)
    : DiffUtilItemCallback<FeedItem>(oldItems, newItems) {

    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean = (oldItem.imageUrl == newItem.imageUrl)
    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean = (oldItem == newItem)

}