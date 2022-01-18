package com.example.githubsearch.ui.detail

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubsearch.data.repository.UserRepository
import com.example.githubsearch.model.UserDetail
import com.example.githubsearch.model.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: UserRepository) : ViewModel() {

    private val _info = MutableLiveData<UserDetail>()
    val info: LiveData<UserDetail> = _info

    private var _repo = MutableLiveData<Flow<PagingData<UserRepo>>>()
    val repo: LiveData<Flow<PagingData<UserRepo>>> = _repo

    fun loadData(userId: String?) {
        viewModelScope.launch {
            val detailData = repository.getUserData(userId)

            _info.postValue(detailData as UserDetail?)
        }

        _repo.value = Pager(
            PagingConfig(pageSize = 50)
        ) {
            repository.getRepoData(userId.toString(), 50)
        }.flow

    }


}