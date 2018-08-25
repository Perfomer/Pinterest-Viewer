package com.volkovmedia.perfo.pinterestviewer.presentation.feed

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.data.parsers.ChannelPageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.DetailsPageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.FeedPageParser
import com.volkovmedia.perfo.pinterestviewer.data.parsers.FeedPageParser.Companion.PAGE_SIZE
import com.volkovmedia.perfo.pinterestviewer.data.parsers.base.PageRequest
import com.volkovmedia.perfo.pinterestviewer.presentation.feed.adapter.ImagesAdapter
import com.volkovmedia.perfo.pinterestviewer.presentation.feed.adapter.pagination.FeedDataSourceFactory
import com.volkovmedia.perfo.pinterestviewer.presentation.feed.adapter.pagination.PhotoDiffUtilCallback
import com.volkovmedia.perfo.pinterestviewer.presentation.photo.PhotoActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val imagesAdapter = ImagesAdapter(PhotoDiffUtilCallback(), ::onImageClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(recycler) {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = imagesAdapter
        }

        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .build()

        val pagedList = LivePagedListBuilder<Int, FeedItem>(FeedDataSourceFactory(), config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()

        launch { DetailsPageParser().request(PageRequest("http://www.sex.com/pin/56471859-stormi/"))
            FeedPageParser().request(PageRequest("http://www.sex.com/user/boxlunch/heat-in-motion/"))
            ChannelPageParser().request(PageRequest("http://www.sex.com/user/boxlunch/heat-in-motion/"))
        /* http://www.sex.com/user/boxlunch/heat-in-motion/ */ }

        pagedList.observe(this, Observer { imagesAdapter.submitList(it) })
    }

    private fun onImageClick(model: FeedItem) {
        PhotoActivity.startActivity(this, model.imageUrl)
    }

}