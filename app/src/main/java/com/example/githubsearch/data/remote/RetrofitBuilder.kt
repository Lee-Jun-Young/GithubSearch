package com.example.githubsearch.data.remote

import com.example.githubsearch.data.remote.api.GithubApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    private val baseURL = "https://api.github.com"

    fun getRetrofit(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

}