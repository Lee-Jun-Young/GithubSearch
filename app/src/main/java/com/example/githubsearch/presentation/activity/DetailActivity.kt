package com.example.githubsearch.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.core.BaseActivity
import com.example.domain.model.User
import com.example.domain.model.UserDetail
import com.example.domain.model.UserRepo
import com.example.githubsearch.BuildConfig
import com.example.githubsearch.MyApplication
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityDetailBinding
import com.example.githubsearch.presentation.adapter.DetailAdapter
import com.example.githubsearch.presentation.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailActivity : BaseActivity<ActivityDetailBinding>({ ActivityDetailBinding.inflate(it) }),
    View.OnClickListener {

    @Inject
    lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.detail = this@DetailActivity

        initView()
        initObservers()
    }

    override fun onBackPressed() {
        if (binding.resultView.visibility == View.GONE)
            binding.resultView.visibility = View.VISIBLE
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

    private fun initView() {
        val str = intent.getStringExtra("userId")
        detailViewModel.loadData(str)
    }

    private fun initObservers() {
        detailViewModel.info.observe(this) {
            binding.userInfo = it
        }

        detailViewModel.repo.observe(this) {
            val adapter = DetailAdapter(::itemOnClick)
            binding.detailRecyclerview.adapter = adapter
            lifecycleScope.launch {
                it.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun itemOnClick(userRepo: UserRepo) {
        binding.resultView.visibility = View.GONE
        binding.webView.loadUrl(BuildConfig.WEBVIEW_BASE_URL + userRepo.full_name)
    }

    fun isBookMarkClicked(userDetail: UserDetail) {
        val info = User(userDetail.login, userDetail.avatarUrl)
        if (binding.cbLike.isChecked) {
            detailViewModel.addBookMark(info)
        } else {
            detailViewModel.deleteBookMark(info.login)
        }
    }

}