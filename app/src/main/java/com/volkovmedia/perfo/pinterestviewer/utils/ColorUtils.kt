package com.volkovmedia.perfo.pinterestviewer.utils

import android.graphics.drawable.ColorDrawable
import java.util.*

private val random = Random()

fun createRandomGrayColorDrawable(): ColorDrawable {
    val seed = random.nextInt(255)
    return ColorDrawable(seed * 0x100000 + seed * 0x1000 + seed * 0x10)
}