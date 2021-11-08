package com.example.githubsearch.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.model.User
import com.example.githubsearch.model.UserResponse
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.NullPointerException

class UserDataSource(private val githubApi: GithubApi) : PagingSource<Int, User>() {

    private val retrofit: Retrofit = RetrofitBuilder().getInstance()
    private val api = retrofit.create(githubApi::class.java)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            val nextPage = params.key ?:1
            val pageSize = 50

            val response = api.searchUserId("lee",pageSize, nextPage)
            if(response.isSuccessful){
                val body = response.body()
                if(body != null){
                    val isAvailable = body.totalCount > pageSize * nextPage
                    return LoadResult.Page(
                        data = body.items,
                        prevKey = null,
                        nextKey = if (isAvailable) nextPage + 1 else null
                    )
                }else{
                    throw NullPointerException("response body is null")
                }
            }else{
                throw HttpException(response)
            }
        }catch (e: Exception){
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