package com.example.myappviewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myappfragment.ImageFragment
import com.example.myappfragment.ItemFragment

class MyFragmentStateAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->ImageFragment()
            1->ItemFragment()
            else->ImageFragment()
        }
    }


}