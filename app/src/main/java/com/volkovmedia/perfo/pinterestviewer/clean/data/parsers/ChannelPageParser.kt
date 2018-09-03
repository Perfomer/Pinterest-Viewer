package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.ChannelDetails
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.User
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.JsoupPageParser
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.correctRoot
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class ChannelPageParser : JsoupPageParser<ChannelDetails>() {

    override fun Document.parse(): ChannelDetails {
        val channelInfo = getElementsByClass("board_info_box")[0]

        val followersCount = channelInfo.extractIntValue("followerCount")
        val pinsCount = channelInfo.extractIntValue("pinCount")

        return ChannelDetails(followersCount, pinsCount, author, followers)
    }

    private companion object {

        private fun Element.extractIntValue(elementClass: String): Int {
            return getElementsByClass(elementClass)[0].text().substringBefore(' ').toInt()
        }

        private val Document.followers
            get() = getElementsByClass("followers_list")[0]
                    .select("a")
                    .map {
                        val avatar = it.select("img").attr("src").correctRoot()

                        val name = it.text()
                        val url = it.attr("href")
                        return@map User(name, avatar, url)
                    }

        private val Document.author: User
            get() {
                val author = getElementsByClass("created_by")[0]
                val authorLink = author.select("a")[1]

                val authorAvatar = author.select("img").attr("src").correctRoot()

                val authorName = authorLink.text()
                val authorUrl = authorLink.attr("href")

                return User(authorName, authorAvatar, authorUrl)
            }

    }

}