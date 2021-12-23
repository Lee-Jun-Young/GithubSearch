package com.example.githubsearch.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityDetailBinding
import com.example.githubsearch.model.User
import com.example.githubsearch.model.UserRepo
import com.example.githubsearch.ui.main.MainAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dBinding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var adapter : DetailAdapter

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
            adapter = DetailAdapter(::itemOnClick)
            dBinding.detailRecyclerview.adapter = adapter
            lifecycleScope.launch {
                it.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun itemOnClick(userRepo: UserRepo) {
        dBinding.resultView.visibility = View.GONE
        dBinding.webView.loadUrl("https://github.com/" + userRepo.full_name)
    }

    override fun onBackPressed() {
        if(dBinding.resultView.visibility == View.GONE)
            dBinding.resultView.visibility = View.VISIBLE
        else
            super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_moveBack -> {
                finish()
            }
        }
    }

}