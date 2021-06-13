package com.example.kuvid

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList


class ChartWeekFragment : Fragment() {
    var now = LocalDate.now()
    val past = now.minusDays(7)
    val scope = CoroutineScope(Dispatchers.IO)
    val key_area : String = "iTpYyrz%2B2quf9rhgNwrICe%2BksA%2B3VK6%2FQ%2FmWVn9UcOUfwTTVzvEnG%2B8MBYTXU2jlsWAOVIuOsrdsROX5t%2Btmrg%3D%3D"
    var MyDataList = ArrayList<MyData>()
    val date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    val past_date = past.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    val lineDataList = ArrayList<Entry>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_chart_week, container, false)
        setLineChart(rootView as ViewGroup)
        return rootView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun setLineChart(rootView:ViewGroup) {
        do {

        } while(lineDataList.size < 7)
        val lineChart:com.github.mikephil.charting.charts.LineChart = rootView.findViewById(R.id.lineChart)
        val xAxis =  lineChart.xAxis
        val dateList = ArrayList<String>()
        for(i in 0..7){
            val temp = past.plusDays(i.toLong())
            dateList.add(temp.format(DateTimeFormatter.ofPattern("MM-dd")))
            Log.d("date", temp.toString())
        }
        Log.d("date", dateList.toString())
        xAxis.apply {
            labelCount = 7
            valueFormatter = IndexAxisValueFormatter(dateList)
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 0.5f
            granularity = 1f
            setDrawGridLines(false)
        }
        lineChart.apply {
            axisRight.isEnabled = false
            legend.apply {
                textSize = 15f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(true)
            }
        }
        lineChart.apply{
            setPinchZoom(false)
            setDrawGridBackground(false)
            setDrawBorders(false)
            setTouchEnabled(false)
            description.isEnabled = false
            isDoubleTapToZoomEnabled = false
        }
        val lineDataSet = LineDataSet(lineDataList, "7일 확진자 증감수")
        lineDataSet.color = Color.parseColor("#304567")
        lineDataSet.setCircleColor(Color.parseColor("#FFE91E63"))
        lineDataSet.formSize = 10f
        lineDataSet.valueTextSize = 12f
        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.invalidate()
        lineChart.animateY(1000)



    }
    fun init() {
        scope.launch {
            //URLDecoder.decode(key, "UTF-8")
            val area_url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=" +
                    key_area + "&pageNo=1&numOfRows=10&startCreateDt=${past_date}&endCreateDt=${date}"
            val area_xml: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(area_url)

            area_xml.documentElement.normalize()
            println("Root element : " + area_xml.documentElement.nodeName)


            //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
            val request : NodeList = area_xml.getElementsByTagName("item")
            Log.e("lllll", request.length.toString())

            for(i in 0..request.length-1) {
                var n: Node = request.item(i)
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
                    if(gubun == "합계") {
                        MyDataList.add(data)
                        lineDataList.add(Entry(i.toFloat(), inc_dec.toFloat()))
                        Log.d("data", data.toString())
                    }
                }
            }
        }
    }

}