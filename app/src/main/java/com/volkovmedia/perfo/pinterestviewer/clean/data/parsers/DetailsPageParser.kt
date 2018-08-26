package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.*
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.clean.domain.ROOT_URL
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.parseTime
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class DetailsPageParser(): PageParser<FeedItemDetails> {

    override fun request(request: PageRequest): FeedItemDetails {
        val document = Jsoup.connect(ROOT_URL.dropLast(1) + request.url).get()

        val hostName = document.getElementsByClass("image_frame")
        val author = document.getElementsByAttributeValue("itemprop", "author")[0]

        val outUrl = hostName.select("a")?.attr("href")
        val imageUrl = hostName.select("img")!!.attr("src")
        val time = document.select("time").attr("datetime").parseTime()
        val authorName = author.text()
        val authorUrl = author.parent().attr("href")
        val comments = document.comments
        val tags = document.tags
        val videoUrl = document.select("video").attr("src")
        val thumbnail = document.getElementsByClass("big_thumbnail")[0].select("img").attr("src")

        return if (videoUrl.isNullOrBlank()) {
            FeedItemDetails.ImageDetails(outUrl, imageUrl, time, Channel(authorName, authorUrl, thumbnail), comments, tags)
        } else {
            FeedItemDetails.VideoDetails(outUrl, imageUrl, time, Channel(authorName, authorUrl, thumbnail), comments, tags, videoUrl)
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

                        val avatar = with(it.select("img").attr("src")) {
                            if (!startsWith("http")) ROOT_URL.dropLast(1) + this
                            else this
                        }
                        val name = nameElement.text()
                        val url = nameElement.attr("href")

                        val text = it.getElementsByClass("text")
                                .html()
                                .substringAfter("<br>")
                                .trim()

                        return@map Comment(text, User(name, avatar, url))
                    }
    }

}