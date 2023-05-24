package com.iedrania.mortypedia.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iedrania.mortypedia.data.FavoritesRepository
import com.iedrania.mortypedia.ui.screen.detail.DetailViewModel
import com.iedrania.mortypedia.ui.screen.favorites.FavoritesViewModel
import com.iedrania.mortypedia.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FavoritesRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}