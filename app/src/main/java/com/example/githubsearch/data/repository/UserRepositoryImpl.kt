package com.example.githubsearch.data.repository

import com.example.githubsearch.data.service.GithubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val githubService: GithubService) :
    UserRepository {

    override fun getUserList(userId: String, pageSize: Int) =
        UserPagingSource(githubService, userId, pageSize)

    override fun getRepoData(userId: String, pageSize: Int) =
        UserRepoPagingSource(githubService, sort = "updated", userId, pageSize)

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

