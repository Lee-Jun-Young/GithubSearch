package com.example.githubsearch.data.repository

import android.app.Application
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository(application: Application) {
    private val retrofit: Retrofit = RetrofitBuilder().getInstance()
    private val api = retrofit.create(GithubApi::class.java)

    suspend fun getUserList(userId: String) = withContext(Dispatchers.IO) {

        val response = api.searchUserId(userId, 50, 1)
        return@withContext if (response.isSuccessful) {
            val body = response.body()!!
            body.items
        } else {

        }
    }

}