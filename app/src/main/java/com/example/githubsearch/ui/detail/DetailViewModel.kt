package com.example.githubsearch.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.data.repository.UserDetailRepository
import com.example.githubsearch.model.UserDetail
import com.example.githubsearch.model.UserRepo
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserDetailRepository(application)

    private val _info = MutableLiveData<UserDetail>()
    val info: LiveData<UserDetail> = _info

    private val _repo = MutableLiveData<List<UserRepo>>()
    val repo : LiveData<List<UserRepo>> = _repo

    fun loadData(userId: String?) {
        viewModelScope.launch {
            val detailData = repository.getUserData(userId)
            val repoData = repository.getRepoData(userId)

            _info.postValue(detailData as UserDetail?)
            _repo.postValue(repoData as List<UserRepo>?)

        }
    }

}