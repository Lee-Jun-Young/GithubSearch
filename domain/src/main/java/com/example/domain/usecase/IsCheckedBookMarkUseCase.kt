package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class IsCheckedBookMarkUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(userId: String): Boolean = repository.isCheckedFavorite(userId)
}