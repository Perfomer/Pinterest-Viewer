package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.app.Activity
import android.view.Window
import android.view.WindowManager
import com.jaeger.library.StatusBarUtil

private const val statusBarFlag = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

fun Activity.showStatusBar(show: Boolean) {
//    if (!show) StatusBarUtil.setTransparent(this)
//
    window?.showStatusBar(show)
}

fun Window.showStatusBar(show: Boolean) {
    if (show) clearFlags(statusBarFlag)
    else addFlags(statusBarFlag)
}
