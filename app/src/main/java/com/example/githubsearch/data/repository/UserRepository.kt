package com.example.githubsearch.data.repository

import android.app.Application
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.data.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(application: Application) {

    private val api: GithubApi = RetrofitBuilder().getRetrofit()

    fun getUserList(userId: String, pageSize: Int) =
        UserDataSource(api, userId, pageSize)

    fun getRepoData(userId: String, pageSize: Int) =
        UserRepoDataSource(api, sort = "updated", userId, pageSize)

    suspend fun getUserData(userId: String?) = withContext(Dispatchers.IO) {

        val response = api.searchByUserId(userId)
        return@withContext if (response.isSuccessful) {
            val body = response.body()!!
            body
        } else {
            return@withContext
        }
    }
    
}