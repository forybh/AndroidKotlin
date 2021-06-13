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


public class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var now = LocalDate.now()
    val scope = CoroutineScope(Dispatchers.IO)
    val key : String = "iTpYyrz%2B2quf9rhgNwrICe%2BksA%2B3VK6%2FQ%2FmWVn9UcOUfwTTVzvEnG%2B8MBYTXU2jlsWAOVIuOsrdsROX5t%2Btmrg%3D%3D"
    var MyDataList = ArrayList<MyData>()

    @RequiresApi(Build.VERSION_CODES.O)
    val date = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 00"))

    lateinit var url : String
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        println("testtesttest!!!!:called you")
        printMyData()
        println("the end!")
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun init() {

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

                    val data: MyData = MyData(gubun, def_cnt, iso_ing_cnt, death_cnt, iso_clear_cnt, inc_dec)
                    MyDataList.add(data)
                    Log.d("data", data.toString())

                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun printMyData() {
        println("testtesttest!!!!:calling me")
        /*
            val gubun : String,         //지역
            val def_cnt: Int,           //확진자수
            val iso_ing_cnt : Int,      //격리자수
            val death_cnt : Int,        //사망자
            val iso_clear_cnt:Int,      //격리해제수
            val inc_dec : Int           //전일대비 증감
         */



        // MyDataList
        val stIdx = 0 // 해외 유입 따로 빼놓을거얌
        val endIdx = MyDataList.size // 합계 따로 빼놓을거얌
        var idx = 0

       val MyDataList_it = MyDataList.iterator()

        while(MyDataList_it.hasNext()) {
            val data: MyData = MyDataList_it.next()

            // 동적 행 추가
            val tableRow = TableRow(this)
            tableRow.layoutParams = MyDataRow.layoutParams
            MyData1Table.addView(tableRow, idx+1)

            // 열 레이아웃 설정
            val gubun = TextView(this)
            gubun.layoutParams = row1col1.layoutParams
            //gubun.setTextColor(Color.BLACK)
            gubun.setTextSize(10.0F)
            gubun.setTypeface(gubun.typeface, Typeface.NORMAL)

            gubun.setBackgroundResource(R.drawable.border_layout)
            // 해외 유입인 경우 : stIdx or 합계인 경우
            if(stIdx == idx || endIdx == idx) gubun.setBackgroundColor(R.color.bgcolor)

            gubun.text = data.gubun
            tableRow.addView(gubun)

            val def_cnt = TextView(this)
            def_cnt.layoutParams = row1col2.layoutParams
            //def_cnt.setTextColor(Color.BLACK)
            def_cnt.setTextSize(10.0F)
            def_cnt.setTypeface(def_cnt.typeface, Typeface.NORMAL)
            def_cnt.setBackgroundResource(R.drawable.border_layout)
            // 해외 유입인 경우 : stIdx or 합계인 경우
            if(stIdx == idx || endIdx == idx) def_cnt.setBackgroundColor(R.color.bgcolor)

            def_cnt.text = data.def_cnt.toString()
            tableRow.addView(def_cnt)


            val iso_ing_cnt = TextView(this)
            iso_ing_cnt.layoutParams = row1col3.layoutParams
            //iso_ing_cnt.setTextColor(Color.BLACK)
            iso_ing_cnt.setTextSize(10.0F)
            iso_ing_cnt.setTypeface(iso_ing_cnt.typeface, Typeface.NORMAL)
            iso_ing_cnt.setBackgroundResource(R.drawable.border_layout)
            // 해외 유입인 경우 : stIdx or 합계인 경우
            if(stIdx == idx || endIdx == idx) iso_ing_cnt.setBackgroundColor(R.color.bgcolor)

            iso_ing_cnt.text = data.iso_ing_cnt.toString()
            tableRow.addView(iso_ing_cnt)

            val death_cnt = TextView(this)
            death_cnt.layoutParams = row1col4.layoutParams
            //death_cnt.setTextColor(Color.BLACK)
            death_cnt.setTextSize(10.0F)
            death_cnt.setTypeface(death_cnt.typeface, Typeface.NORMAL)
            death_cnt.setBackgroundResource(R.drawable.border_layout)
            // 해외 유입인 경우 : stIdx or 합계인 경우
            if(stIdx == idx || endIdx == idx) death_cnt.setBackgroundColor(R.color.bgcolor)

            death_cnt.text = data.death_cnt.toString()
            tableRow.addView(death_cnt)

            val iso_clear_cnt = TextView(this)
            iso_clear_cnt.layoutParams = row1col5.layoutParams
            //iso_clear_cnt.setTextColor(Color.BLACK)
            iso_clear_cnt.setTextSize(10.0F)
            iso_clear_cnt.setTypeface(iso_clear_cnt.typeface, Typeface.NORMAL)
            iso_clear_cnt.setBackgroundResource(R.drawable.border_layout)
            // 해외 유입인 경우 : stIdx or 합계인 경우
            if(stIdx == idx || endIdx == idx) iso_clear_cnt.setBackgroundColor(R.color.bgcolor)

            iso_clear_cnt.text = data.iso_clear_cnt.toString()
            tableRow.addView(iso_clear_cnt)

            val inc_dec = TextView(this)
            inc_dec.layoutParams = row1col6.layoutParams
            //inc_dec.setTextColor(Color.BLACK)
            inc_dec.setTextSize(10.0F)
            inc_dec.setTypeface(inc_dec.typeface, Typeface.NORMAL)
            inc_dec.setBackgroundResource(R.drawable.border_layout_last_column)
            // 해외 유입인 경우 : stIdx or 합계인 경우
            if(stIdx == idx || endIdx == idx) inc_dec.setBackgroundColor(R.color.bgcolor)

            inc_dec.text = data.inc_dec.toString()
            tableRow.addView(inc_dec)

            idx++
        }


    }
}