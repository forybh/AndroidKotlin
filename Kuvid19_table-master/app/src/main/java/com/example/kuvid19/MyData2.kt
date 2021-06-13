package com.example.kuvid19

data class MyData2(
    val gubun:String,               //구분
    val conf_case: Int,             //확진자
    val conf_case_rate : Float,    //확진률
    val death : Int ,               //사망자
    val death_rate:Float,          //사망률
    val ciritical_rate :Float      //치명률
)