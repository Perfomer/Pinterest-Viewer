package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.photo

import android.arch.lifecycle.Observer
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.base.BaseFragment
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.isVisible
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.load
import kotlinx.android.synthetic.main.photo_fragment.*
import org.koin.android.architecture.ext.viewModel

class PhotoFragment : NavigationFragment() {

    override val layoutResource = R.layout.photo_fragment

    private val viewModel: PhotoViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let { args ->
            val previewUrl = args.getString(KEY_PREVIEW_URL)
            val fullUrl = args.getString(KEY_FULL_URL)

            with(viewModel) {
                preview.observe(this@PhotoFragment, Observer { it?.render(true) })
                full.observe(this@PhotoFragment, Observer { it?.render(false) })

                setUrls(previewUrl, fullUrl)
            }
        }
    }

    private fun Drawable.render(showProgressBar: Boolean) {
        photo_loading.isVisible = showProgressBar
        photo_image.load(this) { requestOptions = RequestOptions.centerInsideTransform() }
    }

    companion object {

        private const val KEY_PREVIEW_URL = "preview_url"
        private const val KEY_FULL_URL = "full_url"

        fun newInstance(previewUrl: String, fullUrl: String) = PhotoFragment().withArguments {
            putString(KEY_PREVIEW_URL, previewUrl)
            putString(KEY_FULL_URL, fullUrl)
        }

    }

}