package com.example.data.datasource.local

import com.example.data.mapper.toUser
import com.example.data.mapper.toUserEntity
import com.example.database.UserDao
import com.example.domain.datasource.local.FavoriteLocalDataSource
import com.example.domain.model.User
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(private val userDao: UserDao) :
    FavoriteLocalDataSource {
    override suspend fun getFavorites(): List<User> {
        return userDao.getAll().map {
            it.toUser()
        }
    }

    override fun addFavorite(user: User) {
        userDao.insertData(user.toUserEntity())
    }

    override fun deleteFavorite(userId: String) {
        userDao.deleteData(userId)
    }

    override fun isCheckFavorite(userId: String): Boolean {
        return userDao.isChecked(userId).isNotEmpty()
    }

}