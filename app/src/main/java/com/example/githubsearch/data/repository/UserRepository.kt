package com.example.githubsearch.data.repository

interface UserRepository {

    fun getUserList(userId: String, pageSize: Int): UserPagingSource

    fun getRepoData(userId: String, pageSize: Int): UserRepoPagingSource

    suspend fun getUserData(userId: String?): Any

}