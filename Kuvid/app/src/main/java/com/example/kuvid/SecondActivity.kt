package com.example.kuvid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuvid.databinding.ActivitySecondBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SecondActivity : AppCompatActivity() {
    var now = LocalDate.now()
    val scope = CoroutineScope(Dispatchers.IO)
    val key : String = "iTpYyrz%2B2quf9rhgNwrICe%2BksA%2B3VK6%2FQ%2FmWVn9UcOUfwTTVzvEnG%2B8MBYTXU2jlsWAOVIuOsrdsROX5t%2Btmrg%3D%3D"
    var MyDataList = ArrayList<MyData2>()

    val date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 00"))
    val chartAgeFragment = ChartAgeFragment()
    val chartSexFragment = ChartSexFragment()
    val chartWeekFragment = ChartWeekFragment()
    lateinit var binding:ActivitySecondBinding
    lateinit var url:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init() {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.addToBackStack(null)
        fragment.replace(R.id.framelayout, chartSexFragment)
        fragment.commit()

//        scope.launch {
//            URLDecoder.decode(key, "UTF-8")
//            url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19GenAgeCaseInfJson?serviceKey=" +
//                    key + "&pageNo=1&numOfRows=10&STD_DAY=${20210608}}"
//            val xml: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)
//            xml.documentElement.normalize()
//            println("Root element : " + xml.documentElement.nodeName)
//
//            //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
//            val list: NodeList = xml.getElementsByTagName("item")
//            Log.e("lllll", list.length.toString())
//            for (i in 0..list.length - 1) {
//                var n: Node = list.item(i)
//                if (n.getNodeType() == Node.ELEMENT_NODE) {
//                    val elem = n as Element
//                    val map = mutableMapOf<String, String>()
//
//                    for (j in 0..elem.attributes.length - 1) {
//                        map.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)
//                    }
//                    val gubun = elem.getElementsByTagName("gubun").item(0).textContent
//                    val conf_case: Int = elem.getElementsByTagName("confCase").item(0).textContent.toInt()
//                    val conf_case_rate  = elem.getElementsByTagName("confCaseRate").item(0).textContent.toFloat()
//                    val death : Int = elem.getElementsByTagName("death").item(0).textContent.toInt()
//                    val death_rate = elem.getElementsByTagName("deathRate").item(0).textContent.toFloat()
//                    val critical_rate  = elem.getElementsByTagName("criticalRate").item(0).textContent.toFloat()
//
//                    val data:MyData2 = MyData2(gubun, conf_case, conf_case_rate, death, death_rate, critical_rate)
//                    MyDataList.add(data)
//                    Log.d("data", data.toString())
//
//                }
//            }
//        }
    }
}