package com.changgyu.codegenerate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import printInfo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myInformation = MyInfoTest()
        val myInformation2 = MyInfoTest2()

        myInformation.printInfo()
        myInformation2.printInfo()


    }
}