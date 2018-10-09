package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.volkovmedia.perfo.pinterestviewer.utils.extensions.showStatusBar

abstract class BaseFragment() : Fragment() {

    protected abstract val layoutResource: Int

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.showStatusBar(true)
    }

    protected companion object {

        fun <F: BaseFragment> F.withArguments(bundleInitialization: Bundle.() -> Unit): F {
            arguments = Bundle().apply { bundleInitialization(this) }
            return this
        }

    }

}