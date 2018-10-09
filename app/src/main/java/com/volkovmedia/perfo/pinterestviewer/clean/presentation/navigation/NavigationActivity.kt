package com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.NavigationView
import android.support.v7.widget.Toolbar
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base.BaseActivity
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder

abstract class NavigationActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    protected abstract val frameId: Int

    protected val navigationManager: NavigationManager by inject()

    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator by lazy { PinterestNavigator(supportFragmentManager, frameId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationManager.toRoot()
    }

    override fun onBackPressed() {
        navigationManager.back()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

}