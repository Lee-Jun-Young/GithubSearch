package com.example.githubsearch.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
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
        mBinding.main = this@MainActivity

        mBinding.refreshLayout.setOnRefreshListener {
            mBinding.refreshLayout.isRefreshing = false
        }

        initEditTextAdd()
        initRecyclerview()
    }

    private fun initRecyclerview() {
        adapter = MainAdapter()
        mBinding.mainRecyclerview.adapter = adapter
    }

    private fun initEditTextAdd() {
        mBinding.etSearchId.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val temp = mBinding.etSearchId.text.toString()
                lifecycleScope.launch {
                    mainViewModel.getUserId(temp).collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
            false
        }
    }

}