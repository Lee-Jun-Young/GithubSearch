package com.example.githubsearch.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.databinding.ItemRvDetailBinding
import com.example.githubsearch.model.UserRepo

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.MainViewHolder>() {

    var items = ArrayList<UserRepo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            ItemRvDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(repo: List<UserRepo>){
        val diffResult = DiffUtil.calculateDiff(ContentDiffUtil(items, repo), false)
        diffResult.dispatchUpdatesTo(this)
        items.clear()
        items.addAll(repo)
    }

    inner class MainViewHolder(private val binding: ItemRvDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(repo: UserRepo) {
            binding.repo = repo
        }
    }

    class ContentDiffUtil(private val oldList: List<UserRepo>, private val currentList: List<UserRepo>) : DiffUtil.Callback() {
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == currentList[newItemPosition]
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == currentList[newItemPosition]
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = currentList.size
    }
}