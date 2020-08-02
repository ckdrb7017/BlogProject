package com.example.graphql_mvvm.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql_mvvm.databinding.MainItemBinding
import com.example.rocketreserver.LaunchListQuery

class MainAdapter(private val viewModel: MainViewModel,val itemClickListener: ItemClickListener)
    : ListAdapter<LaunchListQuery.Launch, RecyclerView.ViewHolder>(LaunchDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int,payload:List<Any>) {
        Log.d("TAG","onBindViewHolder")
        val item = getItem(position)
        when(holder){
            is MainViewHolder ->{
               holder.bind(viewModel,item)
               holder.binding.idTextView.setOnClickListener {
                   itemClickListener.onClick(item.id)
               }
            }
        }

    }

    class MainViewHolder (val binding : MainItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(viewModel: MainViewModel, item: LaunchListQuery.Launch){
            binding.idTextView.text = item.id
            binding.viewmodel = viewModel
            binding.launch = item
            binding.executePendingBindings()

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }
}

class LaunchDiffCallback : DiffUtil.ItemCallback<LaunchListQuery.Launch>() {
    override fun areItemsTheSame(oldItem: LaunchListQuery.Launch, newItem: LaunchListQuery.Launch): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LaunchListQuery.Launch, newItem: LaunchListQuery.Launch): Boolean {
        return oldItem == newItem
    }

}