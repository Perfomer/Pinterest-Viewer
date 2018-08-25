package com.volkovmedia.perfo.pinterestviewer.clean.presentation.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.robertlevonyan.views.chip.Chip
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.details.adapter.CommentsAdapter
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.loadFromUrl
import kotlinx.android.synthetic.main.details_activity.*

class DetailsActivity : AppCompatActivity() {

    private val image by lazy { details_image }
    private val name by lazy { details_name }

    private val likesCount by lazy { details_likes_count }
    private val pinsCount by lazy { details_pins_count }

    private val commentsList by lazy {
        details_comments.apply {
            layoutManager = LinearLayoutManager(this@DetailsActivity)
        }
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java).apply {
            item.observe(this@DetailsActivity, Observer { it?.render() })
            details.observe(this@DetailsActivity, Observer { it?.render() })

            setItem(intent.getParcelableExtra(KEY_ITEM))
        }

        details_back.setOnClickListener { onBackPressed() }
    }

    private fun FeedItem.render() {
        image.loadFromUrl(imageUrl)
        name.text = title
        likesCount.text = likeCount.toString()
        pinsCount.text = shareCount.toString()
    }

    private fun FeedItemDetails.render() {
        image.loadFromUrl(fullImageUrl)
        //todo set date
        details_channel_name.text = channelName

        tags.forEach { tag ->
            details_tags.addView(Chip(this@DetailsActivity).apply {
                chipText = tag.name
            })
        }

        commentsList.adapter = CommentsAdapter().apply {
            submitList(comments)
        }
    }

    companion object {

        private const val KEY_ITEM = "item_detailed"

        fun startActivity(context: Context, item: FeedItem) {
            val intent = Intent(context, DetailsActivity::class.java).apply {
                putExtra(KEY_ITEM, item)
            }

            context.startActivity(intent)
        }

    }

}