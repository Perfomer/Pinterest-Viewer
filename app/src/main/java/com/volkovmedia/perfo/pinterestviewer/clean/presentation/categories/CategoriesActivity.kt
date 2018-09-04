package com.volkovmedia.perfo.pinterestviewer.clean.presentation.categories

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.categories.adapter.CategoriesAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.categories.adapter.CategoryDiffUtilCallback
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.feed.FeedActivity
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.isVisible
import kotlinx.android.synthetic.main.feed_activity_appbar.*
import kotlinx.android.synthetic.main.feed_activity_content.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class CategoriesActivity : AppCompatActivity() {

    private val loadingIndicator by lazy { feed_progressbar }
    private val toolBar by lazy { feed_toolbar }

    private val categoriesAdapter = CategoriesAdapter(CategoryDiffUtilCallback(), GridLayoutManager(this, 3), ::onCategoryClick)

    private val viewModel: CategoriesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_activity)
        setSupportActionBar(toolBar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onInitialLoading(true)

        feed_list.adapter = categoriesAdapter

        viewModel.items.observe(this, Observer {
            categoriesAdapter.submitList(it)
            onInitialLoading(false)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onInitialLoading(loading: Boolean) {
        launch(UI) { loadingIndicator.isVisible = loading }
    }

    private fun onCategoryClick(model: Category) {
        FeedActivity.startActivity(this, model.picsUrl)
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, CategoriesActivity::class.java)
            context.startActivity(intent)
        }

    }

}