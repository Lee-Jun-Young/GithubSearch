package com.example.githubsearch.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.domain.model.User
import com.example.domain.model.UserDetail
import com.example.domain.model.UserRepo
import com.example.domain.usecase.*
import com.example.githubsearch.presentation.intent.DetailIntent
import com.example.githubsearch.presentation.intent.MainIntent
import com.example.githubsearch.presentation.state.DetailState
import com.example.githubsearch.presentation.state.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getUserDataUseCase: SearchByUserIdUseCase,
    private val getRepoDataUseCase: SearchUserRepoUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val isCheckedBookMark: IsCheckedBookMarkUseCase
) : ViewModel() {

    val detailIntent = Channel<DetailIntent>(Channel.UNLIMITED)

    private val _state = MutableLiveData<DetailState>(DetailState.Idle)
    val state: LiveData<DetailState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            detailIntent.consumeAsFlow().collect {
                when (it) {
                    is DetailIntent.DetailUser -> {
                        loadData(it.userId)
                        isBookMarked(it.userId)
                    }
                    is DetailIntent.AddBookMark -> addBookMark(it.user)
                    is DetailIntent.DeleteBookMark -> deleteBookMark(it.userId)
                }
            }
        }
    }

    private fun loadData(userId: String?) {
        viewModelScope.launch {
            val detailData = getUserDataUseCase(userId.toString())

            _state.value = DetailState.Info(detailData as UserDetail)
        }
        _state.value = DetailState.Repo(getRepoDataUseCase(userId.toString()))
    }

    private fun addBookMark(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavoriteUseCase(user)
        }
    }

    private fun deleteBookMark(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteUseCase(userId)
        }
    }

    private fun isBookMarked(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(DetailState.IsChecked(isCheckedBookMark(userId)))
        }
    }

}