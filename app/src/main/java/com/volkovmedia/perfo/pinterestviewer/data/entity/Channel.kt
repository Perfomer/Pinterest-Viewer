package com.volkovmedia.perfo.pinterestviewer.data.entity

data class Channel(val name: String,
                   val url: String,
                   val followersCount: Int,
                   val pinCount: Int,
                   val author: User,
                   val followers: List<User>)