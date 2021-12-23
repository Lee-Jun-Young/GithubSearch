package com.example.githubsearch.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
            dBinding.detailRecyclerview.adapter = adapter
            lifecycleScope.launch {
                it.collectLatest {
                    adapter.submitData(it)
                }
            }
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