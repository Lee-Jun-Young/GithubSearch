package com.example.githubsearch.data.repository

import androidx.paging.PagingData
import com.example.githubsearch.model.User
import com.example.githubsearch.model.UserRepo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserList(userId: String, pageSize: Int): Flow<PagingData<User>>

    fun getRepoData(userId: String, pageSize: Int): Flow<PagingData<UserRepo>>

    suspend fun getUserData(userId: String?): Any

}