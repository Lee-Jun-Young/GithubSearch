package com.example.domain.datasource.local

import com.example.domain.model.User

interface FavoriteLocalDataSource {

    suspend fun getFavorites(): List<User>

    fun addFavorite(user: User)

    fun deleteFavorite(userId: String)

    fun isCheckFavorite(userId: String): Boolean
}