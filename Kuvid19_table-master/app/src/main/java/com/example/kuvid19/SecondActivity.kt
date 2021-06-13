package com.example.kuvid19

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.net.URLDecoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

public class SecondActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var now = LocalDate.now()
    val scope = CoroutineScope(Dispatchers.IO)
    val key : String = "iTpYyrz%2B2quf9rhgNwrICe%2BksA%2B3VK6%2FQ%2FmWVn9UcOUfwTTVzvEnG%2B8MBYTXU2jlsWAOVIuOsrdsROX5t%2Btmrg%3D%3D"
    var MyDataList = ArrayList<MyData2>()

    @RequiresApi(Build.VERSION_CODES.O)
    val date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 00"))

    lateinit var url:String
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        init()
        printMyData2()
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun init() {

        scope.launch {
            URLDecoder.decode(key, "UTF-8")
            url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19GenAgeCaseInfJson?serviceKey=" +
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
                    val conf_case: Int = elem.getElementsByTagName("confCase").item(0).textContent.toInt()
                    val conf_case_rate  = elem.getElementsByTagName("confCaseRate").item(0).textContent.toFloat()
                    val death : Int = elem.getElementsByTagName("death").item(0).textContent.toInt()
                    val death_rate = elem.getElementsByTagName("deathRate").item(0).textContent.toFloat()
                    val critical_rate  = elem.getElementsByTagName("criticalRate").item(0).textContent.toFloat()

                    val data: MyData2 = MyData2(gubun, conf_case, conf_case_rate, death, death_rate, critical_rate)
                    MyDataList.add(data)
                    Log.d("data", data.toString())

                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun printMyData2() {
        /*
            val gubun:String,               //구분
            val conf_case: Int,             //확진자
            val conf_case_rate : Float,    //확진률
            val death : Int ,               //사망자
            val death_rate:Float,          //사망률
            val ciritical_rate :Float      //치명률
         */

        // MyDataList
        val MyDataList_it = MyDataList.iterator()
        while (MyDataList_it.hasNext()) {
            val data: MyData2 = MyDataList_it.next()

            // 동적 행 추가
            val tableRow = TableRow(this)
            tableRow.layoutParams = MyDataRow.layoutParams

            // 열 레이아웃 설정
            val gubun = TextView(this)
            gubun.layoutParams = t2row1col1.layoutParams
            //gubun.setTextColor(Color.BLACK)
            gubun.setTextSize(13.0F)
            gubun.setTypeface(gubun.typeface, Typeface.NORMAL)

            if(data.gubun.equals("여자") || data.gubun.equals("남자")) gubun.setBackgroundColor(R.color.bgcolor)
            else gubun.setBackgroundResource(R.drawable.border_layout)

            gubun.text = data.gubun
            tableRow.addView(gubun)

            val conf_case = TextView(this)
            conf_case.layoutParams = t2row1col2.layoutParams
            //conf_case.setTextColor(Color.BLACK)
            conf_case.setTextSize(13.0F)
            conf_case.setTypeface(conf_case.typeface, Typeface.NORMAL)

            if(data.gubun.equals("여자") || data.gubun.equals("남자")) conf_case.setBackgroundColor(R.color.bgcolor)
            else conf_case.setBackgroundResource(R.drawable.border_layout)

            conf_case.text = data.conf_case.toString()
            tableRow.addView(conf_case)

            val conf_case_rate = TextView(this)
            conf_case_rate.layoutParams = t2row1col3.layoutParams
            //conf_case_rate.setTextColor(Color.BLACK)
            conf_case_rate.setTextSize(13.0F)
            conf_case_rate.setTypeface(conf_case_rate.typeface, Typeface.NORMAL)

            if(data.gubun.equals("여자") || data.gubun.equals("남자")) conf_case_rate.setBackgroundColor(R.color.bgcolor)
            else conf_case_rate.setBackgroundResource(R.drawable.border_layout)

            conf_case_rate.text = data.conf_case_rate.toString()
            tableRow.addView(conf_case_rate)

            val death = TextView(this)
            death.layoutParams = t2row1col4.layoutParams
            //death.setTextColor(Color.BLACK)
            death.setTextSize(13.0F)
            death.setTypeface(death.typeface, Typeface.NORMAL)

            if(data.gubun.equals("여자") || data.gubun.equals("남자")) death.setBackgroundColor(R.color.bgcolor)
            else death.setBackgroundResource(R.drawable.border_layout)

            death.text = data.death.toString()
            tableRow.addView(death)

            val death_rate = TextView(this)
            death_rate.layoutParams = t2row1col5.layoutParams
            //death_rate.setTextColor(Color.BLACK)
            death_rate.setTextSize(13.0F)
            death_rate.setTypeface(death_rate.typeface, Typeface.NORMAL)

            if(data.gubun.equals("여자") || data.gubun.equals("남자")) death_rate.setBackgroundColor(R.color.bgcolor)
            else death_rate.setBackgroundResource(R.drawable.border_layout)

            death_rate.text = data.death_rate.toString()
            tableRow.addView(death_rate)

            val ciritical_rate = TextView(this)
            ciritical_rate.layoutParams = t2row1col6.layoutParams
            //ciritical_rate.setTextColor(Color.BLACK)
            ciritical_rate.setTextSize(13.0F)
            ciritical_rate.setTypeface(death_rate.typeface, Typeface.NORMAL)

            if(data.gubun.equals("여자") || data.gubun.equals("남자")) ciritical_rate.setBackgroundColor(R.color.bgcolor)
            else ciritical_rate.setBackgroundResource(R.drawable.border_layout)

            ciritical_rate.text = data.ciritical_rate.toString()
            tableRow.addView(ciritical_rate)
        }
    }
}