package com.volkovmedia.perfo.pinterestviewer.clean.data.datasource

import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult

interface MutableDataSource {

    fun <T> MutableMap<String, T>.putData(url: String, requestResult: RequestResult<T>) {
        when (requestResult) {
            is RequestResult.Data -> this[url] = requestResult.data
        }
    }

    fun <T> Map<String, T>.extractData(url: String): RequestResult<T> {
        val data = this[url]

        return if (data == null) RequestResult.Error(KotlinNullPointerException())
        else RequestResult.Data(data)
    }

}