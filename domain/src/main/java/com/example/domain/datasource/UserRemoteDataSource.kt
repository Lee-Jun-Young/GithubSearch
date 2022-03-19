package com.example.domain.datasource

import com.example.domain.model.UserDetail
import com.example.domain.model.UserRepo
import com.example.domain.model.UserResponse

interface UserRemoteDataSource {

    suspend fun searchUser(userId: String, pageSize: Int, nextPage: Int): UserResponse

    suspend fun searchByUserId(userId: String?): UserDetail

    suspend fun searchUserRepo(userId: String, sort: String, pageSize: Int, nextPage: Int): List<UserRepo>

}