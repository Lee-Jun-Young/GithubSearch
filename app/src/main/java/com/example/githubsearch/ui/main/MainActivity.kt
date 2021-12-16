package com.example.githubsearch.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.mainVm = mainViewModel
        mBinding.lifecycleOwner = this@MainActivity

        mBinding.refreshLayout.setOnRefreshListener {
            mBinding.refreshLayout.isRefreshing = false
        }

        initObservers()
    }

    private fun initObservers() {
        mainViewModel.isBlank.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        mainViewModel.data.observe(this) {
            adapter = MainAdapter().apply {
                addLoadStateListener { state ->
                    mainViewModel.setUsersLoadState(
                        if (state.refresh is LoadState.NotLoading && itemCount == 0) null else state
                    )
                }
            }

            mBinding.mainRecyclerview.adapter = adapter

            lifecycleScope.launch {
                it.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

}