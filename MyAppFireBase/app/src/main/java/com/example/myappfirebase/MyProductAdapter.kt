package com.example.myappfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myappfirebase.databinding.RowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyProductAdapter(options: FirebaseRecyclerOptions<Product>)
    : FirebaseRecyclerAdapter<Product, MyProductAdapter.ViewHolder>(options){

    interface OnItemClickListner{
        fun OnItemClick(view: View, position: Int)
    }
    var itemClickListner:OnItemClickListner? = null
    inner class ViewHolder(val binding: RowBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                itemClickListner!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Product) {
        holder.binding.apply {
            productid.text = model.pId.toString()
            productname.text = model.pName.toString()
            productquantity.text = model.pQuantity.toString()
        }
    }
}