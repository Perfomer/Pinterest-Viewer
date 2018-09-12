package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.details

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.robertlevonyan.views.chip.Chip
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItemDetails
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.details.comments.CommentsAdapter
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.*
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.details_fragment_content.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.koin.android.architecture.ext.viewModel

class DetailsFragment : NavigationFragment() {

    override val layoutResource = R.layout.details_fragment

    private val viewModel: DetailsViewModel by viewModel()

    private var awaitingToOpenFullImage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setItem(arguments!!.getParcelable(KEY_ITEM))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(details_comments) {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
        }

        with(viewModel) {
            details_image.setOnClickListener { openFullPhotoPage() }

            item.observe(this@DetailsFragment, Observer { it?.render() })
            details.observe(this@DetailsFragment, Observer { it?.render() })
        }
    }

    private fun FeedItem.render() {
        details_progressbar.isVisible = true

        details_image.load(imageUrl)
        details_name.text = title
        details_likes_count.text = likeCount.toString()
        details_pins_count.text = shareCount.toString()
    }

    private fun FeedItemDetails.render() {
        details_progressbar.isVisible = false

        val fullImage = async {
            fullImageUrl.loadImage(this@DetailsFragment.context!!) { requestOptions = RequestOptions.centerInsideTransform() }
        }

        launch(UI) { details_image.load(fullImage.await()) }

        details_date.text = uploadDate.toDateString()

        with(channel) {
            details_channel_name.text = name

            details_channel_thumbnail.load(thumbnailsUrl) {
                requestOptions = RequestOptions.circleCropTransform()
            }

            details_channel.setOnClickListener {
                navigationManager.toChannel(this)
            }
        }

        val margin = 4f.toDp(context!!)

        details_tags.removeAllViews()

        tags.forEach { tag ->
            details_tags.addView(Chip(context!!).apply {
                chipText = tag.name
                layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    setMargins(0, margin, margin, margin)
                }

                setOnChipClickListener { navigationManager.toFeed(tag.url) }
            })
        }

        if (comments.isNotEmpty()) {
            details_comments_title.isVisible = true
            details_comments_count.text = comments.size.toString()
            details_comments.adapter = CommentsAdapter().apply {
                submitList(comments)
            }
        }

        if (awaitingToOpenFullImage) {
            awaitingToOpenFullImage = false
            openFullPhotoPage()
        }

        general_appbar.setExpanded(true, false)
    }

    private fun openFullPhotoPage() = with(viewModel) {
        val fullImageUrl = details.value?.fullImageUrl
        val imageUrl = item.value?.imageUrl

        if (!imageUrl.isNullOrBlank() && !fullImageUrl.isNullOrBlank()) {
            navigationManager.toPhoto(imageUrl!!, fullImageUrl!!)
        } else {
            awaitingToOpenFullImage = true
        }
    }

    companion object {

        private const val KEY_ITEM = "item_detailed"

        fun newInstance(item: FeedItem) = DetailsFragment().withArguments {
            putParcelable(KEY_ITEM, item)
        }

    }

}