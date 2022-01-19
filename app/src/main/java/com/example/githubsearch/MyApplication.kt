package com.example.githubsearch

import android.app.Application
import com.example.githubsearch.di.AppComponent
import com.example.githubsearch.di.DaggerAppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {

        DaggerAppComponent.factory().create(applicationContext)

    }
}