package com.example.graphql_mvvm.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.graphql_mvvm.main.MainAdapter
import com.example.rocketreserver.LaunchListQuery

object AdapterBinder {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(view: RecyclerView, items: List<LaunchListQuery.Launch>?) {
        (view.adapter as MainAdapter).submitList(items)
    }


    @BindingAdapter("app:image")
    @JvmStatic
    fun setImage(view: ImageView, url: String?) {
        Glide.with(view.context).load(url).override(1000, 1000).into(view)
    }
}
