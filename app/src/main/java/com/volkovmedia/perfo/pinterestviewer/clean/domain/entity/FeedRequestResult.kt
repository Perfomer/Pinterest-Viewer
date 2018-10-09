package com.volkovmedia.perfo.pinterestviewer.clean.domain.entity

import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem

data class FeedRequestResult(val payload: List<FeedItem>,
                             val page: Int)