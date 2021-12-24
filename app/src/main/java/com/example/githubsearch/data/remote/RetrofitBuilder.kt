package com.example.githubsearch.data.remote

import com.example.githubsearch.BuildConfig
import com.example.githubsearch.data.remote.api.GithubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    fun getRetrofit(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(GithubApi::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    }

}