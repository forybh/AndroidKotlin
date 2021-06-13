package com.example.ybhvoca

import java.io.Serializable

data class MyData(var voca: Int, var word:String, var meaning: String?, var wrong:Int):Serializable {

    constructor():this(0,"none", "none", 0)
}