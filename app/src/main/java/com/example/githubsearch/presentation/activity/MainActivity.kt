package com.example.githubsearch.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.core.BaseActivity
import com.example.domain.model.User
import com.example.githubsearch.MyApplication
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityMainBinding
import com.example.githubsearch.presentation.adapter.FavoriteAdapter
import com.example.githubsearch.presentation.adapter.MainAdapter
import com.example.githubsearch.presentation.adapter.UserLoadStateAdapter
import com.example.githubsearch.presentation.intent.MainIntent
import com.example.githubsearch.presentation.state.MainState
import com.example.githubsearch.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>({
    ActivityMainBinding.inflate(it)
}), View.OnClickListener {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding.mainVm = mainViewModel
        binding.main = this@MainActivity
        binding.lifecycleOwner = this@MainActivity

        initView()
        initObservers()
    }

    private fun initView() {
        binding.refreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.SearchUser)
            }
            binding.refreshLayout.isRefreshing = false
        }

        binding.etSearchId.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) binding.drawableLayout.closeDrawer(Gravity.RIGHT)
            }
    }

    private fun initObservers() {
        mainViewModel.state.observe(this) {
            when (it) {
                is MainState.SearchUser -> renderMainRecyclerView(it)
                is MainState.BookMarkUser -> renderBookMarkRecyclerView(it)
                is MainState.IsBlank -> showToast(it)
                is MainState.IsEmpty -> {
                    if (it.isEmpty) {
                        binding.refreshLayout.visibility = View.GONE
                        binding.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.refreshLayout.visibility = View.VISIBLE
                        binding.emptyView.visibility = View.GONE
                    }
                }
                is MainState.IsBookMarkEmpty -> {
                    if (it.isBookMarkEmpty) {
                        binding.favoriteRecyclerview.visibility = View.GONE
                        binding.emptyBookMarkView.visibility = View.VISIBLE
                    } else {
                        binding.favoriteRecyclerview.visibility = View.VISIBLE
                        binding.emptyBookMarkView.visibility = View.GONE
                    }
                }

            }
        }
    }

    private fun renderMainRecyclerView(mainState: MainState.SearchUser) {
        lifecycleScope.launch {
            val adapter = MainAdapter(::itemOnClick).apply {
                addLoadStateListener { state ->
                    mainViewModel.setUsersLoadState(
                        if (state.refresh is LoadState.NotLoading && itemCount == 0) null else state
                    )
                }
                binding.mainRecyclerview.adapter =
                    withLoadStateFooter(UserLoadStateAdapter(::retry))
            }
            mainState.searchUser.collectLatest { data ->
                adapter.submitData(data)
            }
        }
    }

    private fun renderBookMarkRecyclerView(mainState: MainState.BookMarkUser) {
        val adapter = FavoriteAdapter(::itemOnClick)
        binding.favoriteRecyclerview.adapter = adapter
        adapter.submitList(mainState.bookmarkUser)
    }

    private fun showToast(mainState: MainState.IsBlank) {
        if (mainState.isBlank) {
            Toast.makeText(
                this@MainActivity,
                getString(R.string.main_userId_null),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun itemOnClick(user: User) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("userId", user.login)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        )
        binding.drawableLayout.closeDrawer(Gravity.RIGHT)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_list -> {
                if (!binding.drawableLayout.isDrawerOpen(Gravity.RIGHT)) {
                    if (binding.etSearchId.isFocused) {
                        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        manager.hideSoftInputFromWindow(
                            currentFocus!!.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                    }
                    lifecycleScope.launch {
                        mainViewModel.userIntent.send(MainIntent.BookMarkUser)
                    }
                    binding.etSearchId.clearFocus()
                    binding.drawableLayout.openDrawer(Gravity.RIGHT)
                } else {
                    binding.drawableLayout.closeDrawer(Gravity.RIGHT)
                }
            }
        }
    }

}