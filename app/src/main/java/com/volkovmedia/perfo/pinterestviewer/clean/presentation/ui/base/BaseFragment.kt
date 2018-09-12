package com.volkovmedia.perfo.pinterestviewer.clean.presentation.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment() : Fragment() {

    protected abstract val layoutResource: Int

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    protected companion object {

        fun <F: BaseFragment> F.withArguments(bundleInitialization: Bundle.() -> Unit): F {
            arguments = Bundle().apply { bundleInitialization(this) }
            return this
        }

    }

}