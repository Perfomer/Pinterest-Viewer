package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.view.View
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser.Companion.FEED_PAGE_SIZE
import com.volkovmedia.perfo.pinterestviewer.clean.domain.ROOT_URL
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.adapter.FeedAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.adapter.pagination.FeedDataSourceFactory
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.feed.adapter.pagination.FeedItemDiffUtilCallback
import com.volkovmedia.perfo.pinterestviewer.di.PARAM_URL
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.isVisible
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.general_list_content.*
import org.koin.android.ext.android.get
import java.util.concurrent.Executors

open class FeedFragment : NavigationFragment() {

    override val layoutResource = R.layout.feed_fragment

    private val feedAdapter = FeedAdapter(FeedItemDiffUtilCallback(), 3, ::onItemClick)

    protected lateinit var viewModel: FeedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpecificViews()

        general_list.adapter = feedAdapter
        initPagination()
    }

    protected open fun initSpecificViews() {
        val url = arguments?.getString(KEY_URL) ?: ROOT_URL
        viewModel = get(FeedType.QUERY.name) { mapOf(PARAM_URL to url) }

        if (url != ROOT_URL) {
            viewModel.title.observe(this, Observer { general_toolbar.title = it })
        }
    }

    private fun onInitialLoading(loading: Boolean) {
        general_progressbar.isVisible = loading
    }

    private fun onRangeLoading(loading: Boolean) {
        feedAdapter.loading = loading
    }

    private fun onItemClick(model: FeedItem) {
        navigationManager.toFeedItemDetails(model)
    }

    private fun initPagination() {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(FEED_PAGE_SIZE)
                .setInitialLoadSizeHint(FEED_PAGE_SIZE)
                .build()

        val dataSourceFactory = FeedDataSourceFactory(viewModel.url, ::onInitialLoading, ::onRangeLoading)

        val pagedList = LivePagedListBuilder<Int, FeedItem>(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()

        pagedList.observe(this, Observer { feedAdapter.submitList(it) })
    }


    companion object {

        private const val KEY_URL = "url"

        fun newInstance(url: String) = FeedFragment().withArguments {
            putString(KEY_URL, url)
        }

    }

}