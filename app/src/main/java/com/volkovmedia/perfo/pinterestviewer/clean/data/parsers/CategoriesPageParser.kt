package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers

import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageParser
import org.jsoup.nodes.Document

class CategoriesPageParser : PageParser<List<Pair<String, String>>>() {

    override fun Document.parse(): List<Pair<String, String>> {
        val navigation = getElementsByClass("categories clearfix")[0]
        return navigation.select("a").map { it.attr("href") to it.text() }
    }

}