package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class SearchByUserIdUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(userId: String) = repository.getUserData(userId)
}