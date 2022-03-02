package com.example.githubsearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubsearch.data.remote.UserRemoteDataSource
import com.example.githubsearch.model.User
import com.example.githubsearch.model.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {

    override fun getUserList(userId: String, pageSize: Int): Flow<PagingData<User>> {
        return Pager(
            PagingConfig(pageSize = 50)
        ) {
            UserPagingSource(userRemoteDataSource, userId, pageSize)
        }.flow
    }

    override fun getRepoData(userId: String, pageSize: Int): Flow<PagingData<UserRepo>> {
        return Pager(
            PagingConfig(pageSize = 50)
        ) {
            UserRepoPagingSource(userRemoteDataSource, sort = "updated", userId, pageSize)
        }.flow
    }

    override suspend fun getUserData(userId: String?) = withContext(Dispatchers.IO) {
        val response = userRemoteDataSource.searchByUserId(userId)
        return@withContext if (response.isSuccessful) {
            val body = response.body()!!
            body
        } else {
            return@withContext
        }
    }

}

