package com.volkovmedia.perfo.pinterestviewer.utils

import android.support.v7.util.DiffUtil

abstract class DiffUtilItemCallback<T>(private val oldItems: List<T>,
                                       private val newItems: List<T>)
    : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
    }

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

}