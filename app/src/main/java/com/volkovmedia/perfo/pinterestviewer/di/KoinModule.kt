package com.volkovmedia.perfo.pinterestviewer.di

import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.page.CachePageDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.page.GeneralPageDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.page.NetworkPageDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.pinterest.CachePinterestDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.pinterest.GeneralPinterestDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.pinterest.NetworkPinterestDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.*
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.CategoriesProvideInteractor
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.DetailsProvideInteractor
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.FeedDataProvideInteractor
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.page.PageRepository
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.PinterestRepository
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationManager
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationManagerImpl
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.categories.CategoriesViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.details.DetailsViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.channel.ChannelViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.FeedType
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.FeedViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.photo.PhotoViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

typealias CiceroneRouter = Cicerone<Router>

const val PARAM_URL = "url"
const val PARAM_CHANNEL = "channel"

val generalModule = applicationContext {
    bean { this }

    bean { GeneralPinterestDataSource(get(), get()) as PinterestRepository }
    bean { NetworkPinterestDataSource(get(), get(), get(), get(), get(), get()) }
    bean { CachePinterestDataSource() }

    bean { GeneralPageDataSource(get(), get()) as PageRepository }
    bean { CachePageDataSource() }
    bean { NetworkPageDataSource() }

    bean { FeedPageParser() }
    bean { CategoriesPageParser() }
    bean { DetailsPageParser() }
    bean { ChannelPageParser() }
    bean { TitlePageParser() }
}

val navigationModule = applicationContext {
    bean { Cicerone.create() }
    bean { get<CiceroneRouter>().router }
    bean { get<CiceroneRouter>().navigatorHolder }
    bean { NavigationManagerImpl(get()) as NavigationManager }
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

val categoriesModule = applicationContext {
    viewModel { CategoriesViewModel(get()) }
    bean { CategoriesProvideInteractor(get()) }
}