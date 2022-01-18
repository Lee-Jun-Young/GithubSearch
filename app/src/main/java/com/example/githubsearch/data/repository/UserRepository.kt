package com.example.githubsearch.data.repository

interface UserRepository {

    fun getUserList(userId: String, pageSize: Int): UserDataSource

    fun getRepoData(userId: String, pageSize: Int): UserRepoDataSource

    suspend fun getUserData(userId: String?): Any

}