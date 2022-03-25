package com.example.githubsearch.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetFavoritesUseCase
import com.example.domain.usecase.SearchUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserListUseCase: SearchUserUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) :
    ViewModel() {

    private var _data = MutableLiveData<Flow<PagingData<User>>>()
    val data: LiveData<Flow<PagingData<User>>> = _data

    private var _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private var _isBlank = MutableLiveData<Boolean>()
    var isBlank: LiveData<Boolean> = _isBlank

    private var _favorites = MutableLiveData<List<User>>()
    var favorites: LiveData<List<User>> = _favorites

    val searchId = MutableLiveData<String>()

    fun getUserId() {
        val userId = searchId.value
        _isBlank.value = userId.isNullOrBlank()

        _data.value = getUserListUseCase(userId.toString())
    }

    fun setUsersLoadState(loadState: CombinedLoadStates?) {
        val state = loadState?.refresh
        _isEmpty.value = state == null
    }

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _favorites.postValue(getFavoritesUseCase.invoke())
        }
    }

}