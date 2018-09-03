package com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.page

import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult

interface MutablePageRepository : PageRepository {

    fun putPageSource(url: String, data: RequestResult<String>)

}