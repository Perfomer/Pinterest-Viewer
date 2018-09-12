package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.volkovmedia.perfo.pinterestviewer.R
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationActivity
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.NavigationManager
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.navigation.PinterestNavigator
import com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.base.BaseActivity
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.toast
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder

class MainActivity : NavigationActivity(), NavigationView.OnNavigationItemSelectedListener {

    override val frameId = R.id.main_frame

    private val drawerLayout by lazy { main_drawer }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun setToolbar(toolbar: Toolbar, appBarLayout: AppBarLayout) {
        super.setToolbar(toolbar, appBarLayout)
        initDrawerLayout()
    }

    private fun initDrawerLayout() {
        with(ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)) {
            drawerLayout.addDrawerListener(this)
            syncState()
        }

        main_navigation_view.setNavigationItemSelectedListener(this)
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

        return true
    }

}