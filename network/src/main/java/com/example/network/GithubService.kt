package com.example.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") id: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int
    ): Response<UserResponseDto>

    @GET("/users/{user}")
    suspend fun searchByUserId(
        @Path("user") userId: String?
    ): Response<UserDetailDto>

    @GET("/users/{user}/repos")
    suspend fun searchUserRepo(
        @Path("user") userId: String?,
        @Query("sort") sort: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int
    ): Response<List<UserRepoDto>>
}