package com.iedrania.mortypedia.ui.screen.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iedrania.mortypedia.di.Injection
import com.iedrania.mortypedia.ui.ViewModelFactory
import com.iedrania.mortypedia.ui.common.UiState
import com.iedrania.mortypedia.ui.components.CharaItem

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedFavorites()
            }

            is UiState.Success -> {
                FavoritesContent(
                    uiState.data,
//                    onValueChanged = { charaId, value ->
//                        viewModel.updateFavorite(charaId, value)
//                    },
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoritesContent(
    state: FavoritesState,
//    onValueChanged: (id: String, value: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(state.favorites, key = { it.chara.id }) { data ->
            CharaItem(
                name = data.chara.name,
                photoUrl = data.chara.photoUrl,
//                value = data.value,
//                onValueChanged = onValueChanged,
            )
            Divider()
        }
    }
}