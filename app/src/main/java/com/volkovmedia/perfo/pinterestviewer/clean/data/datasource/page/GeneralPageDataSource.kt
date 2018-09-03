package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.page

import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.page.PageRepository

class GeneralPageDataSource(private val cache: CachePageDataSource,
                            private val network: NetworkPageDataSource) : PageRepository {

    override fun getPageSource(url: String): RequestResult<String> {
        val cachedData = cache.getPageSource(url)

        return when (cachedData) {
            is RequestResult.Data -> cachedData
            is RequestResult.Error -> network.getPageSource(url).apply { cache.putPageSource(url, this) }
        }
    }

}