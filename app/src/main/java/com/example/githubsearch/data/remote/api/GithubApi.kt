package com.example.githubsearch.data.remote.api

import com.example.githubsearch.model.UserDetail
import com.example.githubsearch.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") id: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int
    ): Response<UserResponse>

    @GET("/users")
    suspend fun searchByUserId(
        @Query("user") userId: String?
    ): Response<UserDetail>
}