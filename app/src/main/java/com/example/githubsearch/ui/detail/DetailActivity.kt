package com.example.githubsearch.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.githubsearch.BuildConfig
import com.example.githubsearch.MyApplication
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityDetailBinding
import com.example.githubsearch.model.UserRepo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dBinding: ActivityDetailBinding

    @Inject
    lateinit var detailViewModel: DetailViewModel
    private lateinit var adapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        dBinding.lifecycleOwner = this
        dBinding.detail = this@DetailActivity

        (application as MyApplication).appComponent.inject(this)
        /*
        detailViewModel = ViewModelProvider(this, ViewModelFactory(UserRepositoryImpl()))
            .get(DetailViewModel::class.java)
        */
        val str = intent.getStringExtra("userId")
        detailViewModel.loadData(str)

        initObservers()
    }

    private fun initObservers() {
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
        dBinding.webView.loadUrl(BuildConfig.WEBVIEW_BASE_URL + userRepo.full_name)
    }

    override fun onBackPressed() {
        if (dBinding.resultView.visibility == View.GONE)
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