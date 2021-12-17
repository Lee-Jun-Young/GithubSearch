package com.example.githubsearch.data.remote

import com.example.githubsearch.BuildConfig
import com.example.githubsearch.data.remote.api.GithubApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    fun getRetrofit(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

}