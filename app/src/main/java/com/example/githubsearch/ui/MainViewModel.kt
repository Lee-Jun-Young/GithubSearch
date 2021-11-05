package com.example.githubsearch.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.repository.UserRepository
import com.example.githubsearch.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    private var _data = MutableLiveData<List<User>>()
    val data: LiveData<List<User>> = _data

    fun getUserId(userId: String) {
        viewModelScope.launch {
            val result = repository.getUserList(userId) as List<User>

            _data.value = result
        }
    }
}