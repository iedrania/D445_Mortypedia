package com.iedrania.mortypedia.data

import com.iedrania.mortypedia.model.CharasData
import com.iedrania.mortypedia.model.FavoriteChara
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FavoritesRepository {

    private val favorites = mutableListOf<FavoriteChara>()

    init {
        if (favorites.isEmpty()) {
            CharasData.charas.forEach {
                favorites.add(FavoriteChara(it, false))
            }
        }
    }

    fun getAllCharas(): Flow<List<FavoriteChara>> {
        return flowOf(favorites)
    }

    fun getFavoriteCharaById(charaId: String): FavoriteChara {
        return favorites.first {
            it.chara.id == charaId
        }
    }

    fun updateFavorite(charaId: String, newValue: Boolean): Flow<Boolean> {
        val index = favorites.indexOfFirst { it.chara.id == charaId }
        val result = if (index >= 0) {
            val favorite = favorites[index]
            favorites[index] = favorite.copy(chara = favorite.chara, value = newValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedFavorites(): Flow<List<FavoriteChara>> {
        return getAllCharas().map { it.filter { chara -> chara.value } }
    }

    companion object {
        @Volatile
        private var instance: FavoritesRepository? = null

        fun getInstance(): FavoritesRepository = instance ?: synchronized(this) {
            FavoritesRepository().apply {
                instance = this
            }
        }
    }
}