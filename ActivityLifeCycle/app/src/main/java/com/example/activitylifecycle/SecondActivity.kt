package com.example.activitylifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.d("TAG","SecondActivity onCreate()")

        finish_button.setOnClickListener {
           finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG","SecondActivity onStart()")
   }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG","SecondActivity onRestart()")
  }

    override fun onResume() {
        super.onResume()
        Log.d("TAG","SecondActivity onResume()")
   }

    override fun onPause() {
        super.onPause()
        Log.d("TAG","SecondActivity onPause()")
   }

    override fun onStop() {
        super.onStop()
        Log.d("TAG","SecondActivity onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG","SecondActivity onDestroy()")
    }
}