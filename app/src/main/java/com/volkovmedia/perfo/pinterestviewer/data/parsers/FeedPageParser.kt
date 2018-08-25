package com.volkovmedia.perfo.pinterestviewer.data.parsers

import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.data.parsers.base.PageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.base.PageRequest
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.concurrent.TimeUnit

class FeedPageParser(): PageParser<List<FeedItem>> {

    override fun request(request: PageRequest) = Jsoup.connect(request.getPagedUrl())
            .get()
            .getElementsByClass("masonry_box small_pin_box")
            .map {
                val imageWrapper = it.getElementsByClass("image_wrapper")[0]
                val smallButtons = it.getElementsByClass("btn-sm")

                val sharesCount = smallButtons[0].extractSmallButtonValue()
                val likesCount = smallButtons[1].extractSmallButtonValue()
                val commentsCount = smallButtons[2].extractSmallButtonValue()
                val fullPageUrl = imageWrapper.attr("href")
                val title = imageWrapper.attr("title")
                val imageUrl = imageWrapper.select("img").attr("data-src")
                val duration = it.duration
                val hd = it.isHD

                return@map if (duration == -1L) {
                    FeedItem.Image(title, imageUrl, fullPageUrl, likesCount, sharesCount, commentsCount)
                } else {
                    FeedItem.Video(title, imageUrl, fullPageUrl, likesCount, sharesCount, commentsCount, duration, hd)
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

        private fun Element.extractSmallButtonValue(): Int {
            val likeValue = html()
            val lastChar = likeValue.indexOfLast { it == '>' } + 2
            return likeValue.substring(lastChar).toInt()
        }

    }

}