package com.volkovmedia.perfo.pinterestviewer.clean.presentation.base

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

abstract class BaseActivity: AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

}