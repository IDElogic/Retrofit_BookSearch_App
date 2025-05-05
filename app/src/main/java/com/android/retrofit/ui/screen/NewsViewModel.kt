package com.android.retrofit.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.retrofit.BuildConfig
import com.android.retrofit.data.NewsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import com.android.retrofit.network.NewsAPI
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface NewsUiState {
    object Init : NewsUiState
    object Loading : NewsUiState
    data class Success(val news: NewsResult) : NewsUiState
    data class Error(val errorMsg: String) : NewsUiState
}

@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsAPI: NewsAPI
) : ViewModel() {

    var newsUiState: NewsUiState by mutableStateOf(NewsUiState.Init)


    fun getNews() {
        newsUiState = NewsUiState.Loading
        viewModelScope.launch {
            newsUiState = try {
                val result = newsAPI.getNews("us",
                    BuildConfig.NEWS_API_KEY)
                NewsUiState.Success(result)
            } catch (e: Exception) {
                NewsUiState.Error(e.message!!)
            }
        }
    }
}