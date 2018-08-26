package com.volkovmedia.perfo.pinterestviewer.clean.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChannelDetails(val name: String,
                          val url: String,
                          val followersCount: Int,
                          val pinCount: Int,
                          val author: User,
                          val followers: List<User>): Parcelable