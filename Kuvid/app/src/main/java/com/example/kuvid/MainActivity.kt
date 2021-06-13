package com.example.kuvid

import android.os.Bundle
import android.util.Log
import android.view.PixelCopy.request
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.net.URLDecoder
import java.net.URLEncoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory


class MainActivity : AppCompatActivity() {
    var now = LocalDate.now()
    val scope = CoroutineScope(Dispatchers.IO)
    val key : String = "iTpYyrz%2B2quf9rhgNwrICe%2BksA%2B3VK6%2FQ%2FmWVn9UcOUfwTTVzvEnG%2B8MBYTXU2jlsWAOVIuOsrdsROX5t%2Btmrg%3D%3D"
    var MyDataList = ArrayList<MyData>()

    var date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 00"))

    lateinit var url : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }
    fun init() {
        date = "2021년 06월 08일"
        scope.launch {
            URLDecoder.decode(key, "UTF-8")
            url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=" +
                    key + "&pageNo=1&numOfRows=10&STD_DAY=${date}}"
            val xml: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)
            xml.documentElement.normalize()
            println("Root element : " + xml.documentElement.nodeName)

            //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
            val list: NodeList = xml.getElementsByTagName("item")
            Log.e("lllll", list.length.toString())
            for (i in 0..list.length - 1) {
                var n: Node = list.item(i)
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    val elem = n as Element
                    val map = mutableMapOf<String, String>()

                    for (j in 0..elem.attributes.length - 1) {
                        map.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)
                    }
                    val gubun = elem.getElementsByTagName("gubun").item(0).textContent
                    val def_cnt: Int = elem.getElementsByTagName("defCnt").item(0).textContent.toInt()
                    val iso_ing_cnt : Int = elem.getElementsByTagName("isolIngCnt").item(0).textContent.toInt()
                    val death_cnt : Int = elem.getElementsByTagName("deathCnt").item(0).textContent.toInt()
                    val iso_clear_cnt = elem.getElementsByTagName("isolClearCnt").item(0).textContent.toInt()
                    val inc_dec : Int = elem.getElementsByTagName("incDec").item(0).textContent.toInt()

                    val data:MyData = MyData(gubun, def_cnt, iso_ing_cnt, death_cnt, iso_clear_cnt, inc_dec)
                    MyDataList.add(data)
                    Log.d("data", data.toString())

                }
            }
        }
    }
}




