package com.example.githubsearch.model

import com.google.gson.annotations.SerializedName

data class UserRepo(

    @SerializedName("full_name")
    val full_name: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("language")
    val language: String?

)