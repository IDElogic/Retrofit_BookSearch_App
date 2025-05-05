package com.android.retrofit.network

import com.android.retrofit.data.NewsResult
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=cb5b31a80eff4bd1a6f63870d6bd533e
// Host: https://newsapi.org
//
// Path: /v2/top-headlines
//
// Query params: ?country=us&category=business&apiKey=cb5b31a80eff4bd1a6f63870d6bd533e


interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getNews(@Query("country") country: String,
                              @Query("apiKey") apiKey: String): NewsResult

    @GET("/v2/top-headlines")
    suspend fun getNewsAsNews(@Query("country") country: String,
                                @Query("apiKey") apiKey: String): String

}
