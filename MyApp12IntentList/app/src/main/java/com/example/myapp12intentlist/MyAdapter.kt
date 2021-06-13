package com.example.myapp12intentlist

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp12intentlist.databinding.RowBinding

class MyAdapter(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    interface OnItemClickLinstener{
        fun OnItemClick(holder:ViewHolder, view:View, data:MyData, position:Int)
    }
    var itemClickLinstener:OnItemClickLinstener?=null

    fun moveItem(oldPos:Int, newPos:Int) {
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)
        notifyItemMoved(oldPos, newPos)
    }

    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(var binding: RowBinding) : RecyclerView.ViewHolder(binding.root){

        init{
            binding.root.setOnClickListener {
                itemClickLinstener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            appclass.text = items[position].appclass
            applabel.text = items[position].applabel
            imageView.setImageDrawable(items[position].appicon)
        }
    }
}