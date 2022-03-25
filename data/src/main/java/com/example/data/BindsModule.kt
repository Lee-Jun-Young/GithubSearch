package com.example.data


import com.example.data.datasource.local.FavoriteLocalDataSourceImpl
import com.example.data.datasource.remote.UserRemoteDataSourceImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.datasource.local.FavoriteLocalDataSource
import com.example.domain.datasource.remote.UserRemoteDataSource
import com.example.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class BindsModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideUserDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun provideFavoriteDataSource(favoriteLocalDataSourceImpl: FavoriteLocalDataSourceImpl): FavoriteLocalDataSource
}