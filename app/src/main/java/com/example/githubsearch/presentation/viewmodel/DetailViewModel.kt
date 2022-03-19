package com.example.githubsearch.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.UserDetail
import com.example.domain.model.UserRepo
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _info = MutableLiveData<UserDetail>()
    val info: LiveData<UserDetail> = _info

    private var _repo = MutableLiveData<Flow<PagingData<UserRepo>>>()
    val repo: LiveData<Flow<PagingData<UserRepo>>> = _repo

    fun loadData(userId: String?) {
        viewModelScope.launch {
            val detailData = repository.getUserData(userId)

            _info.postValue(detailData as UserDetail?)
        }

        _repo.value = repository.getRepoData(userId.toString(), 50).cachedIn(viewModelScope)

    }

}