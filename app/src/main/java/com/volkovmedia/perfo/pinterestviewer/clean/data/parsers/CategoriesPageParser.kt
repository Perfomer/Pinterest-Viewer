package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.JsoupPageParser
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.correctRoot
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class CategoriesPageParser : JsoupPageParser<List<Category>>() {

    override fun Document.parse(): List<Category> = getElementsByClass("category-item").map {
        val urls = it.getElementsByClass("category-links").select("a")

        val imageUrl = it.select("img").attr("src").correctRoot()
        val name = it.getElementsByClass("category-name").text().trim()

        val picsUrl = urls.extractUrl(0)
        val videosUrl = urls.extractUrl(1)
        val gifsUrl = urls.extractUrl(2)

        return@map Category(name, imageUrl, picsUrl, gifsUrl, videosUrl)
    }

    companion object {

        private fun Elements.extractUrl(position: Int): String {
            return this[position].attr("href").correctRoot()
        }

    }

}