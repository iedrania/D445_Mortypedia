package com.iedrania.mortypedia.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iedrania.mortypedia.di.Injection
import com.iedrania.mortypedia.model.FavoriteChara
import com.iedrania.mortypedia.ui.ViewModelFactory
import com.iedrania.mortypedia.ui.common.UiState
import com.iedrania.mortypedia.ui.components.CharaItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCharas()
            }

            is UiState.Success -> {
                HomeContent(charas = uiState.data, modifier = modifier)
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    charas: List<FavoriteChara>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(charas) { data ->
            CharaItem(
                name = data.chara.name,
                photoUrl = data.chara.photoUrl,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}