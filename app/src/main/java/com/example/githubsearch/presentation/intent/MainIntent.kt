package com.example.githubsearch.presentation.intent

sealed class MainIntent {

    class SearchUser(val userId: String) : MainIntent()
    object BookMarkUser : MainIntent()

}