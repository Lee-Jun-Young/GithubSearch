package com.example.githubsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearch.data.repository.UserRepository
import com.example.githubsearch.ui.detail.DetailViewModel
import com.example.githubsearch.ui.main.MainViewModel

class ViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(userRepository) as T
            }
            else -> {
                throw IllegalAccessError()
            }
        }
    }
}