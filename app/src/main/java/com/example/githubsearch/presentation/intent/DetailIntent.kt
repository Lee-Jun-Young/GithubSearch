package com.example.githubsearch.presentation.intent

import com.example.domain.model.User

sealed class DetailIntent {

    data class DetailUser(val userId: String) : DetailIntent()
    data class AddBookMark(val user: User) : DetailIntent()
    data class DeleteBookMark(val userId: String) : DetailIntent()

}