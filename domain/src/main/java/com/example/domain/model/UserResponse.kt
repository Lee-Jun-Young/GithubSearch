package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val totalCount: Int,
    val items: List<User>
) : Parcelable