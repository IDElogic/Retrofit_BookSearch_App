package com.android.retrofit.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.android.retrofit.network.NewsAPI
import com.android.retrofit.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NEWS_BASE_URL)
            .addConverterFactory(Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()) )
            //.addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsAPI(retrofit: Retrofit): NewsAPI {
        return retrofit.create(NewsAPI::class.java)
    }
}