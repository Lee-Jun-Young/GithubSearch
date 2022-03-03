package com.example.githubsearch.ui.main

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bumptech.glide.load.engine.Resource
import com.example.githubsearch.R
import com.example.githubsearch.data.repository.UserRepository
import com.example.githubsearch.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private var _data = MutableLiveData<Flow<PagingData<User>>>()
    val data: LiveData<Flow<PagingData<User>>> = _data

    private var _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private var _isBlank = MutableLiveData<Boolean>()
    var isBlank: LiveData<Boolean> = _isBlank

    val searchId = MutableLiveData<String>()

    fun getUserId() {
        val userId = searchId.value
        _isBlank.value = userId.isNullOrBlank()

        _data.value = repository.getUserList(userId.toString(), 50).cachedIn(viewModelScope)
    }

    fun setUsersLoadState(loadState: CombinedLoadStates?) {
        val state = loadState?.refresh
        _isEmpty.value = state == null
    }

}