package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected val context: Context
        get() = itemView.context

}