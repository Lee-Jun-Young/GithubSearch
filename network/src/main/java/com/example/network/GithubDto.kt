package com.example.network

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)

data class UserDetailDto(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("name")
    val name: String?
)

data class UserRepoDto(
    @SerializedName("full_name")
    val full_name: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("language")
    val language: String?
)

data class UserResponseDto(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<UserDto>
)