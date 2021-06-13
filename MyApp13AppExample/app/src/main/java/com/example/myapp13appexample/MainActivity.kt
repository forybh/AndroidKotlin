package com.example.myapp13appexample

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    val APPLIST_REQUEST = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onClick(view: View){
        btnAction()
    }
    private fun btnAction() {
        val intent = Intent("com.example.myapp12intentlist")
        if(ActivityCompat.checkSelfPermission(this,"com.example.myapp12intentlist")!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf("com.example.myapp12intentlist"),APPLIST_REQUEST)
        }else{
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==APPLIST_REQUEST) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                btnAction()
            }else{
                finish()
            }
        }
    }

}