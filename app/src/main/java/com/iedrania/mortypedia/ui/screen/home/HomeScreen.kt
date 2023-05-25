package com.iedrania.mortypedia.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
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
    navigateToDetail: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCharas()
            }

            is UiState.Success -> {
                HomeContent(
                    charas = uiState.data, modifier = modifier, navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    charas: List<FavoriteChara>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(charas) { data ->
            Box(modifier = Modifier.clickable { navigateToDetail(data.chara.id) }) {
                CharaItem(name = data.chara.name, photoUrl = data.chara.photoUrl)
            }
            Divider()
        }
    }
}