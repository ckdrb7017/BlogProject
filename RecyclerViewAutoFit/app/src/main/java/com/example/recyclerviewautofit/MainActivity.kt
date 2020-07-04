package com.example.recyclerviewautofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewautofit.databinding.ActivityMainBinding
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mLayoutManager : LinearLayoutManager
    private lateinit var mainAdapter : MainAdapter

    private var dataList = arrayListOf<MainItemData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataList.add(MainItemData("Title 1","Description 1","https://placeimg.com/250/250/1"))
        dataList.add(MainItemData("Title 2","Description 2","https://placeimg.com/250/250/2"))
        dataList.add(MainItemData("Title 3","Description 3","https://placeimg.com/250/250/3"))
        dataList.add(MainItemData("Title 4","Description 4","https://placeimg.com/250/250/4"))
        dataList.add(MainItemData("Title 5","Description 5","https://placeimg.com/250/250/5"))

        mLayoutManager =  LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mainAdapter = MainAdapter(this)
        binding.recyclerView.apply{
            this.layoutManager = mLayoutManager
            this.adapter = mainAdapter
            this.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE){
                        var firstPos = (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        var secondPos = (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        var selectedPos = max(firstPos,secondPos)
                        if(selectedPos!=-1){
                            var viewItem = (binding.recyclerView.layoutManager as LinearLayoutManager).findViewByPosition(selectedPos)
                            viewItem?.run{
                                var itemMargin = (binding.recyclerView.measuredWidth-viewItem.measuredWidth)/2
                                binding.recyclerView.smoothScrollBy( this.x.toInt()-itemMargin , 0)
                            }

                            binding.titleText.text = mainAdapter.dataList[selectedPos].title
                            binding.descriptionText.text = String.format("%s ",mainAdapter.dataList[selectedPos].description)
                        }
                    }
                }
            })
        }
        mainAdapter.dataList = dataList
        binding.titleText.text = mainAdapter.dataList[0].title
        binding.descriptionText.text = String.format("%s ",mainAdapter.dataList[0].description)
    }
}