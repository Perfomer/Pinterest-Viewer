package com.volkovmedia.perfo.pinterestviewer.clean.presentation.categories.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.base.BaseViewHolder
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.GridLayoutManager
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category

class CategoriesAdapter(diffUtilCallback: CategoryDiffUtilCallback,
                        private val gridLayoutManager: GridLayoutManager,
                        private val onClickListener: (Category) -> Unit)
    : ListAdapter<Category, BaseViewHolder>(diffUtilCallback) {

    var loading = false
        set(value) {
            field = value

            val lastPosition = itemCount - 1

            if (value) notifyItemInserted(lastPosition + 1)
            else notifyItemRemoved(lastPosition)
        }

    init {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = if (isFooter(position)) {
                gridLayoutManager.spanCount
            } else {
                1
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = gridLayoutManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VT_LOADING_FOOTER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item_loading_footer, parent, false)
                BaseViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_item, parent, false)
                CategoryViewHolder(view, ::onCategoryClick)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder.itemViewType) {
            VT_FEED_ITEM -> {
                val categoryViewHolder = holder as CategoryViewHolder
                categoryViewHolder.bind(getItem(position)!!)
            }
        }
    }

    override fun getItemViewType(position: Int) = when {
        isFooter(position) -> VT_LOADING_FOOTER
        else -> VT_FEED_ITEM
    }

    private fun onCategoryClick(position: Int) {
        onClickListener(getItem(position)!!)
    }

    private fun isFooter(position: Int): Boolean {
        return loading && position == itemCount - 1
    }

    private companion object {

        private const val VT_FEED_ITEM = 100
        private const val VT_LOADING_FOOTER = 200

    }

}