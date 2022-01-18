package com.example.githubsearch.data.repository

import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.data.remote.api.GithubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl : UserRepository {

    private val githubService: GithubService = RetrofitBuilder().getRetrofit()

    override fun getUserList(userId: String, pageSize: Int) =
        UserDataSource(githubService, userId, pageSize)

    override fun getRepoData(userId: String, pageSize: Int) =
        UserRepoDataSource(githubService, sort = "updated", userId, pageSize)

    override suspend fun getUserData(userId: String?) = withContext(Dispatchers.IO) {

        val response = githubService.searchByUserId(userId)
        return@withContext if (response.isSuccessful) {
            val body = response.body()!!
            body
        } else {
            return@withContext
        }
    }

}

