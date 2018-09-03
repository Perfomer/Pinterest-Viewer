package com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.page

import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult

interface PageRepository {

    fun getPageSource(url: String): RequestResult<String>

}