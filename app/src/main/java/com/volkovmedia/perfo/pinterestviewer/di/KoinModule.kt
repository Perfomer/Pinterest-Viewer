package com.volkovmedia.perfo.pinterestviewer.di

import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.NetworkPinterestDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.DetailsProvideInteractor
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.FeedDataProvideInteractor
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.PinterestRepository
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.details.DetailsViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.ChannelViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.FeedType
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.FeedViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.photo.PhotoViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val generalModule = applicationContext {
    bean { this }
    bean { NetworkPinterestDataSource() as PinterestRepository }
}

val detailsModule = applicationContext {
    viewModel { DetailsViewModel(get()) }
    bean { DetailsProvideInteractor(get()) }
}

val photoModule = applicationContext {
    viewModel { PhotoViewModel(get()) }
}

val feedModule = applicationContext {
    viewModel(FeedType.QUERY.name) { params -> FeedViewModel(get(), params[PARAM_URL]) }
    viewModel(FeedType.CHANNEL.name) { params -> ChannelViewModel(get(), params[PARAM_CHANNEL]) as FeedViewModel }
    bean { FeedDataProvideInteractor(get()) }
}

const val PARAM_URL = "url"
const val PARAM_CHANNEL = "channel"