package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * from user_data")
    suspend fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(userEntity: UserEntity)

    @Query("DELETE FROM user_data WHERE login = :login")
    fun deleteData(login: String)
}