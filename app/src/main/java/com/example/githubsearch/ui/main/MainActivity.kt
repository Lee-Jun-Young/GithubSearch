package com.example.githubsearch.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.extension.NetworkConnection
import com.example.githubsearch.model.User
import com.example.githubsearch.ui.detail.DetailActivity
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
            mainViewModel.getUserId()
            mBinding.refreshLayout.isRefreshing = false
        }


        initObservers()
    }

    private fun itemOnClick(user: User) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("userId", user.login)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        )
    }

    private fun initObservers() {

        val connection = NetworkConnection(applicationContext)
        connection.observe(this) { isConnected ->
            if (isConnected) {
                mBinding.layoutDisconnected.visibility = View.GONE
            } else {
                mBinding.emptyView.visibility = View.GONE
                mBinding.layoutDisconnected.visibility = View.VISIBLE
            }
        }

        mainViewModel.isBlank.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        mainViewModel.isEmpty.observe(this) { isEmpty ->
            if (isEmpty && connection.value == true) {
                mBinding.emptyView.visibility = View.VISIBLE
            } else if(isEmpty && connection.value == false){
                mBinding.emptyView.visibility = View.GONE
            }else{
                mBinding.emptyView.visibility = View.VISIBLE
            }
        }

        mainViewModel.data.observe(this) {
            adapter = MainAdapter(::itemOnClick).apply {
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