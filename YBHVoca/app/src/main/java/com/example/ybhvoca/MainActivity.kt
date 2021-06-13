package com.example.ybhvoca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ybhvoca.databinding.ActivityMainBinding
import com.example.ybhvoca.databinding.ActivityYbhVocaBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        val fragment = supportFragmentManager.beginTransaction()
        fragment.addToBackStack(null)
        binding.apply {
            button.setOnClickListener {
                var intent = Intent(this@MainActivity, ybhVoca::class.java)
                startActivity(intent)

            }
            button2.setOnClickListener {
                var intent = Intent(this@MainActivity, TestActivity::class.java)
                startActivity(intent)
            }
            button3.setOnClickListener {
                var intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }
}