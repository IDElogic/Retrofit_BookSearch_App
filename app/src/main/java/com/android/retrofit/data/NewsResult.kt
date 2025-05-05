package com.android.retrofit.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResult(
    @SerialName("articles")
    val articles: List<Article?>?,
    @SerialName("status")
    val status: String?,
    @SerialName("totalResults")
    val totalResults: Int?
)