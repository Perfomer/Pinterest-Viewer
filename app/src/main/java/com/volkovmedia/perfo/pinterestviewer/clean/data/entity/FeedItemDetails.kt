package com.volkovmedia.perfo.pinterestviewer.clean.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class FeedItemDetails: Parcelable {

    abstract val outUrl: String?
    abstract val fullImageUrl: String
    abstract val uploadDate: Long
    abstract val channel: Channel
    abstract val comments: List<Comment>
    abstract val tags: List<Tag>

    @Parcelize
    data class ImageDetails(override val outUrl: String?,
                            override val fullImageUrl: String,
                            override val uploadDate: Long,
                            override val channel: Channel,
                            override val comments: List<Comment>,
                            override val tags: List<Tag>) : FeedItemDetails()

    @Parcelize
    data class VideoDetails(override val outUrl: String?,
                            override val fullImageUrl: String,
                            override val uploadDate: Long,
                            override val channel: Channel,
                            override val comments: List<Comment>,
                            override val tags: List<Tag>,
                            val videoUrl: String) : FeedItemDetails()


}