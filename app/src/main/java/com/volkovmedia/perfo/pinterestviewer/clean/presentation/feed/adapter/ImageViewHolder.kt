package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.base.BaseViewHolder
import com.volkovmedia.perfo.pinterestviewer.utils.createRandomGrayColorDrawable
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.load
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.loadImage

class ImageViewHolder(itemView: View, onClickListener: (Int) -> Unit) : BaseViewHolder(itemView) {

    private val image by lazy {
        itemView.findViewById<ImageView>(R.id.image).apply {
            setOnClickListener { onClickListener(adapterPosition) }
        }
    }

    fun bind(feedItem: FeedItem) {
        image.load(feedItem.imageUrl) {
            requestOptions = RequestOptions.placeholderOf(createRandomGrayColorDrawable())
        }
    }

}