package com.example.githubsearch.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.core.BaseActivity
import com.example.domain.model.User
import com.example.githubsearch.MyApplication
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.presentation.adapter.MainAdapter
import com.example.githubsearch.presentation.adapter.UserLoadStateAdapter
import com.example.githubsearch.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>({
    ActivityMainBinding.inflate(it)
}) {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding.mainVm = mainViewModel
        binding.lifecycleOwner = this@MainActivity

        initView()
        initScrollListener()
        initObservers()
    }

    private fun initView() {
        binding.refreshLayout.setOnRefreshListener {
            mainViewModel.getUserId()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun itemOnClick(user: User) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("userId", user.login)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        )
    }

    private fun initScrollListener() {
        var temp = 0
        binding.mainRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (temp == 1) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy != 0) binding.etSearchId.clearFocus()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                temp = 1
            }
        })
    }

    private fun initObservers() {

        mainViewModel.isBlank.observe(this) {
            if (it)
                Toast.makeText(this, getString(R.string.main_userId_null), Toast.LENGTH_SHORT)
                    .show()
        }

        mainViewModel.data.observe(this) {
            val adapter = MainAdapter(::itemOnClick).apply {
                addLoadStateListener { state ->
                    mainViewModel.setUsersLoadState(
                        if (state.refresh is LoadState.NotLoading && itemCount == 0) null else state
                    )
                }
                binding.mainRecyclerview.adapter =
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