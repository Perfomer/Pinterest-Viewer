package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers

import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.JsoupPageParser
import org.jsoup.nodes.Document

class TitlePageParser : JsoupPageParser<String>() {

    override fun Document.parse(): String {
        return getElementsByClass("home_title").select("h1").text()
    }

}