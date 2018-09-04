package com.volkovmedia.perfo.pinterestviewer.clean.presentation.categories.adapter

import android.support.v7.widget.RecyclerView.NO_POSITION
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.base.BaseViewHolder
import com.volkovmedia.perfo.pinterestviewer.utils.createRandomGrayColorDrawable
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.load
import kotlinx.android.synthetic.main.categories_item.view.*

class CategoryViewHolder(itemView: View, private val onClickListener: (Int) -> Unit) : BaseViewHolder(itemView) {

    init {
        itemView.setOnClickListener { onClick() }
    }

    private val image by lazy {
        itemView.categories_item_image.apply {
            setOnClickListener { onClick() }
        }
    }

    private val name by lazy {
        itemView.categories_item_name
    }

    fun bind(category: Category) {
        image.load(category.imageUrl) {
            requestOptions = RequestOptions.placeholderOf(createRandomGrayColorDrawable())
        }

        name.text = category.name
    }

    private fun onClick() {
        val position = adapterPosition

        if (position != NO_POSITION) {
            onClickListener(position)
        }
    }

}