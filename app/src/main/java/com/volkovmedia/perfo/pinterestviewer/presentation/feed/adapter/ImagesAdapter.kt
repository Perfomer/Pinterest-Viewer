package com.volkovmedia.perfo.pinterestviewer.presentation.feed.adapter

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.presentation.feed.adapter.pagination.PhotoDiffUtilCallback

class ImagesAdapter(diffUtilCallback: PhotoDiffUtilCallback,
                    private val onClickListener: (FeedItem) -> Unit)
    : PagedListAdapter<FeedItem, ImageViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_image, parent, false)
        return ImageViewHolder(view, ::onImageClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    private fun onImageClick(position: Int) {
        onClickListener(getItem(position)!!)
    }

}
