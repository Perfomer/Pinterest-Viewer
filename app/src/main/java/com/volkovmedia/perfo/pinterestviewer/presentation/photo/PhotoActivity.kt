package com.volkovmedia.perfo.pinterestviewer.presentation.photo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import com.klinker.android.sliding.MultiShrinkScroller
import com.klinker.android.sliding.SlidingActivity
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.extensions.loadFromUrl
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : SlidingActivity() {

    private val photoView by lazy { fullitem_image }

    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_photo)
        disableHeader()
        enableFullscreen()

        val photoUrl = intent.getStringExtra(KEY_PHOTO_URL)
        photoView.loadFromUrl(photoUrl)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        expandFromPoints(
                0,
                0,
                displayMetrics.widthPixels,
                displayMetrics.heightPixels)
    }

    override fun configureScroller(scroller: MultiShrinkScroller) {
        super.configureScroller(scroller);
        scroller.intermediateHeaderHeightRatio = 1f;
    }

    companion object {

        private const val KEY_PHOTO_URL = "photo_url"

        fun startActivity(context: Context, photoUrl: String) {
            val intent = Intent(context, PhotoActivity::class.java)
            intent.putExtra(KEY_PHOTO_URL, photoUrl)
            context.startActivity(intent)
        }

    }

}