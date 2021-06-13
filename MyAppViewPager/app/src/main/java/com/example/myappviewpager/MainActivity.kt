package com.example.myappviewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappviewpager.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val iconarr = arrayListOf<Int>(R.drawable.ic_baseline_add_alert_24, R.drawable.ic_baseline_emoji_food_beverage_24)
    val textarr = arrayListOf<String>("이미지", "리스트")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.viewPager.adapter = MyFragmentStateAdapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewPager){
            tab, position ->
            tab.text = textarr[position]
            tab.setIcon(iconarr[position])
        }.attach()
    }
}