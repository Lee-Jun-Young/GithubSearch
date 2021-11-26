package com.example.githubsearch.data.repository

import androidx.lifecycle.LiveData
import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.model.UserDetail
import retrofit2.Retrofit

class UserDetailRepository {

    private val retrofit: Retrofit = RetrofitBuilder().getInstance()
    private val api = retrofit.create(GithubApi::class.java)


}