package com.example.ybhvoca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.ybhvoca.databinding.ActivityYbhVocaBinding
import com.google.firebase.database.*
import java.util.ArrayList

class ybhVoca : AppCompatActivity() {

    lateinit var rdb: DatabaseReference
    lateinit var binding:ActivityYbhVocaBinding
    private var pos = 0
    lateinit var itemFragment:ItemFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("ybhVocaOnCreate","g")
        super.onCreate(savedInstanceState)
        binding = ActivityYbhVocaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        Log.e("ybhVocaInit","g")
        rdb = FirebaseDatabase.getInstance().getReference("MyData/items")
        itemFragment = ItemFragment()
        val fragment = supportFragmentManager.beginTransaction()
        fragment.addToBackStack(null)
        fragment.replace(R.id.frameLayout, itemFragment)
        fragment.commit()
        binding.apply {

            easyBtn.setOnClickListener {
                myBtn.background = ContextCompat.getDrawable(it.context, R.drawable.mybuttonstyle)
                easyBtn.background = ContextCompat.getDrawable(it.context, R.drawable.clicked)
                hardBtn.background = ContextCompat.getDrawable(it.context, R.drawable.mybuttonstyle)
                pos = 0
                itemFragment = ItemFragment()
                var fragment = supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                fragment.replace(R.id.frameLayout, itemFragment)
                fragment.commit()
                clearInput()
            }
            hardBtn.setOnClickListener {
                myBtn.background = ContextCompat.getDrawable(it.context, R.drawable.mybuttonstyle)
                hardBtn.background = ContextCompat.getDrawable(it.context, R.drawable.clicked)
                easyBtn.background = ContextCompat.getDrawable(it.context, R.drawable.mybuttonstyle)
                pos = 1
                itemFragment = ItemFragment()
                var fragment = supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                fragment.replace(R.id.frameLayout, itemFragment)
                fragment.commit()
                clearInput()
            }
            myBtn.setOnClickListener {
                myBtn.background = ContextCompat.getDrawable(it.context, R.drawable.clicked)
                hardBtn.background = ContextCompat.getDrawable(it.context, R.drawable.mybuttonstyle)
                easyBtn.background = ContextCompat.getDrawable(it.context, R.drawable.mybuttonstyle)
                pos = 2
                itemFragment = ItemFragment()
                var fragment = supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                fragment.replace(R.id.frameLayout, itemFragment)
                fragment.commit()
                clearInput()
            }
            insertBtn.setOnClickListener {
                val voca = MyData(pos, wordEdit.text.toString(),meanEdit.text.toString(), 0)
                rdb.child(pos.toString()).child(wordEdit.text.toString()).setValue(voca)
                var args = Bundle()
                args.putString("insertword", wordEdit.text.toString())
                args.putString("insertmean", meanEdit.text.toString())
                args.putInt("insertwrong", 0)
                args.putInt("insertvoca", pos)
                Log.d("findvoca",args.getInt("voca").toString())
                itemFragment = ItemFragment()
                itemFragment.arguments = args
                var fragment = supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                fragment.replace(R.id.frameLayout, itemFragment, "find")
                fragment.commit()
                clearInput()
            }
            findBtn.setOnClickListener {
                var find = MyData()
                val findData = ArrayList<MyData>()
                val query = rdb.child(pos.toString()).orderByKey().equalTo(wordEdit.text.toString())
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (postSnapshot in dataSnapshot.children) {
                            val mean = postSnapshot.child("meaning").value.toString()
                            val word = postSnapshot.child("word").value.toString()
                            val wrong = postSnapshot.child("wrong").value.toString().toInt()
                            val voca = postSnapshot.child("voca").value.toString().toInt()
                            find = MyData(voca, word, mean, wrong)
                            findData.add(find)
                        }
                        var args = Bundle()
                        args.putString("word", find.word)
                        args.putString("mean", find.meaning)
                        args.putInt("wrong", find.wrong)
                        args.putInt("voca", find.voca)
                        Log.d("findvoca",args.getInt("voca").toString())
                        itemFragment = ItemFragment()
                        itemFragment.arguments = args
                        var fragment = supportFragmentManager.beginTransaction()
                        fragment.addToBackStack(null)
                        fragment.replace(R.id.frameLayout, itemFragment, "find")
                        fragment.commit()
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
                clearInput()
            }
            deleteBtn.setOnClickListener {
                var find = MyData()
                val findData = ArrayList<MyData>()
                val query = rdb.child(pos.toString()).orderByKey().equalTo(wordEdit.text.toString())
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (postSnapshot in dataSnapshot.children) {
                            val mean = postSnapshot.child("meaning").value.toString()
                            val word = postSnapshot.child("word").value.toString()
                            val wrong = postSnapshot.child("wrong").value.toString().toInt()
                            val voca = postSnapshot.child("voca").value.toString().toInt()
                            find = MyData(voca, word, mean, wrong)
                            findData.add(find)
                        }
                        var args = Bundle()
                        args.putString("delword", find.word)
                        args.putString("delmean", find.meaning)
                        args.putInt("delwrong", find.wrong)
                        args.putInt("delvoca", find.voca)
                        Log.d("delword",args.getString("delword").toString())
                        FirebaseDatabase.getInstance().getReference("MyData/items").child(pos.toString()).child(find.word).removeValue()
                        itemFragment = ItemFragment()
                        itemFragment.arguments = args
                        var fragment = supportFragmentManager.beginTransaction()
                        fragment.addToBackStack(null)
                        fragment.replace(R.id.frameLayout, itemFragment, "del")
                        fragment.commit()
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
                clearInput()
            }
        }
    }
    fun getPos() :Int {
        Log.e("getPos",pos.toString())
        return pos
    }
    fun getData(): ArrayList<MyData> {
        var data = ArrayList<MyData>()
        val new_rdb = FirebaseDatabase.getInstance().getReference("MyData/items/$pos")
        new_rdb.addListenerForSingleValueEvent(object :ValueEventListener {
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
                    data.add(temp)
                }
            }
        })
        Log.d("vocaData", data.toString())
        return data
    }


    fun clearInput(){
        binding.apply {
            wordEdit.text.clear()
            meanEdit.text.clear()
        }
    }

}