package com.example.githubsearch.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.model.User
import retrofit2.HttpException
import java.lang.Exception
import java.lang.NullPointerException

class UserDataSource(
    private val githubApi: GithubApi,
    private val userId: String,
    private val pageSize: Int
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            val nextPage = params.key ?: 1
            val response = githubApi.searchUser(userId, pageSize, nextPage)
            if (response.isSuccessful) {
                val body = response.body()
                val isAvailable = body!!.totalCount > pageSize * nextPage
                return LoadResult.Page(
                    data = body.items,
                    prevKey = null,
                    nextKey = if (isAvailable) nextPage + 1 else null
                )
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}