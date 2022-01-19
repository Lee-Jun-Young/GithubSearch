package com.example.githubsearch.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearch.data.remote.api.GithubService
import com.example.githubsearch.model.UserRepo
import java.lang.Exception
import javax.inject.Inject

class UserRepoDataSource(
    private val githubService: GithubService,
    private val sort: String,
    private val userId: String,
    private val pageSize: Int
) : PagingSource<Int, UserRepo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepo> {
        return try {
            val nextPage = params.key ?: 1
            val response = githubService.searchUserRepo(userId, sort, pageSize, nextPage)
            val body = response.body()
            val isAvailable = body!!.size > pageSize * nextPage
            LoadResult.Page(
                data = body,
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