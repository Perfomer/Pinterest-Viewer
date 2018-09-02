package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class JsoupPageParser<T> : PageParser<T> {

    override fun request(html: String): RequestResult<T> {
        return try {
            RequestResult.Data(Jsoup.parse(html).parse())
        } catch (ex: Exception) {
            RequestResult.Error(ex)
        }
    }

    abstract fun Document.parse(): T

}