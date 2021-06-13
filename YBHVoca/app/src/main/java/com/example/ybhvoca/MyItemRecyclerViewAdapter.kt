package com.example.ybhvoca

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ybhvoca.databinding.FragmentItemBinding

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class MyItemRecyclerViewAdapter(
    var values: ArrayList<MyData>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.e("onCreateViewHolder", "g")
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.wordText.text = values[position].word
        holder.meanText.text = values[position].meaning
    }

    override fun getItemCount(): Int = values.size

    fun getItem(position: Int): MyData {
        return values[position]
    }

    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, view:View, data:MyData, position:Int)
    }
    var itemClickListener:OnItemClickListener?= null

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val wordText = itemView.findViewById<TextView>(R.id.wordText)
        val meanText = itemView.findViewById<TextView>(R.id.meanText)
        init {
            itemView.setOnClickListener {
                Log.e("OnItemClick", itemClickListener.toString())
                itemClickListener?.OnItemClick(this,it, values[adapterPosition],adapterPosition)
            }
        }
    }
    fun setData(data:ArrayList<MyData>){
        values = data
    }
}



