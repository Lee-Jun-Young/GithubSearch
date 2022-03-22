package com.example.database

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDao(context: Context): UserDao {
        return AppDatabase.getDatabase(context)!!.userDao()
    }

}