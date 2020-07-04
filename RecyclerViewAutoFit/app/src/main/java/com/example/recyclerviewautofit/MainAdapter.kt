package com.example.recyclerviewautofit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewautofit.databinding.MainItemBinding

class MainAdapter(private val context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mContext=context

    var dataList = listOf<MainItemData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = MainItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MainMusicHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MainMusicHolder -> {
                holder.bind(dataList[position])
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads:List<Any>) {
        when(holder) {
            is MainMusicHolder -> {
                holder.bind(dataList[position])
            }
        }
    }


    override fun getItemCount(): Int = dataList.size

    inner class MainMusicHolder(val binding : MainItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MainItemData) {

            Glide.with(mContext)
                .load(data.profileImage)
                .centerInside()
                .override(1000,1000)
                .into(binding.itemImage)
            if(adapterPosition==0 || adapterPosition == dataList.size-1){
                binding.itemLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

                val displayMetrics = mContext.resources.displayMetrics
                val screenWidth = displayMetrics.widthPixels
                var mLayoutParam : RecyclerView.LayoutParams =  binding.itemLayout.layoutParams as RecyclerView.LayoutParams
                if(adapterPosition == 0)
                    mLayoutParam.leftMargin = (screenWidth - binding.itemLayout.measuredWidthAndState)/2
                else
                    mLayoutParam.rightMargin = (screenWidth - binding.itemLayout.measuredWidthAndState)/2
            }
        }

    }



}