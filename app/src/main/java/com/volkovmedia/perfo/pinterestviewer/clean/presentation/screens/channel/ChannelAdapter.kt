package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.channel

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.adapter.FeedAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseViewHolder
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.adapter.FeedAdapter.UpdateCallback

class ChannelAdapter(onClickListener: (FeedItem) -> Unit,
                     private val onFollowClickListener: () -> Unit) : FeedAdapter(onClickListener) {

    private lateinit var channel: Channel
    private lateinit var channelDetails: ChannelDetails

    private val isChannelSet
        get() = ::channel.isInitialized

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        with(recyclerView.layoutManager) { (this as? GridLayoutManager)?.setSizeLookup() }
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == VT_CHANNEL_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.channel_header, parent, false)
            ChannelHeaderViewHolder(view, onFollowClickListener)
        } else {
            super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.itemViewType == VT_CHANNEL_HEADER) {
            holder as ChannelHeaderViewHolder
            holder.bind(channel, channelDetails)
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return if (isHeader(position)) -1000L else super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeader(position)) VT_CHANNEL_HEADER
        else super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        var count = super.getItemCount()
        if (isChannelSet) count++
        return count
    }

    override fun getRealPosition(position: Int): Int {
        var realPosition = super.getRealPosition(position)

        if (isChannelSet) realPosition--

        return realPosition
    }

    fun setChannel(channel: Channel, channelDetails: ChannelDetails) {
        this.channel = channel
        this.channelDetails = channelDetails
        notifyItemInserted(0)
    }

    private fun isHeader(position: Int) = position == 0 && isChannelSet

    private fun GridLayoutManager.setSizeLookup() {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (isHeader(position)) spanCount
                else 1
            }
        }
    }

    companion object {

        private const val VT_CHANNEL_HEADER = 101

    }

}