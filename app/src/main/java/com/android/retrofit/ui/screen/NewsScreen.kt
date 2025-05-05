@file:Suppress("DEPRECATION")

package com.android.retrofit.ui.screen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.android.retrofit.data.Article
import com.android.retrofit.data.NewsResult
import com.android.retrofit.R


@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.onSurface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.widget),
                contentDescription = "attached-file",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(0.dp)
                    .size(140.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterVertically),
                shape = ButtonDefaults.shape,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceVariant),
                onClick = {
                    newsViewModel.getNews()
                }) {
                Text(
                    text = "Refresh News",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
        }

        when (newsViewModel.newsUiState) {
            is NewsUiState.Init -> {}
            is NewsUiState.Loading -> CircularProgressIndicator()
            is NewsUiState.Success -> ResultScreen((newsViewModel.newsUiState as NewsUiState.Success).news)
            is NewsUiState.Error -> Text(text = "Error: ${(newsViewModel.newsUiState as NewsUiState.Error).errorMsg}")
        }
    }
}

@Composable
fun ResultScreen(newsResult: NewsResult) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(newsResult.articles!!) {
            NewsCard(it!!)
        }
    }
}

@Composable
fun NewsCard (
    article: Article
) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        //append(article.url)
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color.Blue)) {
            append("Link")
            addStringAnnotation(
                tag = "URL",
                annotation = article.url!!,
                start = length - article.url.length,
                end = length
            )
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(4.dp),
        border =BorderStroke(1.dp,Color.Gray.copy(0.66f)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = article.author ?: "",
                fontSize = 16.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                textAlign = TextAlign.End,
                text = article.publishedAt ?: "",
                color = Color.Gray,
                fontSize = 12.sp
            )
            Text(
                text = article.title ?: "",
                color = Color.DarkGray,
            )
            ClickableText(
                text = annotatedString,
                style = TextStyle.Default,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                }
            )
        }
    }
}