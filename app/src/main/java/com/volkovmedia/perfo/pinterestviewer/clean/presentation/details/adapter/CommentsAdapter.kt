package com.volkovmedia.perfo.pinterestviewer.clean.presentation.details.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Comment
import com.volkovmedia.perfo.pinterestviewer.utils.DataDiffUtil

class CommentsAdapter: ListAdapter<Comment, CommentViewHolder>(DataDiffUtil<Comment>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.details_item_comment, parent, false)
        return CommentViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}