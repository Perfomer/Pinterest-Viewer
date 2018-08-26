package com.volkovmedia.perfo.pinterestviewer.di

import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.GlideImageDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.data.datasource.NetworkPinterestDataSource
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.DetailsProvideInteractor
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.ImageProvideInteractor
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.ImageRepository
import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.PinterestRepository
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.details.DetailsViewModel
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.photo.PhotoViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val generalModule = applicationContext {
    bean { this }
    bean { NetworkPinterestDataSource() as PinterestRepository }
    bean { GlideImageDataSource(get()) as ImageRepository }
}

val detailsModule = applicationContext {
    viewModel { DetailsViewModel(get()) }
    bean { DetailsProvideInteractor(get()) }
}

val photoModule = applicationContext {
    viewModel { PhotoViewModel(get()) }
    bean { ImageProvideInteractor(get()) }
}