package com.volkovmedia.perfo.pinterestviewer

import android.app.Application
import com.volkovmedia.perfo.pinterestviewer.di.*
import org.koin.android.ext.android.startKoin

class PinterestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(generalModule, navigationModule, detailsModule, photoModule, feedModule, categoriesModule))
    }

}