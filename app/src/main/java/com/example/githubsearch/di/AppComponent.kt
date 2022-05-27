package com.example.githubsearch.di

import android.content.Context
import com.example.data.BindsModule
import com.example.database.DatabaseModule
import com.example.githubsearch.presentation.activity.DetailActivity
import com.example.githubsearch.presentation.activity.MainActivity
import com.example.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BindsModule::class, NetworkModule::class, DatabaseModule::class, MainModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)
}