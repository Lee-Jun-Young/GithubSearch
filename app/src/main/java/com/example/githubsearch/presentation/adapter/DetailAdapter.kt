package com.example.githubsearch.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.UserRepo
import com.example.githubsearch.databinding.ItemRvDetailBinding

class DetailAdapter(
    private val onClick: (UserRepo) -> Unit
) : PagingDataAdapter<UserRepo, DetailAdapter.DetailViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding =
            ItemRvDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.onBind(getItem(position)!!)
    }

    inner class DetailViewHolder(
        private val binding: ItemRvDetailBinding,
        private val onClick: (UserRepo) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(repo: UserRepo) {
            binding.onClick = onClick
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