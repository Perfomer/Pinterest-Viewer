package com.volkovmedia.perfo.pinterestviewer.clean.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Channel(val name: String,
                   val url: String,
                   val thumbnailsUrl: String): Parcelable