package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.feed.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.FeedItem
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseViewHolder
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

open class FeedAdapter(private val onClickListener: (FeedItem) -> Unit)
    : BaseAdapter<BaseViewHolder>() {

    protected open val updateCallback by lazy { UpdateCallback() }

    var items = listOf<FeedItem>()
        @Synchronized
        set(value) {
            if (true || items.isEmpty()) {
                field = ArrayList(value)
                notifyDataSetChanged()
            } else {
                val diffResult = async {
                    DiffUtil.calculateDiff(FeedItemDiffUtilCallback(items, value))
                }

                launch(UI) {
                    field = ArrayList(value)
                    val comparisionResult = diffResult.await()
                    comparisionResult.dispatchUpdatesTo(updateCallback)
                }
            }
        }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return getItem(position).fullPageUrl.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)
        return FeedItemViewHolder(view) { onClickListener(getItem(it)) }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as FeedItemViewHolder
        holder.bind(getItem(position))
    }

    protected open fun getRealPosition(position: Int) = position

    private fun getItem(position: Int) = items[getRealPosition(position)]

    open inner class UpdateCallback : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            notifyItemRangeChanged(getRealPosition(position), count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(getRealPosition(fromPosition), getRealPosition(toPosition))
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(getRealPosition(position), count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(getRealPosition(position), count)
        }

    }

}