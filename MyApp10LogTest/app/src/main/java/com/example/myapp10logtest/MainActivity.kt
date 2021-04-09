package com.example.myapp10logtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    val MainTag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(MainTag,"OnCreate")
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Log.i(MainTag,"OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(MainTag,"OnResume")

    }

    override fun onStop() {
        super.onStop()
        Log.i(MainTag,"OnStop")
    }

    override fun onPause() {
        super.onPause()
        Log.i(MainTag,"OnPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(MainTag,"OnRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(MainTag,"OnDestroy")
    }
}