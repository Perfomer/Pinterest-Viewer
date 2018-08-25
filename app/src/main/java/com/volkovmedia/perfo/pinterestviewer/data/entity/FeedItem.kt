package com.volkovmedia.perfo.pinterestviewer.data.entity

sealed class FeedItem {

    abstract val title: String
    abstract val imageUrl: String
    abstract val fullPageUrl: String
    abstract val likeCount: Int
    abstract val shareCount: Int
    abstract val commentCount: Int

    data class Image(override val title: String,
                     override val imageUrl: String,
                     override val fullPageUrl: String,
                     override val likeCount: Int,
                     override val shareCount: Int,
                     override val commentCount: Int): FeedItem()

    data class Video(override val title: String,
                     override val imageUrl: String,
                     override val fullPageUrl: String,
                     override val likeCount: Int,
                     override val shareCount: Int,
                     override val commentCount: Int,
                     val duration: Long,
                     val hd: Boolean): FeedItem()

}