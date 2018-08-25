package com.volkovmedia.perfo.pinterestviewer.data.parsers.base

data class PageRequest(val url: String,
                       val page: Int = 1,
                       val cookies: Map<String, String>? = null) {

    fun getPagedUrl() = "$url?page=$page"

}