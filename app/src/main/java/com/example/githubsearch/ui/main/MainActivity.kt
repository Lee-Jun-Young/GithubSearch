package com.example.githubsearch.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.githubsearch.MyApplication
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.model.User
import com.example.githubsearch.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        (application as MyApplication).appComponent.inject(this)

        /*
        mainViewModel = ViewModelProvider(this, ViewModelFactory(UserRepositoryImpl()))
            .get(MainViewModel::class.java)
        */

        mBinding.mainVm = mainViewModel
        mBinding.lifecycleOwner = this@MainActivity

        mBinding.refreshLayout.setOnRefreshListener {
            //adapter.refresh()
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

        mainViewModel.isBlank.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        mainViewModel.data.observe(this) {
            val adapter = MainAdapter(::itemOnClick).apply {
                addLoadStateListener { state ->
                    mainViewModel.setUsersLoadState(
                        if (state.refresh is LoadState.NotLoading && itemCount == 0) null else state
                    )
                }
                mBinding.mainRecyclerview.adapter =
                    withLoadStateFooter(UserLoadStateAdapter(::retry))
            }

            lifecycleScope.launch {
                it.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

    }

}