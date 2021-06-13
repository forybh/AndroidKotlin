package com.example.ybhvoca

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ybhvoca.databinding.ActivityYbhVocaBinding
import com.google.firebase.database.*
import java.util.*
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class ItemFragment : Fragment() {
    var data = ArrayList<MyData>()
    private lateinit var rdb: DatabaseReference
    var voca_num = 0
    var adapter:MyItemRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("FragmentOnCreateView","g")
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        init(view)
        Log.d("AdapterData", data.toString())
        return view
    }


    fun init(view:View){
        val recyclerView = view.findViewById(R.id.list) as RecyclerView
        recyclerView.layoutManager =  LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = MyItemRecyclerViewAdapter(data)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        voca_num = (activity as ybhVoca).getPos()
        data = (activity as ybhVoca).getData()
    }

    fun readFileScan(scan:Scanner, del:String){
        data = ArrayList()
        scan.nextLine()
        while (scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            if(word != del){
                data.add(MyData(0 ,word, meaning,0))
            }
        }
        scan.close()
    }
    fun refreshAdapter(){
        val refreshData = ArrayList<MyData>()
        rdb = FirebaseDatabase.getInstance().getReference("MyData/items/$voca_num")
        rdb.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val test = snapshot.children
                test.forEach {
                    val value = it.value
                    val mean = it.child("meaning").value.toString()
                    val word = it.child("word").value.toString()
                    val wrong = it.child("wrong").value.toString().toInt()
                    val voca = it.child("voca").value.toString().toInt()
                    val temp :MyData = MyData(voca, word, mean, wrong)
                    refreshData.add(temp)
                }
            }
        })
        Log.d("refreshData", refreshData.toString())
        adapter?.setData(refreshData)
        adapter?.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var scan = Scanner(resources.openRawResource(R.raw.words0))
        when(voca_num){
            0-> scan = Scanner(resources.openRawResource(R.raw.words0))
            1-> scan = Scanner(resources.openRawResource(R.raw.words1))
            2-> scan = Scanner(resources.openRawResource(R.raw.words2))
        }
        readFileScan(scan, arguments?.getString("delword").toString())
        Log.d("word",arguments?.getString("word").toString())
        Log.d("inword",arguments?.getString("insertword").toString())
        Log.d("delword",arguments?.getString("delword").toString())
        if (arguments != null){
            val voca = arguments?.getInt("voca")
            val wrong = arguments?.getInt("wrong")
            val mean = arguments?.getString("mean")
            val word = arguments?.getString("word")
            val invoca = arguments?.getInt("insertvoca")
            val inwrong = arguments?.getInt("insertwrong")
            val inmean = arguments?.getString("insertmean")
            val inword = arguments?.getString("insertword")

            if(mean!=null){
                data.clear()
                data.add(MyData(voca!!, word!!, mean!!,wrong!!))
            }
            if(inmean!=null){
                data.add(MyData(invoca!!, inword!!, inmean!!, inwrong!!))
            }

        }

    }
}

