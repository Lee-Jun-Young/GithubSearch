package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(userId: String) = repository.deleteFavorite(userId)
}