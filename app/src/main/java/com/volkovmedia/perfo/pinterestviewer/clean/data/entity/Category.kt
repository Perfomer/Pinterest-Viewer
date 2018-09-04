package com.volkovmedia.perfo.pinterestviewer.clean.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(val name: String,
                    val imageUrl: String,
                    val picsUrl: String,
                    val gifsUrl: String,
                    val videosUrl: String) : Parcelable