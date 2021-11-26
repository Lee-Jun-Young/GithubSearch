package com.example.githubsearch.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.ItemRvMainBinding
import com.example.githubsearch.model.User

class MainAdapter : PagingDataAdapter<User,
        MainAdapter.MainViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemRvMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(getItem(position)!!)
    }

    inner class MainViewHolder(private val binding: ItemRvMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            binding.user = user

            itemView.setOnClickListener {
                val userId = user.login
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("userId", userId)
                itemView.context.startActivity(intent)
            }
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