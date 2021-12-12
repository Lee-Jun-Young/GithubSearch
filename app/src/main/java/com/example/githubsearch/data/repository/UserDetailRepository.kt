package com.example.githubsearch.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

class UserDetailRepository(application: Application) {

    private val retrofit: Retrofit = RetrofitBuilder().getInstance()
    private val api = retrofit.create(GithubApi::class.java)

    suspend fun getUserData(userId: String?) = withContext(Dispatchers.IO) {

        val response = api.searchByUserId(userId)
        return@withContext if (response.isSuccessful) {
            val body = response.body()!!
            body
        } else {
            return@withContext
        }
    }

    suspend fun getRepoData(userId: String?) = withContext(Dispatchers.IO) {

        val response = api.userRepo(userId,"updated")
        return@withContext if (response.isSuccessful) {
            val body = response.body()!!
            body
        } else {
            return@withContext
        }
    }

}