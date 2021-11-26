package com.example.githubsearch.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.githubsearch.R
import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dBinding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        dBinding.detail = this@DetailActivity

        val data = intent.getStringExtra("userId")

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_moveBack -> {
                finish()
            }
        }
    }


}