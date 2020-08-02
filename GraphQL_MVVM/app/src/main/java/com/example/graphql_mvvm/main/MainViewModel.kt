package com.example.graphql_mvvm.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql_mvvm.repository.DefaultRepository
import com.example.rocketreserver.LaunchListQuery
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    private var _items :MutableLiveData<List<LaunchListQuery.Launch?>> = MutableLiveData(listOf())
    val items : LiveData<List<LaunchListQuery.Launch?>> = _items
    var myItem = arrayListOf<LaunchListQuery.Launch?>()
    var count=0

    fun getItems(){
        viewModelScope.launch {
            val items = repository.getLaunchList()
            //myItem.addAll(items?.data?.launches!!.launches)
            _items.value = items?.data?.launches!!.launches
        }
    }

    fun addItems(){
        viewModelScope.launch {
      /*      myItem.add(LaunchListQuery.Launch("","Id : "+count,"Site : "+count,null))
            _items.value = myItem.toList()*/
            val items = repository.getLaunchList()
            myItem.addAll(items?.data?.launches!!.launches)
            _items.value = myItem.toList()
            count++
        }
    }

    fun removeItems(){
        viewModelScope.launch {
            myItem.removeAt(0)
            _items.value = myItem.toList()
        }
    }
}