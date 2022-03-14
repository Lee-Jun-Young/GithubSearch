package com.example.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserList(userId: String, pageSize: Int): Flow<PagingData<User>>

    fun getRepoData(userId: String, pageSize: Int): Flow<PagingData<UserRepo>>

    suspend fun getUserData(userId: String?): Any

}