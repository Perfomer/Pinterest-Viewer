package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.adapter

import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.base.BaseViewHolder
import com.volkovmedia.perfo.pinterestviewer.utils.createRandomGrayColorDrawable
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.load
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedItemViewHolder(itemView: View, onClickListener: (Int) -> Unit) : BaseViewHolder(itemView) {

    private val image by lazy {
        itemView.feed_item_image.apply {
            setOnClickListener { onClickListener(adapterPosition) }
        }
    }

    fun bind(feedItem: FeedItem) {
        image.load(feedItem.imageUrl) {
            requestOptions = RequestOptions.placeholderOf(createRandomGrayColorDrawable())
        }
    }

}