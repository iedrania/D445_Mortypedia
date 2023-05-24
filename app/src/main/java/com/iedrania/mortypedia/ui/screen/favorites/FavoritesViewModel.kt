package com.iedrania.mortypedia.ui.screen.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iedrania.mortypedia.data.FavoritesRepository
import com.iedrania.mortypedia.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoritesState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoritesState>>
        get() = _uiState

    fun getAddedFavorites() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedFavorites().collect { favorite ->
                _uiState.value = UiState.Success(FavoritesState(favorite))
            }
        }
    }

    fun updateFavorite(charaId: String, newValue: Boolean) {
        viewModelScope.launch {
            repository.updateFavorite(charaId, newValue).collect { isUpdated ->
                if (isUpdated) {
                    getAddedFavorites()
                }
            }
        }
    }
}