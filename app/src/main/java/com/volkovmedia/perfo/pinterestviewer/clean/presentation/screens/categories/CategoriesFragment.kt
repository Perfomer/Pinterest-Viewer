package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.categories

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationFragment
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.categories.adapter.CategoriesAdapter
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.categories.adapter.CategoryDiffUtilCallback
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.isVisible
import kotlinx.android.synthetic.main.general_list_content.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class CategoriesFragment : NavigationFragment() {

    override val layoutResource = R.layout.categories_fragment

    private val categoriesAdapter = CategoriesAdapter(::onCategoryClick)

    private val viewModel: CategoriesViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onInitialLoading(true)

        general_list.layoutManager = GridLayoutManager(context, 3)
        general_list.adapter = categoriesAdapter

        viewModel.items.observe(this, Observer {
            categoriesAdapter.addItems(it!!)
            onInitialLoading(false)
        })
    }

    private fun onInitialLoading(loading: Boolean) {
        launch(UI) { general_progressbar.isVisible = loading }
    }

    private fun onCategoryClick(model: Category) {
        navigationManager.toFeed(model.picsUrl)
    }

    companion object {
        fun newInstance() = CategoriesFragment()
    }

}