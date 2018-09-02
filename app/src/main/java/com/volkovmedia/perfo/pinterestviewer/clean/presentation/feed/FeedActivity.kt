package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser.Companion.PAGE_SIZE
import com.volkovmedia.perfo.pinterestviewer.clean.domain.ROOT_URL
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.details.DetailsActivity
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.ImagesAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination.FeedDataSourceFactory
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination.PhotoDiffUtilCallback
import com.volkovmedia.perfo.pinterestviewer.di.PARAM_CHANNEL
import com.volkovmedia.perfo.pinterestviewer.di.PARAM_URL
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.isVisible
import kotlinx.android.synthetic.main.feed_activity.*
import kotlinx.android.synthetic.main.feed_activity_appbar.*
import kotlinx.android.synthetic.main.feed_activity_content.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.get
import java.util.concurrent.Executors

class FeedActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val loadingIndicator by lazy { feed_progressbar }
    private val drawerLayout by lazy { feed_drawer }
    private val toolBar by lazy { feed_toolbar }

    private val imagesAdapter = ImagesAdapter(PhotoDiffUtilCallback(), GridLayoutManager(this, 3), ::onImageClick, ::onFollowClick)

    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_activity)
        setSupportActionBar(toolBar)

        val feedTypeInt = intent.getIntExtra(KEY_FEED_TYPE, FeedType.QUERY.ordinal)
        val feedType = FeedType.values()[feedTypeInt]

        when (feedType) {
            FeedType.QUERY -> {
                val url = intent.getStringExtra(KEY_URL) ?: ROOT_URL
                viewModel = get(feedType.name) { mapOf(PARAM_URL to url) }
            }
            FeedType.CHANNEL -> {
                val channel = intent.getParcelableExtra<Channel>(KEY_CHANNEL)
                viewModel = get(feedType.name) { mapOf(PARAM_CHANNEL to channel) }
                val channelViewModel = viewModel as ChannelViewModel

                channelViewModel.channelDetails.observe(this, Observer { imagesAdapter.setChannelData(channel, it!!) })
            }
        }

        feed_list.adapter = imagesAdapter

        initDrawerLayout()
        initPagination()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun onInitialLoading(loading: Boolean) {
        launch(UI) { loadingIndicator.isVisible = loading }
    }

    private fun onRangeLoading(loading: Boolean) {
        launch(UI) { imagesAdapter.loading = loading }
    }

    private fun onImageClick(model: FeedItem) {
        DetailsActivity.startActivity(this, model)
    }

    private fun onFollowClick() {

    }

    private fun initPagination() {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .build()

        val dataSourceFactory = FeedDataSourceFactory(viewModel.url, ::onInitialLoading, ::onRangeLoading)

        val pagedList = LivePagedListBuilder<Int, FeedItem>(dataSourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()

        pagedList.observe(this, Observer { imagesAdapter.submitList(it) })
    }

    private fun initDrawerLayout() {
        with(ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)) {
            drawerLayout.addDrawerListener(this)
            syncState()
        }

        feed_navigation_view.setNavigationItemSelectedListener(this)
    }

    companion object {

        private const val KEY_FEED_TYPE = "feed_type"
        private const val KEY_URL = "url"
        private const val KEY_CHANNEL = "channel"

        fun startActivity(context: Context, url: String) = startActivity(context) {
            putExtra(KEY_FEED_TYPE, FeedType.QUERY.ordinal)
            putExtra(KEY_URL, url)
        }

        fun startActivity(context: Context, channel: Channel) = startActivity(context) {
            putExtra(KEY_FEED_TYPE, FeedType.CHANNEL.ordinal)
            putExtra(KEY_CHANNEL, channel)
        }

        private fun startActivity(context: Context, intentInit: Intent.() -> Unit) {
            val intent = Intent(context, FeedActivity::class.java)
            intentInit.invoke(intent)
            context.startActivity(intent)
        }

    }

}