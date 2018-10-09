package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseAdapter

class CategoriesAdapter(private val onClickListener: (Category) -> Unit)
    : BaseAdapter<CategoryViewHolder>() {

    private val categories = mutableListOf<Category>()

    override fun getItemCount() = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_item, parent, false)
        return CategoryViewHolder(view, ::onCategoryClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemId(position: Int): Long {
        return categories[position].name.hashCode().toLong()
    }

    fun addItems(items: List<Category>) {
        categories += items
        notifyDataSetChanged()
    }

    private fun onCategoryClick(position: Int) {
        onClickListener(categories[position])
    }

}