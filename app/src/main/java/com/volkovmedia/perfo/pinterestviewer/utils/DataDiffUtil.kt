package com.volkovmedia.perfo.pinterestviewer.utils

import android.support.v7.util.DiffUtil

class DataDiffUtil<T>(): DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

}