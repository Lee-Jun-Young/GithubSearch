package com.example.githubsearch.presentation.state

import androidx.paging.PagingData
import com.example.domain.model.User
import com.example.domain.model.UserDetail
import com.example.domain.model.UserRepo
import kotlinx.coroutines.flow.Flow

sealed class DetailState {

    object Idle : DetailState()
    class IsChecked(val isChecked: Boolean) : DetailState()
    data class Repo(val repo: Flow<PagingData<UserRepo>>) : DetailState()
    data class Info(val info: UserDetail) : DetailState()

}