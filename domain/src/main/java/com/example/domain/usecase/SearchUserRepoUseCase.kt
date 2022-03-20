package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserRepoUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(userId: String) = repository.getRepoData(userId, 50)
}