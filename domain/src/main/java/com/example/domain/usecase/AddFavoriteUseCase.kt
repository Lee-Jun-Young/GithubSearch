package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(user:User) = repository.addFavorite(user)
}