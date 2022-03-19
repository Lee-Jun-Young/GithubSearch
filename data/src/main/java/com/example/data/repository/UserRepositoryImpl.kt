package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.pagingsource.UserPagingSource
import com.example.data.pagingsource.UserRepoPagingSource
import com.example.domain.datasource.UserRemoteDataSource
import com.example.domain.model.User
import com.example.domain.model.UserRepo
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {

    override fun getUserList(userId: String, pageSize: Int): Flow<PagingData<User>> {
        return Pager(
            PagingConfig(pageSize = 50)
        ) {
            UserPagingSource(userRemoteDataSource, userId, pageSize)
        }.flow
    }

    override fun getRepoData(userId: String, pageSize: Int): Flow<PagingData<UserRepo>> {
        return Pager(
            PagingConfig(pageSize = 50)
        ) {
            UserRepoPagingSource(userRemoteDataSource, sort = "updated", userId, pageSize)
        }.flow
    }

    override suspend fun getUserData(userId: String?) = withContext(Dispatchers.IO) {
        return@withContext userRemoteDataSource.searchByUserId(userId)
    }

}