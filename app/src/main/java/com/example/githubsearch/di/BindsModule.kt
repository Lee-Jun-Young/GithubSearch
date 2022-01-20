package com.example.githubsearch.di

import com.example.githubsearch.data.remote.UserRemoteDataSource
import com.example.githubsearch.data.remote.UserRemoteDataSourceImpl
import com.example.githubsearch.data.repository.UserRepository
import com.example.githubsearch.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class BindsModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideUserDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

}