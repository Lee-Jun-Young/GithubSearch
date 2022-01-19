package com.example.githubsearch.di

import com.example.githubsearch.data.repository.UserRepository
import com.example.githubsearch.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repositoryImpl: UserRepositoryImpl): UserRepository

}