package com.example.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    val login: String,
    val avatarUrl: String?,
    val name: String?
): Parcelable