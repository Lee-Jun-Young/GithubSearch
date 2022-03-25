package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.User
import com.example.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserList(userId: String, pageSize: Int): Flow<PagingData<User>>

    fun getRepoData(userId: String, pageSize: Int): Flow<PagingData<UserRepo>>

    suspend fun getUserData(userId: String?): Any

    suspend fun getFavorites(): List<User>

    fun addFavorite(user: User)

    fun deleteFavorite(userId: String)

}