package com.example.githubsearch.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.User
import com.example.domain.model.UserDetail
import com.example.domain.model.UserRepo
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.AddFavoriteUseCase
import com.example.domain.usecase.DeleteFavoriteUseCase
import com.example.domain.usecase.SearchByUserIdUseCase
import com.example.domain.usecase.SearchUserRepoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getUserDataUseCase: SearchByUserIdUseCase,
    private val getRepoDataUseCase: SearchUserRepoUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private val _info = MutableLiveData<UserDetail>()
    val info: LiveData<UserDetail> = _info

    private var _repo = MutableLiveData<Flow<PagingData<UserRepo>>>()
    val repo: LiveData<Flow<PagingData<UserRepo>>> = _repo

    fun loadData(userId: String?) {
        viewModelScope.launch {
            val detailData = getUserDataUseCase(userId.toString())

            _info.postValue(detailData as UserDetail?)
        }

        _repo.value = getRepoDataUseCase(userId.toString())
    }

    fun addBookMark(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavoriteUseCase(user)
        }
    }

    fun deleteBookMark(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteUseCase(userId)
        }
    }

}