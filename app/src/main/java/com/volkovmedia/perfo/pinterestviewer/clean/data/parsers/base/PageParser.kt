package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base

interface PageParser<T> {
    fun request(html: String): RequestResult<T>
}