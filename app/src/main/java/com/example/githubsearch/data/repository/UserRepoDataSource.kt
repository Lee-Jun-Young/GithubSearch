package com.example.githubsearch.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.model.UserRepo
import retrofit2.HttpException
import java.lang.Exception

class UserRepoDataSource(
    private val githubApi: GithubApi,
    private val sort: String,
    private val userId: String,
    private val pageSize: Int
) : PagingSource<Int, UserRepo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepo> {
        try {
            val nextPage = params.key ?: 1
            val response = githubApi.searchUserRepo(userId, sort, pageSize, nextPage)
            if (response.isSuccessful) {
                val body = response.body()
                val isAvailable = body!!.size > pageSize * nextPage
                return LoadResult.Page(
                    data = body,
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

    override fun getRefreshKey(state: PagingState<Int, UserRepo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}