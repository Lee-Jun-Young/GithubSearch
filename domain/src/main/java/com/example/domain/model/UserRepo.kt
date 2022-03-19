package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepo(
    val full_name: String,
    val name: String,
    val description: String?,
    val language: String?
): Parcelable