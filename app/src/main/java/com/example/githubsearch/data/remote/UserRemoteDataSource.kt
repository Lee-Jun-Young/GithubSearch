package com.example.githubsearch.data.remote

import com.example.githubsearch.model.UserDetail
import com.example.githubsearch.model.UserRepo
import com.example.githubsearch.model.UserResponse
import retrofit2.Response

interface UserRemoteDataSource {

    suspend fun searchUser(userId: String, pageSize: Int, nextPage: Int): Response<UserResponse>

    suspend fun searchByUserId(userId: String?): Response<UserDetail>

    suspend fun searchUserRepo(userId: String, sort: String, pageSize: Int, nextPage: Int): Response<List<UserRepo>>

}