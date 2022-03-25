package com.example.data.mapper

import com.example.database.UserEntity
import com.example.domain.model.User
import com.example.domain.model.UserDetail
import com.example.domain.model.UserRepo
import com.example.domain.model.UserResponse
import com.example.network.UserDetailDto
import com.example.network.UserDto
import com.example.network.UserRepoDto
import com.example.network.UserResponseDto

fun UserDto.toUser(): User =
    User(
        login,
        avatarUrl
    )

fun UserEntity.toUser(): User =
    User(
        login,
        avatarUrl
    )

fun User.toUserEntity(): UserEntity =
    UserEntity(
        login,
        avatarUrl
    )

fun UserRepoDto.toUserRepo(): UserRepo =
    UserRepo(
        full_name,
        name,
        description,
        language
    )

fun UserDetailDto.toUserDetail(): UserDetail =
    UserDetail(
        login,
        avatarUrl,
        name
    )

fun UserResponseDto.toUserResponse(): UserResponse =
    UserResponse(
        totalCount,
        items.map {
            it.toUser()
        }
    )