package com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Channel
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.CategoriesPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.DetailsPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.FeedPageParser.Companion.PAGE_SIZE
import com.volkovmedia.perfo.pinterestviewer.clean.domain.ROOT_URL
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.details.DetailsActivity
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.ImagesAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination.FeedDataSourceFactory
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.adapter.pagination.PhotoDiffUtilCallback
import com.volkovmedia.perfo.pinterestviewer.di.PARAM_CHANNEL
import com.volkovmedia.perfo.pinterestviewer.di.PARAM_URL
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.requestDocument
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.requestPageSource
import kotlinx.android.synthetic.main.feed_activity.*
import kotlinx.coroutines.experimental.launch
import org.jsoup.nodes.Document
import org.koin.android.ext.android.get
import java.util.concurrent.Executors

class FeedActivity : AppCompatActivity() {

    private val imagesAdapter = ImagesAdapter(PhotoDiffUtilCallback(), GridLayoutManager(this, 3), ::onImageClick)

    private lateinit var viewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_activity)

        val feedTypeInt = intent.getIntExtra(KEY_FEED_TYPE, FeedType.QUERY.ordinal)
        val feedType = FeedType.values()[feedTypeInt]

        when(feedType) {
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

        recycler.adapter = imagesAdapter

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .build()

        val pagedList = LivePagedListBuilder<Int, FeedItem>(FeedDataSourceFactory(viewModel.url), config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()

        launch {
            val a = CategoriesPageParser().request("http://www.sex.com/user/boxlunch/heat-in-motion/".requestPageSource())
            val i = 0
//            FeedPageParser().request("http://www.sex.com/user/boxlunch/heat-in-motion/".requestDocument())
//            ChannelPageParser().request("http://www.sex.com/user/boxlunch/heat-in-motion/".requestDocument())
            /* http://www.sex.com/user/boxlunch/heat-in-motion/ */
        }

        pagedList.observe(this, Observer { imagesAdapter.submitList(it) })
    }

    private fun onImageClick(model: FeedItem) {
        DetailsActivity.startActivity(this, model)
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