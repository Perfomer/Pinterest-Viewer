package com.volkovmedia.perfo.pinterestviewer.clean.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class FeedItem(val isVideo: Boolean): Parcelable {

    abstract val title: String
    abstract val imageUrl: String
    abstract val fullPageUrl: String
    abstract val likeCount: Int
    abstract val shareCount: Int
    abstract val commentCount: Int

    @Parcelize
    data class Image(override val title: String,
                     override val imageUrl: String,
                     override val fullPageUrl: String,
                     override val likeCount: Int,
                     override val shareCount: Int,
                     override val commentCount: Int): FeedItem(false)
    @Parcelize
    data class Video(override val title: String,
                     override val imageUrl: String,
                     override val fullPageUrl: String,
                     override val likeCount: Int,
                     override val shareCount: Int,
                     override val commentCount: Int,
                     val duration: Long,
                     val hd: Boolean): FeedItem(true)

}