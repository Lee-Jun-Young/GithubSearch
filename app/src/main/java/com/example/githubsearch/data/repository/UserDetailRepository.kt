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
            Log.d("test!!","성공")
            val body = response.body()!!
            Log.d("test!!",body.toString())
            body
        } else {
            Log.d("test!!","실패")
            return@withContext
        }
    }
}