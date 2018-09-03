package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.page

import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.page.PageRepository
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.requestPageSource

class NetworkPageDataSource : PageRepository {

    override fun getPageSource(url: String): RequestResult<String> {
        return try {
            RequestResult.Data(url.requestPageSource())
        } catch (ex: Exception) {
            RequestResult.Error(ex)
        }
    }

}