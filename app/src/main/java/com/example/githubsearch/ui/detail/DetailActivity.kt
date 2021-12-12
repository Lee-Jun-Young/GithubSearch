package com.example.githubsearch.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.githubsearch.R
import com.example.githubsearch.data.remote.RetrofitBuilder
import com.example.githubsearch.data.remote.api.GithubApi
import com.example.githubsearch.databinding.ActivityDetailBinding
import com.example.githubsearch.model.UserRepo
import com.example.githubsearch.ui.main.MainAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dBinding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private var adapter = DetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        dBinding.lifecycleOwner = this
        dBinding.detail = this@DetailActivity

        val str = intent.getStringExtra("userId")
        detailViewModel.loadData(str)

        detailViewModel.info.observe(this) {
            dBinding.userInfo = it
        }

        detailViewModel.repo.observe(this) {
            adapter = DetailAdapter()
            adapter.notifyDataSetChanged()
            dBinding.detailRecyclerview.adapter = adapter
            adapter.setList(it)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_moveBack -> {
                finish()
            }
        }
    }

}