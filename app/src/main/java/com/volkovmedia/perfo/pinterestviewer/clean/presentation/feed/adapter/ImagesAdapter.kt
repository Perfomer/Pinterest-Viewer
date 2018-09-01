package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.base.BaseViewHolder
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.channel.ChannelHeaderViewHolder
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination.PhotoDiffUtilCallback
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.GridLayoutManager

class ImagesAdapter(diffUtilCallback: PhotoDiffUtilCallback,
                    private val gridLayoutManager: GridLayoutManager,
                    private val onClickListener: (FeedItem) -> Unit,
                    private val onFollowClickListener: (() -> Unit)? = null)
    : PagedListAdapter<FeedItem, BaseViewHolder>(diffUtilCallback) {

    private lateinit var channel: Channel
    private lateinit var channelDetails: ChannelDetails

    init {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (isChannel(position)) {
                    gridLayoutManager.spanCount
                } else {
                    1
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = gridLayoutManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VT_CHANNEL_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item_channel_header, parent, false)
                ChannelHeaderViewHolder(view, onFollowClickListener!!)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
                ImageViewHolder(view, ::onImageClick)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isChannel(position)) {
            val channelHeader = holder as ChannelHeaderViewHolder
            channelHeader.bind(channel, channelDetails)
        } else {
            val imageViewHolder = holder as ImageViewHolder
            imageViewHolder.bind(getItem(position)!!)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isChannel(position)) VT_CHANNEL_HEADER
        else VT_FEED_ITEM
    }

    fun setChannelData(channel: Channel, channelDetails: ChannelDetails) {
        this.channel = channel
        this.channelDetails = channelDetails
        notifyDataSetChanged()
    }

    private fun onImageClick(position: Int) {
        onClickListener(getItem(position)!!)
    }

    private fun isChannel(position: Int): Boolean {
        return ::channel.isInitialized && position == 0
    }

    private companion object {

        private const val VT_CHANNEL_HEADER = 150
        private const val VT_FEED_ITEM = 100

    }

}