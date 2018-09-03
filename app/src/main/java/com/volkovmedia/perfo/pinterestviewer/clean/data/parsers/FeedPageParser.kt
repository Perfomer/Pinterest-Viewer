package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.JsoupPageParser
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.concurrent.TimeUnit

class FeedPageParser() : JsoupPageParser<List<FeedItem>>() {

    override fun Document.parse() = getElementsByClass("masonry_box small_pin_box")
            .map {
                val imageWrapper = it.getElementsByClass("image_wrapper")[0]
                val smallButtons = it.getElementsByClass("btn-sm")

                val sharesCount = smallButtons[0].smallButtonValue
                val likesCount = smallButtons[1].smallButtonValue
                val commentsCount = smallButtons[2].smallButtonValue
                val fullPageUrl = imageWrapper.attr("href")
                val title = imageWrapper.attr("title")
                val imageUrl = imageWrapper.select("img").attr("data-src")
                val duration = it.duration

                return@map if (duration == -1L) {
                    FeedItem.Image(title, imageUrl, fullPageUrl, likesCount, sharesCount, commentsCount)
                } else {
                    FeedItem.Video(title, imageUrl, fullPageUrl, likesCount, sharesCount, commentsCount, duration, it.isHD)
                }
            }

    companion object {

        const val PAGE_SIZE = 96

        private val Element.isHD: Boolean
            get() {
                select("span").filter { it.hasClass("hd") }.forEach { return true }
                return false
            }

        private val Element.duration: Long
            get() {
                val durationElement = getElementsByClass("duration")
                if (durationElement.isEmpty()) return -1

                val timeUnits = durationElement[0].text().split(':')
                return TimeUnit.MINUTES.toMillis(timeUnits[0].toLong()) + TimeUnit.SECONDS.toMillis(timeUnits[1].toLong())
            }

        private val Element.smallButtonValue: Int
            get() = with(html()) {
                val lastChar = indexOfLast { it == '>' } + 2
                return substring(lastChar).toInt()
            }

    }

}