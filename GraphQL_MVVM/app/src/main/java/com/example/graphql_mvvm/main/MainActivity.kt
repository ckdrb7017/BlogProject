package com.example.graphql_mvvm.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graphql_mvvm.databinding.ActivityMainBinding
import com.example.graphql_mvvm.di.TokenIntercepter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    var mAdapter: MainAdapter? = null

    @Inject
    lateinit var tokenIntercepter: TokenIntercepter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewmodel = viewModel
        }
        setContentView(binding.root)

        binding.lifecycleOwner = this
        val listener = object : ItemClickListener {
            override fun onClick(id: String) {

            }
        }

        mAdapter = MainAdapter(viewModel, listener)
        tokenIntercepter.token = "Y2tkcmI3MDE3QGhhaWkuaW8="
        binding.recyclerView.apply {
            adapter = mAdapter
        }

        viewModel.getItems()
        mAdapter!!.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                (binding.recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(itemCount,0)
            }
        })

    }
}
/*
gradlew :app:downloadApolloSchema -Pcom.apollographql.apollo.endpoint='https://apollo-fullstack-tutorial.herokuapp.com/'
-Pcom.apollographql.apollo.schema='src/main/graphql/com/example/rocketreserver/schema.json'
*/
