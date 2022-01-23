package com.example.githubsearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.databinding.LoadStateItemBinding

class UserLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UserLoadStateAdapter.ReposLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ReposLoadStateViewHolder {
        return ReposLoadStateViewHolder.create(parent, retry)
    }

    class ReposLoadStateViewHolder(
        private val binding: LoadStateItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                if (loadState.error.localizedMessage.isNullOrEmpty())
                    binding.errorMsg.text = "오류가 발생했습니다.\n 잠시후 재시도 버튼을 눌러주십시오."
                else
                    binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.load_state_item, parent, false)
                val binding = LoadStateItemBinding.bind(view)
                return ReposLoadStateViewHolder(binding, retry)
            }
        }

    }

}
