@file:Suppress("DEPRECATION")

package com.android.retrofit.ui.screen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.retrofit.R
import com.android.retrofit.ui.theme.Pink40


@Composable
fun HomeScreen(viewModel: BookShelfViewModel, modifier: Modifier = Modifier) {

    val currentUIState by remember { derivedStateOf { viewModel.bookUiState}}
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        when (currentUIState) {
            is BookUIState.Searching -> StartScreen(viewModel)
            is BookUIState.ShowingResult -> ResultScreen(viewModel)
            is BookUIState.Error -> ErrorScreen(viewModel)
        }
    }
}

@Composable
fun StartScreen(viewModel: BookShelfViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp))
        {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.book),
            contentDescription = "book",
            contentScale = ContentScale.Crop,
            alpha = DefaultAlpha
        )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
        Spacer(modifier = Modifier.height(400.dp))
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = {
                Text(
                    text = "Enter search query"
                )
            },
            placeholder = {
                Text(
                    text = "Type a book name"
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Button(
            colors = ButtonDefaults.buttonColors(Pink40),
            onClick = {
                viewModel.updateSearchTerm(searchQuery)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(
                text = "Search",
                color = Color.White
            )
        }
            }
    }
    }
}

@Composable
fun ResultScreen(viewModel: BookShelfViewModel) {
    val uiState = viewModel.bookUiState

    if (uiState is BookUIState.ShowingResult) {
        val result = uiState.result
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp))
        {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(items = result) { item ->
                    BookPhotoCard(
                        imgSrc = item,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }

            GoBackButton(
                viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        }
    } else {
        Text("Loading")
    }
}


@Composable
fun BookPhotoCard(imgSrc: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(3f / 4f),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(imgSrc.replace("http://", "https://"))
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.error_image_generic),
                placeholder = painterResource(R.drawable.loading_image_generic),
                contentDescription = "BookPicture",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun ErrorScreen(viewModel: BookShelfViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Connection Error: Between GoogleAPI and your Internet, at least one is down!",
            modifier = Modifier
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GoBackButton(viewModel)
    }
}

@Composable
fun GoBackButton(viewModel: BookShelfViewModel, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(Pink40),
            onClick = {  Handler(Looper.getMainLooper()).post { viewModel.reset() } },
            modifier = Modifier
                .padding(horizontal = 10.dp),
        ) {
            Text(
                text = "Search Again")
        }
    }
}
