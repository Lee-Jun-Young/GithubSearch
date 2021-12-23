package com.example.githubsearch.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.ItemRvDetailBinding
import com.example.githubsearch.model.UserRepo

class DetailAdapter : PagingDataAdapter<UserRepo, DetailAdapter.MainViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemRvDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(getItem(position)!!)
    }

    inner class MainViewHolder(
        private val binding: ItemRvDetailBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(repo: UserRepo) {
            binding.repo = repo
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UserRepo>() {
            override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
                return oldItem == newItem
            }
        }
    }
}