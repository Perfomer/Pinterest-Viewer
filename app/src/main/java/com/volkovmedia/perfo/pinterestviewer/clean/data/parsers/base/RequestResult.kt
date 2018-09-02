package com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base

sealed class RequestResult<T> {

    data class Data<T>(val data: T) : RequestResult<T>()

    data class Error<T>(val throwable: Throwable) : RequestResult<T>()

}