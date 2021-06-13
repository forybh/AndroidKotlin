package com.example.kuvid19

data class MyData(
    val gubun : String,         //지역
    val def_cnt: Int,           //확진자수
    val iso_ing_cnt : Int,          //격리자수
    val death_cnt : Int,        //사망자
    val iso_clear_cnt:Int,      //격리해제수
    val inc_dec : Int           //전일대비 증감
)