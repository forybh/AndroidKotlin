package com.example.myapp09voca

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class IntroActivity : AppCompatActivity() {
    val ADD_VOC_REQUEST = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        init()
    }

    private fun init() {
        val btn1 = findViewById<Button>(R.id.button)
        val btn2  = findViewById<Button>(R.id.button2)
        btn1.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn2.setOnClickListener {
            var intent = Intent(this, AddVocActivity::class.java)
            startActivityForResult(intent, ADD_VOC_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            ADD_VOC_REQUEST -> {
                if(resultCode== Activity.RESULT_OK) {
                    val str = data?.getSerializableExtra("voc") as MyData
                    Toast.makeText(this, str.word + " 단어 추가됨", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}