package com.example.githubsearch.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.repository.UserDetailRepository
import com.example.githubsearch.model.UserDetail
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserDetailRepository(application)

    private val _info = MutableLiveData<UserDetail>()
    val info: LiveData<UserDetail> = _info

    fun loadData(userId: String?) {
        viewModelScope.launch {
            val data = repository.getUserData(userId)

            _info.postValue(data as UserDetail?)
        }
    }

}