package com.example.myapp12intentlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp12intentlist.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var adapter:MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }


    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(ArrayList<MyData>())

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val applist = packageManager.queryIntentActivities(intent, 0)
        if(applist.size>0) {
            for(appinfo in applist){
                val applabel = appinfo.loadLabel(packageManager)
                val appclass = appinfo.activityInfo.name
                val apppackagename = appinfo.activityInfo.packageName
                val appicon = appinfo.loadIcon(packageManager)
                adapter.items.add(MyData(applabel.toString(), appclass, apppackagename, appicon))
            }
        }

        adapter.itemClickLinstener = object : MyAdapter.OnItemClickLinstener{
            override fun OnItemClick(
                holder: MyAdapter.ViewHolder,
                view: View,
                data: MyData,
                position: Int
            ) {
                val intent = packageManager.getLaunchIntentForPackage(data.appackname)
                startActivity(intent)
            }

        }
        binding.recyclerView.adapter = adapter
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
                adapter.removeItem(viewHolder.adapterPosition)

            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

}