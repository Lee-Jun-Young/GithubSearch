package com.example.githubsearch.extension

import android.view.inputmethod.EditorInfo
import android.widget.EditText
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