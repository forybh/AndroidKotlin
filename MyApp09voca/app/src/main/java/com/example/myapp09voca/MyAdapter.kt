package com.example.myapp09voca

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    interface OnItemClickLinstener{
        fun OnItemClick(holder:ViewHolder, view:View, data:MyData, position:Int)
    }
    var itemClickLinstener:OnItemClickLinstener?=null
    var flag: MutableList<Boolean> = MutableList<Boolean>(items.size, {i->true})

    fun moveItem(oldPos:Int, newPos:Int) {
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)
        val temp:Boolean = flag[oldPos]
        flag[oldPos] = flag[newPos]
        flag[newPos] = temp
        notifyItemMoved(oldPos, newPos)
    }

    fun removeItem(pos:Int){
        items.removeAt(pos)
        flag.removeAt(pos)
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
        init{
            itemView.setOnClickListener {
                itemClickLinstener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].word
        holder.textView2.text = items[position].meaning

    }
}