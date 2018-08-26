package com.volkovmedia.perfo.pinterestviewer.clean.presentation.details.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Comment
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.loadCircleFromUrl
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.loadFromUrl
import kotlinx.android.synthetic.main.details_item_comment.view.*

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val authorAvatar by lazy { itemView.details_item_comment_author_avatar }

    private val authorName by lazy { itemView.details_item_comment_author_name }

    private val text by lazy { itemView.details_item_comment_text }

    fun bind(item: Comment) {
        with(item.author) {
            authorAvatar.loadCircleFromUrl(avatarUrl)
            authorName.text = name
        }

        text.text = item.text
    }

}