package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base

import android.support.v7.widget.RecyclerView

abstract class BaseAdapter<VH: BaseViewHolder> : RecyclerView.Adapter<VH>() {

    protected lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

}