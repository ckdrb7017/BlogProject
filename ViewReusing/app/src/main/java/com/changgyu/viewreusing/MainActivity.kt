package com.changgyu.viewreusing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import com.changgyu.viewreusing.databinding.ActivityMainBinding
import com.changgyu.viewreusing.databinding.ViewstubProgressBinding

class MainActivity : AppCompatActivity() {
    
    lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Databinding in <include>
        binding.appbar.appBarText = "Included AppBar"


        // <merge>
        binding.headerText = "Header"
        binding.bodyText = "Body"


        // <viewstub>
        binding.button.setOnClickListener {
            val progressViewStub =  binding.progressViewStub
            var stubBinding: ViewstubProgressBinding? = null
            progressViewStub.setOnInflateListener { stub, inflated ->
                stubBinding = ViewstubProgressBinding.bind(inflated)
            }

            if(!progressViewStub.isInflated){
               progressViewStub.viewStub?.inflate()
            }
        }
    }
}