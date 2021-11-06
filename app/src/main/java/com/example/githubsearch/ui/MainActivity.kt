package com.example.githubsearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.githubsearch.R
import com.example.githubsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.main = this@MainActivity

        mBinding.refreshLayout.setOnRefreshListener {
            mBinding.refreshLayout.isRefreshing = false
            initObserve()
        }

        initEditTextAdd()
        initObserve()
    }

    private fun initObserve() {
        mainViewModel.data.observe(this) {
            adapter = MainAdapter(it)
            mBinding.mainRecyclerview.adapter = adapter
        }
    }

    private fun initEditTextAdd() {
        mBinding.etSearchId.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val temp = mBinding.etSearchId.text.toString()
                mainViewModel.getUserId(temp)
            }
            false
        }
    }
}