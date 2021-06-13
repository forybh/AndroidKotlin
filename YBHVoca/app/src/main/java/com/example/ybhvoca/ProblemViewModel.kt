package com.example.ybhvoca

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProblemViewModel : ViewModel() {
    val selectednum = MutableLiveData<MyData>()
    fun setLiveData(data:MyData){
        selectednum.value = data
    }
}