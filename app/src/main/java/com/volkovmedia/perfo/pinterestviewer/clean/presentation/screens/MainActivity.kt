package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.MenuItem
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationActivity
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.toast
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : NavigationActivity(), NavigationView.OnNavigationItemSelectedListener {

    override val frameId = R.id.main_frame

    private val drawerToggle by lazy {
        ActionBarDrawerToggle(this, main_drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onBackPressed() {
        if (main_drawer.isDrawerOpen(GravityCompat.START)) {
            main_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun setToolbar(toolbar: Toolbar, appBarLayout: AppBarLayout) {
        super.setToolbar(toolbar, appBarLayout)
        initDrawerLayout()
    }

    private fun initDrawerLayout() {
        val isBackStackEmpty = supportFragmentManager.backStackEntryCount == 0

        with(drawerToggle) {
            main_drawer.addDrawerListener(this)
            syncState()
        }

        main_navigation_view.setNavigationItemSelectedListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(!isBackStackEmpty)
        if (isBackStackEmpty) drawerToggle.isDrawerIndicatorEnabled = true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_feed_home -> navigationManager.dropToRoot()
            R.id.menu_feed_subscriptions -> toast("You haven't logged in")
            R.id.menu_feed_categories -> navigationManager.toCategories()
            R.id.menu_feed_settings -> toast("Not ready yet")
            R.id.menu_feed_about -> toast("Not ready yet")
            else -> return false
        }

        main_drawer.closeDrawer(Gravity.START)
        return true
    }

}