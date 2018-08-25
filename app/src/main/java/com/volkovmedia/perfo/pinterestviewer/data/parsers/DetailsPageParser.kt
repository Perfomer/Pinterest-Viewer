package com.volkovmedia.perfo.pinterestviewer.data.parsers

import com.volkovmedia.perfo.pinterestviewer.data.entity.Comment
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.data.entity.Tag
import com.volkovmedia.perfo.pinterestviewer.data.entity.User
import com.volkovmedia.perfo.pinterestviewer.data.parsers.base.PageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.extensions.parseTime
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class DetailsPageParser(): PageParser<FeedItemDetails> {

    override fun request(request: PageRequest): FeedItemDetails {
        val document = Jsoup.connect(request.url).get()

        val hostName = document.getElementsByAttribute("data-hostname")
        val author = document.getElementsByAttributeValue("itemprop", "author")[0]

        val outUrl = hostName.find { it.hasClass("btn") }!!.attr("href")
        val imageUrl = hostName.find { !it.hasClass("btn") }!!.attr("href")
        val time = document.select("time").attr("datetime").parseTime()
        val authorName = author.text()
        val authorUrl = author.parent().attr("href")
        val comments = document.comments
        val tags = document.tags
        val videoUrl = document.select("video").attr("src")

        return if (videoUrl.isNullOrBlank()) {
            FeedItemDetails.ImageDetails(outUrl, imageUrl, time, authorName, authorUrl, comments, tags)
        } else {
            FeedItemDetails.VideoDetails(outUrl, imageUrl, time, authorName, authorUrl, comments, tags, videoUrl)
        }
    }

    private companion object {

        private fun String.parseTime(): Long {
            return replace('T', ' ').parseTime("yyyy-MM-dd HH:mm:ssZ")
        }

        private val Element.tags: List<Tag>
            get() {
                val tags = getElementsByClass("tags")
                if (tags.isEmpty()) return listOf()

                return tags[0].children().map { tag -> Tag(tag.text(), tag.attr("href")) }
            }

        private val Document.comments
            get() = select("div")
                    .filter { it.hasClass("comments") }
                    .flatMap { it.select("div") }
                    .filter { it.hasClass("comment") }
                    .map {
                        val nameElement = it.select("a")

                        val avatar = it.select("img").attr("src")
                        val name = nameElement.text()
                        val url = nameElement.attr("href")

                        val text = it.getElementsByClass("text").text()

                        return@map Comment(text, User(name, avatar, url))
                    }
    }

}