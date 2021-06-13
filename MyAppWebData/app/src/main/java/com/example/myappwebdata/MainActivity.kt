package com.example.myappwebdata

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappwebdata.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    val url ="https://www.daum.net"
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)
        init()
    }

    fun getnews() {
        scope.launch {
            adapter.items.clear()
            val doc = Jsoup.connect(url).get()
            val headlines = doc.select("ul.list_txt>li>a")
            for(news in headlines){
                adapter.items.add(MyData(news.text(),news.absUrl("href)")))
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }
    private fun init() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            getnews()
        }
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(ArrayList<MyData>())
        adapter.itemClickLinstener = object:MyAdapter.OnItemClickLinstener{
            override fun OnItemClick(
                holder: MyAdapter.MyViewHolder,
                view: View,
                data: MyData,
                position: Int
            ) {
                val intent = Intent(ACTION_VIEW, Uri.parse(adapter.items[position].url))
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter = adapter
        getnews()
    }
}