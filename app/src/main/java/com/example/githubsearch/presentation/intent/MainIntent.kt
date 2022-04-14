package com.example.githubsearch.presentation.intent

sealed class MainIntent {

    object SearchUser : MainIntent()
    object BookMarkUser : MainIntent()

}