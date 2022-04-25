package com.example.githubsearch.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.User
import com.example.githubsearch.MyApplication
import com.example.githubsearch.presentation.composable.Scaffold
import com.example.githubsearch.presentation.intent.MainIntent
import com.example.githubsearch.presentation.viewmodel.MainViewModel
import com.google.accompanist.appcompattheme.AppCompatTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContent {
            AppCompatTheme {
                Scaffold(
                    mainViewModel = mainViewModel,
                    onClick = ::itemOnClick
                )
            }
        }
    }

    private fun itemOnClick(user: User) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("userId", user.login)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        )
    }

}
