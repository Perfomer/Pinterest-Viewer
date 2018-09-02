package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class JsoupPageParser<T> : PageParser<T> {

    override fun request(html: String): T {
        return Jsoup.parse(html).parse()
    }

    abstract fun Document.parse(): T

}