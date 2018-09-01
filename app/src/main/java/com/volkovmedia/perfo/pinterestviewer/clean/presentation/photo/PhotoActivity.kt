package com.volkovmedia.perfo.pinterestviewer.clean.presentation.photo

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.request.RequestOptions
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.load
import kotlinx.android.synthetic.main.photo_activity.*
import org.koin.android.architecture.ext.viewModel

class PhotoActivity : AppCompatActivity() {

    private val photoView by lazy { photo_image }

    private val viewModel: PhotoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_activity)

        val previewUrl = intent.getStringExtra(KEY_PREVIEW_URL)
        val fullUrl = intent.getStringExtra(KEY_FULL_URL)

        with(viewModel) {
            preview.observe(this@PhotoActivity, Observer { it?.render() })
            full.observe(this@PhotoActivity, Observer { it?.render() })

            setUrls(previewUrl, fullUrl)
        }
    }

    private fun Drawable.render() = photoView.load(this) { requestOptions = RequestOptions.centerInsideTransform() }

    companion object {

        private const val KEY_PREVIEW_URL = "preview_url"
        private const val KEY_FULL_URL = "full_url"

        fun startActivity(context: Context, previewUrl: String, fullUrl: String) {
            val intent = Intent(context, PhotoActivity::class.java).apply {
                putExtra(KEY_PREVIEW_URL, previewUrl)
                putExtra(KEY_FULL_URL, fullUrl)
            }

            context.startActivity(intent)
        }

    }

}