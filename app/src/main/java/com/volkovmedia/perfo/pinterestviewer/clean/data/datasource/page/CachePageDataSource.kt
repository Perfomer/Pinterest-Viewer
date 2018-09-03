package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.page

import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.MutableDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.page.MutablePageRepository

class CachePageDataSource: MutablePageRepository, MutableDataSource {

    private val pagesSources = mutableMapOf<String, String>()

    override fun putPageSource(url: String, data: RequestResult<String>) {
        pagesSources.putData(url, data)
    }

    override fun getPageSource(url: String): RequestResult<String> {
        return pagesSources.extractData(url)
    }

}