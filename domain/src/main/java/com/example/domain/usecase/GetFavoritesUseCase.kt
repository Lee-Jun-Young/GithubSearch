package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke() = repository.getFavorites()
}