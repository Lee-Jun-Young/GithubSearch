package com.example.githubsearch.data.repository

import com.example.githubsearch.data.remote.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {

    override fun getUserList(userId: String, pageSize: Int) =
        UserPagingSource(userRemoteDataSource, userId, pageSize)

    override fun getRepoData(userId: String, pageSize: Int) =
        UserRepoPagingSource(userRemoteDataSource, sort = "updated", userId, pageSize)

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

