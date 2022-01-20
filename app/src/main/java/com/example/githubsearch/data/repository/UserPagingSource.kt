package com.example.githubsearch.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearch.data.remote.UserRemoteDataSource
import com.example.githubsearch.data.service.GithubService
import com.example.githubsearch.model.User
import java.lang.Exception
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userId: String,
    private val pageSize: Int
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val nextPage = params.key ?: 1
            val response = userRemoteDataSource.searchUser(userId, pageSize, nextPage)
            val body = response.body()
            val isAvailable = body!!.totalCount > pageSize * nextPage
            LoadResult.Page(
                data = body.items,
                prevKey = null,
                nextKey = if (isAvailable) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}