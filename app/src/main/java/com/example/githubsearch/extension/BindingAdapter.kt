package com.example.githubsearch.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object BindingAdapter {

    @BindingAdapter("bind_userImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, avatar_url: String?) {
        Glide.with(imageView.context).load(avatar_url)
            .into(imageView)
    }

}