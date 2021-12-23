package com.example.githubsearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.ItemRvMainBinding
import com.example.githubsearch.model.User

class MainAdapter(
    private val onClick: (User) -> Unit
) : PagingDataAdapter<User, MainAdapter.MainViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemRvMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(getItem(position)!!)
    }

    inner class MainViewHolder(
        private val binding: ItemRvMainBinding,
        private val onClick: (User) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: User) {
            binding.onClick = onClick
            binding.user = user
            binding.executePendingBindings()
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}