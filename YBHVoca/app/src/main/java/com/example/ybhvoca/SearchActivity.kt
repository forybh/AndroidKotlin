package com.example.ybhvoca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.ybhvoca.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() {
        val webView: WebView = findViewById<WebView>(R.id.webView)
        var url = "https://dic.daum.net/search.do?q="
        var word = ""

        binding.apply {
            inputBtn2.setOnClickListener {
                word = editTextSearch.text.toString()
                val searchUrl = url + word
                webView.loadUrl(searchUrl)
            }
        }
    }
}