package com.example.githubsearch.data.repository

import android.app.Application
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit

class UserRepository(application: Application) {
    private val retrofit: Retrofit = RetrofitBuilder().getInstance()
    private val api = retrofit.create(GithubApi::class.java)

    fun getUserList(userId: String, pageSize: Int) =
        UserDataSource(api, userId, pageSize)

}