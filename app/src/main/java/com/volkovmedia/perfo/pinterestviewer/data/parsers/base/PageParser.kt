package com.volkovmedia.perfo.pinterestviewer.data.parsers.base

interface PageParser<T> {
    fun request(request: PageRequest): T
}