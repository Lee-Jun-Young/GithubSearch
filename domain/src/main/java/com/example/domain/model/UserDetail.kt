package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    val login: String,
    val avatarUrl: String?,
    val name: String?
): Parcelable