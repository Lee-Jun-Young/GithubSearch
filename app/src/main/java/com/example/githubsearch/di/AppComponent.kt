package com.example.githubsearch.di

import android.content.Context
import com.example.githubsearch.ui.detail.DetailActivity
import com.example.githubsearch.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BindsModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)
}