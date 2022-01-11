package com.example.githubsearch.data.repository

import android.app.Application
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.data.remote.RetrofitBuilder

class UserRepository(application: Application) {

    private val api: GithubApi = RetrofitBuilder().getRetrofit()

    fun getUserList(userId: String, pageSize: Int) =
        UserDataSource(api, userId, pageSize)


}