package com.example.githubsearch.presentation.viewmodel

import android.util.Log
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
import com.example.githubsearch.presentation.intent.MainIntent
import com.example.githubsearch.presentation.state.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserListUseCase: SearchUserUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) :
    ViewModel() {

    private var _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private var _isEmptyBookMark = MutableLiveData<Boolean>()
    val isEmptyBookMark: LiveData<Boolean> = _isEmptyBookMark

    val searchId = MutableLiveData<String>()

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.SearchUser -> getUserId()
                    is MainIntent.BookMarkUser -> getFavorites()
                }
            }
        }
    }

    fun getUserId() {
        val userId = searchId.value

        if (userId.isNullOrBlank()) {
            _state.value = MainState.IsBlank(userId.isNullOrBlank())
        } else {
            _state.value = MainState.SearchUser(getUserListUseCase(userId.toString()))
        }
    }

    fun setUsersLoadState(loadState: CombinedLoadStates?) {
        val state = loadState?.refresh
        _isEmpty.value = state == null
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getFavoritesUseCase()

            _state.value = MainState.BookMarkUser(result)

            _isEmptyBookMark.postValue(result.isEmpty())
        }
    }

}