package com.volkovmedia.perfo.pinterestviewer.clean.presentation.screens.categories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.volkovmedia.perfo.pinterestviewer.BuildConfig
import com.volkovmedia.perfo.pinterestviewer.clean.data.entity.Category
import com.volkovmedia.perfo.pinterestviewer.clean.data.parsers.base.RequestResult
import com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors.CategoriesProvideInteractor
import kotlinx.coroutines.experimental.launch

open class CategoriesViewModel(private val categoriesProvideInteractor: CategoriesProvideInteractor) : ViewModel() {

    val items: LiveData<List<Category>> by lazy {
        launch { requestData() }
        return@lazy itemsLiveData
    }

    private val itemsLiveData = MutableLiveData<List<Category>>()

    private fun requestData() {
        val result = categoriesProvideInteractor.requestCategories(BuildConfig.ROOT_URL + "categories/")

        when (result) {
            is RequestResult.Data -> itemsLiveData.postValue(result.data)
        }
    }

}