package com.volkovmedia.perfo.pinterestviewer

import android.app.Application
import android.graphics.Color
import com.volkovmedia.perfo.pinterestviewer.di.*
import jp.wasabeef.takt.Seat
import jp.wasabeef.takt.Takt
import org.koin.android.ext.android.startKoin

class PinterestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(generalModule, navigationModule, detailsModule, photoModule, feedModule, categoriesModule))

//        if (BuildConfig.DEBUG) {
            Takt.stock(this)
                    .seat(Seat.BOTTOM_RIGHT)
                    .interval(250)
                    .color(Color.WHITE)
                    .size(14f)
                    .alpha(.5f)
//                    .showOverlaySetting(true)
                    .play();
//        }
    }

    override fun onTerminate() {
//        if (BuildConfig.DEBUG) {
            Takt.finish();
//        }

        super.onTerminate()
    }

}