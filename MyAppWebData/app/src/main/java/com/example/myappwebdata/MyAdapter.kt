package com.example.myappwebdata

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myappwebdata.databinding.RowBinding

class MyAdapter(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    interface OnItemClickLinstener{
        fun OnItemClick(holder:MyViewHolder, view:View, data:MyData, position:Int)
    }
    var itemClickLinstener:OnItemClickLinstener?=null


    inner class MyViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.newstitle.setOnClickListener {
                itemClickLinstener?.OnItemClick(this,it,items[adapterPosition],adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.newstitle.text = items[position].newstitle

    }
}