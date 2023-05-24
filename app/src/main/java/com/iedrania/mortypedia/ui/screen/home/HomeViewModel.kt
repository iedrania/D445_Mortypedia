package com.iedrania.mortypedia.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iedrania.mortypedia.data.FavoritesRepository
import com.iedrania.mortypedia.model.FavoriteChara
import com.iedrania.mortypedia.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteChara>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteChara>>>
        get() = _uiState

    fun getAllCharas() {
        viewModelScope.launch {
            repository.getAllCharas().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { charas ->
                _uiState.value = UiState.Success(charas)
            }
        }
    }
}