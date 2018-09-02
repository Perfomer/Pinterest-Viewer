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

    var loading = false
        set(value) {
            field = value

            val lastPosition = itemCount - 1

            if (value) notifyItemInserted(lastPosition)
            else notifyItemRemoved(lastPosition)
        }

    init {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (isHeader(position) || isFooter(position)) {
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

    override fun getItemCount(): Int {
        var add = 0

        if (::channel.isInitialized) add++
        if (loading) add++

        return super.getItemCount() + add
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VT_CHANNEL_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item_channel_header, parent, false)
                ChannelHeaderViewHolder(view, onFollowClickListener!!)
            }
            VT_LOADING_FOOTER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item_loading_footer, parent, false)
                BaseViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
                ImageViewHolder(view, ::onImageClick)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            VT_CHANNEL_HEADER -> {
                val channelHeader = holder as ChannelHeaderViewHolder
                channelHeader.bind(channel, channelDetails)
            }
            VT_FEED_ITEM -> {
                val imageViewHolder = holder as ImageViewHolder
                imageViewHolder.bind(getItem(position)!!)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeader(position)) VT_CHANNEL_HEADER
        else if (isFooter(position)) VT_LOADING_FOOTER
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

    private fun isHeader(position: Int): Boolean {
        return ::channel.isInitialized && position == 0
    }

    private fun isFooter(position: Int): Boolean {
        return loading && position == itemCount - 1
    }

    private companion object {

        private const val VT_FEED_ITEM = 100
        private const val VT_CHANNEL_HEADER = 150
        private const val VT_LOADING_FOOTER = 200

    }

}