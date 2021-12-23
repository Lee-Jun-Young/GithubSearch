package com.example.githubsearch.data.repository

import android.app.Application
import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.data.remote.api.GithubApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDetailRepository(application: Application) {

    private val api: GithubApi = RetrofitBuilder().getRetrofit()

    suspend fun getUserData(userId: String?) = withContext(Dispatchers.IO) {

        val response = api.searchByUserId(userId)
        return@withContext if (response.isSuccessful) {
            val body = response.body()!!
            body
        } else {
            return@withContext
        }
    }

    fun getRepoData(userId: String, pageSize: Int) =
        UserRepoDataSource(api, sort = "updated", userId, pageSize)


}