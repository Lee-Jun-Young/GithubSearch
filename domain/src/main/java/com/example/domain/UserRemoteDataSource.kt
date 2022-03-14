package com.example.domain

import retrofit2.Response

interface UserRemoteDataSource {

    suspend fun searchUser(userId: String, pageSize: Int, nextPage: Int): Response<User>

    suspend fun searchByUserId(userId: String?): Response<User>

    suspend fun searchUserRepo(userId: String, sort: String, pageSize: Int, nextPage: Int): Response<List<UserRepo>>

}