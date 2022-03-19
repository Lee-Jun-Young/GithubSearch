package com.example.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.datasource.UserRemoteDataSource
import com.example.domain.model.UserRepo
import javax.inject.Inject

class UserRepoPagingSource @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val sort: String,
    private val userId: String,
    private val pageSize: Int
) : PagingSource<Int, UserRepo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepo> {
        return try {
            val nextPage = params.key ?: 1
            val response = userRemoteDataSource.searchUserRepo(userId, sort, pageSize, nextPage)
            val isAvailable = response!!.size > pageSize * nextPage
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (isAvailable) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserRepo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}