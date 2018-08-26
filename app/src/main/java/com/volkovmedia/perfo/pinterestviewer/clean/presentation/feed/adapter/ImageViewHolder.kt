package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.loadFromUrl

class ImageViewHolder(itemView: View, onClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

    private val image by lazy {
        itemView.findViewById<ImageView>(R.id.image).apply {
            setOnClickListener { onClickListener(adapterPosition) }
        }
    }

    fun bind(feedItem: FeedItem) {
        image.loadFromUrl(feedItem.imageUrl)
    }

}