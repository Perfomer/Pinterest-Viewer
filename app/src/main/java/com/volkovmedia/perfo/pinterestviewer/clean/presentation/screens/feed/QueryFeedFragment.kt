package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.volkovmedia.perfo.pinterestviewer.BuildConfig
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.adapter.FeedAdapter
import com.volkovmedia.perfo.pinterestviewer.di.PARAM_URL
import kotlinx.android.synthetic.main.feed_fragment.*
import org.koin.android.ext.android.get

open class QueryFeedFragment : FeedFragment() {

    override val layoutResource = R.layout.feed_fragment

    override val feedAdapter = FeedAdapter(::onItemClick).apply { setHasStableIds(true) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(KEY_URL) ?: BuildConfig.ROOT_URL
        provideViewModel(get(FeedType.QUERY.name) { mapOf(PARAM_URL to url) })

        if (url != BuildConfig.ROOT_URL) {
            viewModel.title.observe(this, Observer { general_toolbar.title = it })
        }
    }

    companion object {

        private const val KEY_URL = "url"

        fun newInstance(url: String) = QueryFeedFragment().withArguments {
            putString(KEY_URL, url)
        }

    }

}