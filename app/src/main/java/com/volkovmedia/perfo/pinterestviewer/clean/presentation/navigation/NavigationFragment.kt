package com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseActivity
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseFragment
import kotlinx.android.synthetic.main.feed_fragment.*
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent

abstract class NavigationFragment : BaseFragment(), KoinComponent {

    private val baseActivity by lazy { activity as BaseActivity }

    protected val navigationManager: NavigationManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(getToolbars()) { baseActivity.setToolbar(first, second) }
    }

    protected open fun getToolbars(): Pair<Toolbar, AppBarLayout> {
        return general_toolbar to general_appbar
    }

}