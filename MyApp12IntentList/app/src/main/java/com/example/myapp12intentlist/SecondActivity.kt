package com.example.myapp12intentlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun OnGetAppList(view : View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}