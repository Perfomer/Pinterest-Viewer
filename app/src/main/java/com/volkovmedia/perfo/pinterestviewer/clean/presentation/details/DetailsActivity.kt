package com.volkovmedia.perfo.pinterestviewer.clean.presentation.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.robertlevonyan.views.chip.Chip
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.details.adapter.CommentsAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.photo.PhotoActivity
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.*
import kotlinx.android.synthetic.main.details_activity.*
import org.koin.android.architecture.ext.viewModel

class DetailsActivity : AppCompatActivity() {

    private val image by lazy { details_image }
    private val name by lazy { details_name }

    private val likesCount by lazy { details_likes_count }
    private val pinsCount by lazy { details_pins_count }

    private val tagsList by lazy { details_tags }

    private val progressBar by lazy { details_progressbar }

    private val commentsList by lazy {
        details_comments.apply {
            layoutManager = LinearLayoutManager(this@DetailsActivity)
            isNestedScrollingEnabled = false
        }
    }

    private val viewModel: DetailsViewModel by viewModel()

    private var awaitingToOpenFullImage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        details_back.setOnClickListener { onBackPressed() }

        with(viewModel) {
            image.setOnClickListener { openFullPhotoPage() }

            item.observe(this@DetailsActivity, Observer { it?.render() })
            details.observe(this@DetailsActivity, Observer { it?.render() })

            setItem(intent.getParcelableExtra(KEY_ITEM))
        }
    }

    private fun FeedItem.render() {
        progressBar.isVisible = true

        image.loadFromUrl(imageUrl)
        name.text = title
        likesCount.text = likeCount.toString()
        pinsCount.text = shareCount.toString()
    }

    private fun FeedItemDetails.render() {
        progressBar.isVisible = false
        details_details.isVisible = true
        details_date.text = uploadDate.toDateString()

        with(channel) {
            details_channel_name.text = name
            details_channel_thumbnail.loadCircleFromUrl(thumbnailsUrl)
        }

        val margin = 4f.toDp(this@DetailsActivity)

        tagsList.removeAllViews()

        tags.forEach { tag ->
            details_tags.addView(Chip(this@DetailsActivity).apply {
                chipText = tag.name
                layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    setMargins(0, margin, margin, margin)
                }
            })
        }

        if (comments.isNotEmpty()) {
            details_comments_wrap.isVisible = true
            commentsList.adapter = CommentsAdapter().apply {
                submitList(comments)
            }

            details_root.scrollTo(0, 0)
        }

        if (awaitingToOpenFullImage) {
            awaitingToOpenFullImage = false
            openFullPhotoPage()
        }
    }

    private fun openFullPhotoPage() = with(viewModel) {
        val fullImageUrl = details.value?.fullImageUrl
        val imageUrl = item.value?.imageUrl

        if (!imageUrl.isNullOrBlank() && !fullImageUrl.isNullOrBlank()) {
            PhotoActivity.startActivity(this@DetailsActivity, imageUrl!!, fullImageUrl!!)
        } else {
            awaitingToOpenFullImage = true
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