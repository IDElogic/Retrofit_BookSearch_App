package com.android.retrofit

import android.app.Application
import com.android.retrofit.data.AppContainer
import com.android.retrofit.data.DefaultAppContainer

class MainApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}