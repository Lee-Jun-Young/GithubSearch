package com.example.githubsearch.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("repos_url")
    val repoUrl: String

)