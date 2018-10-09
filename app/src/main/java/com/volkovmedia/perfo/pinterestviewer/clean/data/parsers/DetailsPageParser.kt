package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.*
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.JsoupPageParser
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.correctRoot
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.parseTime
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class DetailsPageParser : JsoupPageParser<FeedItemDetails>() {

    override fun Document.parse(): FeedItemDetails {
        val hostName = getElementsByClass("image_frame")
        val channel = getElementsByClass("title").select("a")[0]

        val outUrl = hostName.select("a")?.attr("href")
        val imageUrl = hostName.select("img")!!.attr("src")
        val time = select("time").attr("datetime").parseTime()
        val channelName = channel.text()
        val channelUrl = channel.attr("href").correctRoot()
        val videoUrl = select("video").attr("src")
        val thumbnail = getElementsByClass("big_thumbnail")[0].select("img").attr("src")

        return if (videoUrl.isNullOrBlank()) {
            FeedItemDetails.ImageDetails(outUrl, imageUrl, time, Channel(channelName, channelUrl, thumbnail), comments, tags)
        } else {
            FeedItemDetails.VideoDetails(outUrl, imageUrl, time, Channel(channelName, channelUrl, thumbnail), comments, tags, videoUrl)
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

                return tags[0].children().map { tag -> Tag(tag.text(), tag.attr("href").correctRoot()) }
            }

        private val Document.comments
            get() = select("div")
                    .filter { it.hasClass("comments") }
                    .flatMap { it.select("div") }
                    .asSequence()
                    .filter { it.hasClass("comment") }
                    .map {
                        val nameElement = it.select("a")

                        val avatar = it.select("img").attr("src").correctRoot()

                        val name = nameElement.text()
                        val url = nameElement.attr("href")

                        val text = it.getElementsByClass("text")
                                .html()
                                .substringAfter("<br>")
                                .trim()

                        return@map Comment(text, User(name, avatar, url))
                    }
                    .toList()
    }

}