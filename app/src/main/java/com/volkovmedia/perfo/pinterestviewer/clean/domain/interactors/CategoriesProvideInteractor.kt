package com.volkovmedia.perfo.pinterestviewer.clean.domain.interactors

import com.volkovmedia.perfo.pinterestviewer.clean.domain.repository.pinterest.PinterestRepository

class CategoriesProvideInteractor(private val pinterestRepository: PinterestRepository) {

    fun requestCategories(url: String) = pinterestRepository.getCategories(url)

}