package com.iedrania.mortypedia.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.iedrania.mortypedia.R
import com.iedrania.mortypedia.di.Injection
import com.iedrania.mortypedia.ui.ViewModelFactory
import com.iedrania.mortypedia.ui.common.UiState
import com.iedrania.mortypedia.ui.theme.MortypediaTheme

@Composable
fun DetailScreen(
    charaId: String, viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ), navigateBack: () -> Unit, navigateToFavorites: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteById(charaId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(data.chara.name,
                    data.chara.photoUrl,
                    data.value,
                    onBackClick = navigateBack,
                    onAddToFavorites = { newValue ->
                        viewModel.addToFavorites(data.chara, newValue)
                        navigateToFavorites()
                    })
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    photoUrl: String,
    value: Boolean,
    onBackClick: () -> Unit,
    onAddToFavorites: (newValue: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    var favValue by rememberSaveable { mutableStateOf(value) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() })
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(LightGray)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Button(onClick = {
                onAddToFavorites(!favValue)
            }) {
                Text(stringResource(R.string.add_to_favorites))
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    MortypediaTheme {
        DetailContent("Chara Name", "", false, onBackClick = {}, onAddToFavorites = {})
    }
}