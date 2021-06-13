package com.example.myrecycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    var data:ArrayList<MyData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
        data.add(MyData(item1,10))
    }
}