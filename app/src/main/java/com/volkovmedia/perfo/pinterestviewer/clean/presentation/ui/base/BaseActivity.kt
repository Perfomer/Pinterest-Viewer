package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.base

import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

abstract class BaseActivity: AppCompatActivity() {

    protected lateinit var toolbar: Toolbar

    open fun setToolbar(toolbar: Toolbar, appBarLayout: AppBarLayout) {
        this.toolbar = toolbar
        setSupportActionBar(toolbar)
        appBarLayout.bringToFront()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

}