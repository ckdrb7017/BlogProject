package com.example.activitylifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.activitylifecycle.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG","MainActivity onCreate()")

        next_button.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG","MainActivity onStart()")
   }

    override fun onRestart() {
        super.onRestart()
        Log.d("TAG","MainActivity onRestart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG","MainActivity onResume()")
   }

    override fun onPause() {
        super.onPause()
        Log.d("TAG","MainActivity onPause()")
   }

    override fun onStop() {
        super.onStop()
        Log.d("TAG","MainActivity onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG","MainActivity onDestroy()")
   }

}
