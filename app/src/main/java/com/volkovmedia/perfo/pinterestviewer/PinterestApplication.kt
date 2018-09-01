package com.volkovmedia.perfo.pinterestviewer

import android.app.Application
import com.volkovmedia.perfo.pinterestviewer.di.detailsModule
import com.volkovmedia.perfo.pinterestviewer.di.feedModule
import com.volkovmedia.perfo.pinterestviewer.di.generalModule
import com.volkovmedia.perfo.pinterestviewer.di.photoModule
import org.koin.android.ext.android.startKoin

class PinterestApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(generalModule, detailsModule, photoModule, feedModule))
    }

}