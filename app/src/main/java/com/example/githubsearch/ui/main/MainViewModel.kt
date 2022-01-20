package com.example.githubsearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubsearch.data.repository.UserRepository
import com.example.githubsearch.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MainViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

    private var _data = MutableLiveData<Flow<PagingData<User>>>()
    val data: LiveData<Flow<PagingData<User>>> = _data

    private var _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private var _isBlank = MutableLiveData<String>()
    var isBlank: LiveData<String> = _isBlank

    val searchId = MutableLiveData<String>()

    fun getUserId() {
        val userId = searchId.value

        if (userId.isNullOrBlank()) {
            _isBlank.value = "검색어를 입력해주세요."
            return
        }

        _data.value = Pager(
            PagingConfig(pageSize = 50)
        ) {
            repository.getUserList(userId.toString(), 50)
        }.flow

    }

    fun setUsersLoadState(loadState: CombinedLoadStates?) {
        val state = loadState?.refresh
        _isEmpty.value = state == null
    }

}