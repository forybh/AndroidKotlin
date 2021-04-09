package com.example.myapp09voca

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var data:ArrayList<MyData> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyAdapter
    lateinit var tts:TextToSpeech
    var isTtsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initRecyclerView()
        initTTS()
    }

    private fun initTTS() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })
    }

    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(data)

        adapter.itemClickLinstener = object : MyAdapter.OnItemClickLinstener{
            override fun OnItemClick(
                holder: MyAdapter.ViewHolder,
                view: View,
                data: MyData,
                position: Int
            ) {
                if(adapter.flag[position]) {
                    holder.textView2.visibility = View.VISIBLE
                    adapter.flag[position] = false
                }
                else {
                    holder.textView2.visibility = View.GONE
                    adapter.flag[position] = true
                }

                Toast.makeText(applicationContext, data.meaning, Toast.LENGTH_SHORT).show()
                if(isTtsReady)
                    tts.speak(data.word, TextToSpeech.QUEUE_ADD, null, null)
            }

        }
        recyclerView.adapter = adapter
        val simpleCallback = object:ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or ItemTouchHelper.UP, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.e(" ", viewHolder.adapterPosition.toString())
                adapter.removeItem(viewHolder.adapterPosition)

            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }
    fun readFileScan(scan:Scanner){
        while (scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(word, meaning))
        }
        scan.close()
    }

    private fun initData() {
        try {
            val scan2 = Scanner(openFileInput("out.txt"))
            readFileScan(scan2)
        }catch (e:Exception) {
            Toast.makeText(this,"추가된 단어가 없음", Toast.LENGTH_LONG).show()
        }

        val scan = Scanner(resources.openRawResource(R.raw.words))
        readFileScan(scan)

    }
}