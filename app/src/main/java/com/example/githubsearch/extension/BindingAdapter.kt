package com.example.githubsearch.extension

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubsearch.model.UserRepo
import com.example.githubsearch.ui.detail.DetailAdapter


object BindingAdapter {

    @BindingAdapter("bind_userImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, avatar_url: String?) {
        Glide.with(imageView.context).load(avatar_url)
            .into(imageView)
    }

    @BindingAdapter("etAction")
    @JvmStatic
    fun onAction(editText: EditText, execute: () -> Unit){
        editText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                execute.invoke()
            }
            false
        }
    }

}