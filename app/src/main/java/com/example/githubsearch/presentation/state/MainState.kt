package com.example.githubsearch.presentation.state

import androidx.paging.PagingData
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

sealed class MainState {

    object Idle : MainState()
    class IsBlank(val isBlank: Boolean) : MainState()
    class IsEmpty(val isEmpty: Boolean) : MainState()
    class IsBookMarkEmpty(val isBookMarkEmpty: Boolean) : MainState()
    data class SearchUser(val searchUser: Flow<PagingData<User>>) : MainState()
    data class BookMarkUser(val bookmarkUser: List<User>) : MainState()

}