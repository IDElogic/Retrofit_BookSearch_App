package com.android.retrofit.network

import com.android.retrofit.model.APIResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBookAPIService {
    @GET("volumes")
    suspend fun searchForBook(@Query("q") term: String): APIResponse

}