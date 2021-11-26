package com.example.githubsearch.ui.main

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubsearch.data.repository.UserRepository
import com.example.githubsearch.model.User
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    private var _data = MutableLiveData<List<User>>()
    val data: LiveData<List<User>> = _data

    fun getUserId(userId: String): Flow<PagingData<User>> {
        return repository.getUserList(userId).cachedIn(viewModelScope)
    }

}