package com.iedrania.mortypedia.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iedrania.mortypedia.data.FavoritesRepository
import com.iedrania.mortypedia.model.Chara
import com.iedrania.mortypedia.model.FavoriteChara
import com.iedrania.mortypedia.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteChara>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteChara>>
        get() = _uiState

    fun getFavoriteById(charaId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getFavoriteCharaById(charaId))
        }
    }

    fun addToFavorites(chara: Chara, newValue: Boolean) {
        viewModelScope.launch {
            repository.updateFavorite(chara.id, newValue)
        }
    }
}