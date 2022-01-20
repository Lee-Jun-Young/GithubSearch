package com.example.githubsearch.data.remote

import com.example.githubsearch.data.service.GithubService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val githubService: GithubService) : UserRemoteDataSource {

    override suspend fun searchUser(userId: String, pageSize: Int, nextPage: Int) =
        githubService.searchUser(userId, pageSize, nextPage)

    override suspend fun searchByUserId(userId: String?) =
        githubService.searchByUserId(userId)

    override suspend fun searchUserRepo(userId: String, sort: String, pageSize: Int, nextPage: Int) =
        githubService.searchUserRepo(userId, sort, pageSize, nextPage)

}