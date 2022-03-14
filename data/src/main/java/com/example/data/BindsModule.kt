package com.example.data


import com.example.domain.UserRemoteDataSource
import com.example.domain.UserRepository
import dagger.Binds
import dagger.Module
import org.jetbrains.annotations.NotNull

@Module
abstract class BindsModule {

    @NotNull
    @Binds
    abstract fun provideRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @NotNull
    @Binds
    abstract fun provideUserDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

}