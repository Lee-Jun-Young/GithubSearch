package com.example.githubsearch.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
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

        initEditTextAdd()
        initRecyclerview()
        initObservers()
    }

    private fun initRecyclerview() {
        adapter = MainAdapter()
        mBinding.mainRecyclerview.adapter = adapter
    }

    private fun initObservers() {
        mainViewModel.isEmpty.observe(this) {
            if(it){
                mBinding.refreshLayout.visibility = View.GONE
                mBinding.emptyView.visibility = View.VISIBLE
            }else{
                mBinding.refreshLayout.visibility = View.VISIBLE
                mBinding.emptyView.visibility = View.GONE
            }
        }

        mainViewModel.data.observe(this) {
            adapter.apply {
                addLoadStateListener { state ->
                    mainViewModel.setUsersLoadState(
                        if (state.refresh is LoadState.NotLoading && itemCount == 0) null else state
                    )
                }
            }

            lifecycleScope.launch {
                it.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun initEditTextAdd() {
        mBinding.etSearchId.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mainViewModel.getUserId()
            }
            false
        }
    }

}