package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.omega_r.libs.omegarecyclerview.pagination.OnPageRequestListener
import com.paginate.Paginate
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.adapter.FeedAdapter
import kotlinx.android.synthetic.main.general_list_content.*

abstract class FeedFragment : NavigationFragment() {

    override val layoutResource = R.layout.feed_fragment

    protected abstract val feedAdapter: FeedAdapter

    protected lateinit var viewModel: FeedViewModel
        private set

    private val paginationCallback = PaginationCallback()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        general_list.setPaginationCallback(paginationCallback)

        general_list.apply {
            layoutManager = GridLayoutManager(context!!, 3)
            adapter = feedAdapter
        }
    }

    protected fun provideViewModel(viewModel: FeedViewModel) {
        if (!this::viewModel.isInitialized) {
            this.viewModel = viewModel
        }

        viewModel.items.observe(this, Observer { onItemsUpdated(it!!) })
        viewModel.hasLoadedAllItems.observe(this, Observer { onLoadedAllItems(it!!) })

        general_list.showProgressPagination()
    }

    protected fun onItemClick(model: FeedItem) {
        navigationManager.toFeedItemDetails(model)
    }

    private fun onItemsUpdated(items: List<FeedItem>) {
        feedAdapter.items = items
    }

    private fun onLoadedAllItems(loaded: Boolean) {
        if (loaded) general_list.hidePagination()
    }

    private inner class PaginationCallback : OnPageRequestListener {

        override fun onPageRequest(page: Int) {
            viewModel.loadMoreItems()
        }

        override fun getPagePreventionForEnd() = 60

    }

}