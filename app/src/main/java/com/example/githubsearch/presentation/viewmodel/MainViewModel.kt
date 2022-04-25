package com.example.githubsearch.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import com.example.domain.usecase.GetFavoritesUseCase
import com.example.domain.usecase.SearchUserUseCase
import com.example.githubsearch.presentation.intent.MainIntent
import com.example.githubsearch.presentation.state.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserListUseCase: SearchUserUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) :
    ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableLiveData<MainState>(MainState.Idle)
    val state: LiveData<MainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.SearchUser -> getUserId(it.userId)
                    is MainIntent.BookMarkUser -> getFavorites()
                }
            }
        }
    }

    private fun getUserId(searchId: String) {
        if (searchId.isBlank()) {
            _state.value = MainState.IsBlank(searchId.isBlank())
        } else {
            _state.value = MainState.SearchUser(getUserListUseCase(searchId.toString()))
        }
    }

    private suspend fun getFavorites() {
        _state.value = MainState.BookMarkUser(getFavoritesUseCase())
    }

}