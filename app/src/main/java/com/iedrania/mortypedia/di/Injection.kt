package com.iedrania.mortypedia.di

import com.iedrania.mortypedia.data.FavoritesRepository

object Injection {
    fun provideRepository(): FavoritesRepository {
        return FavoritesRepository.getInstance()
    }
}