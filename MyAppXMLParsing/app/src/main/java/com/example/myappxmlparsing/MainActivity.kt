package com.example.myappxmlparsing

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappwebdata.MyAdapter
import com.example.myappwebdata.MyData
import com.example.myappxmlparsing.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    val url ="https://www.daum.net"
    val rssurl = "http://fs.jtbc.joins.com//RSS/culture.xml"
    val jsonurl = "http://api.icndb.com/jokes/random"
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)
        init()
    }
    fun getjson() {
        scope.launch {
            adapter.items.clear()
            val doc = Jsoup.connect(jsonurl).ignoreContentType(true).get()
            Log.i("json", doc.text())
            val json = JSONObject(doc.text())
            val joke = json.getJSONObject("value")
            val jokestr = joke.getString("joke")
            Log.i("json joke", jokestr)

        }
    }
    fun getrssnews() {
        scope.launch {
            adapter.items.clear()
            val doc = Jsoup.connect(rssurl).parser(Parser.xmlParser()).get()
            val headlines = doc.select("item")
            for(news in headlines){
                adapter.items.add(MyData(news.select("title").text(),news.select("link").text()))
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
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
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(ArrayList<MyData>())
        adapter.itemClickLinstener = object: MyAdapter.OnItemClickLinstener{
            override fun OnItemClick(
                    holder: MyAdapter.MyViewHolder,
                    view: View,
                    data: MyData,
                    position: Int
            ) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adapter.items[position].url))
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter = adapter
        getrssnews()
        getjson()
    }
}