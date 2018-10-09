package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.adapter

import android.support.v7.widget.RecyclerView.NO_POSITION
import android.view.View
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseViewHolder
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.load
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedItemViewHolder(itemView: View, onClickListener: (Int) -> Unit) : BaseViewHolder(itemView) {

    private val image by lazy {
        itemView.feed_item_image.apply {
            setOnClickListener {
                if (adapterPosition == NO_POSITION) return@setOnClickListener
                onClickListener(adapterPosition)
            }
        }
    }

    fun bind(item: FeedItem) {
        image.load(item.imageUrl, withPlaceHolder = true)
    }

}