package com.example.data.datasource

import com.example.data.mapper.toUserDetail
import com.example.data.mapper.toUserRepo
import com.example.data.mapper.toUserResponse
import com.example.domain.datasource.UserRemoteDataSource
import com.example.domain.model.UserDetail
import com.example.domain.model.UserRepo
import com.example.domain.model.UserResponse
import com.example.network.GithubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val githubService: GithubService) :
    UserRemoteDataSource {

    override suspend fun searchUser(userId: String, pageSize: Int, nextPage: Int): UserResponse =
        withContext(Dispatchers.IO) {
            githubService.searchUser(userId, pageSize, nextPage).body()!!.toUserResponse()
        }

    override suspend fun searchByUserId(userId: String?): UserDetail = withContext(Dispatchers.IO) {
        githubService.searchByUserId(userId).body()!!.toUserDetail()
    }

    override suspend fun searchUserRepo(
        userId: String,
        sort: String,
        pageSize: Int,
        nextPage: Int
    ): List<UserRepo> = withContext(Dispatchers.IO) {
        githubService.searchUserRepo(userId, sort, pageSize, nextPage).body()!!.map {
            it.toUserRepo()
        }
    }

}