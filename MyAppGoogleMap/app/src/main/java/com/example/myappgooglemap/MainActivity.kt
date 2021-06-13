package com.example.myappgooglemap

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.myappgooglemap.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var googleMap: GoogleMap
    val loc = LatLng(37.554752, 126.970631)
    val arrLoc = ArrayList<LatLng>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initmap()
        initSpinner()
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,ArrayList<String>())
        adapter.add("Hybrid")
        adapter.add("Normal")
        adapter.add("Satellite")
        adapter.add("Terrain")
        binding.apply {
            spinner.adapter = adapter
            spinner.setSelection(1)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when(p2){
                        0->{
                            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                        }
                        1->{
                            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                        }
                        2->{
                            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                        }
                        3->{
                            googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                        }
                    }
                }

            }
        }
    }

    private fun initmap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
            googleMap.setMinZoomPreference(10.0f)
            googleMap.setMaxZoomPreference(18.0f)
            val option = MarkerOptions()
            option.position(loc)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            option.title("역")
            option.snippet("서울역")
            val mk1 = googleMap.addMarker(option)
            mk1.showInfoWindow()
            initMapListener()
        }
    }

    private fun initMapListener() {
        googleMap.setOnMapClickListener {
            arrLoc.add(it)
            googleMap.clear()
            val option = MarkerOptions()
            option.position(it)
            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            googleMap.addMarker(option)
//            val option2 = PolylineOptions().color(Color.GREEN).addAll(arrLoc)
//            googleMap.addPolyline(option2)
            val option2 = PolygonOptions().fillColor(Color.argb(100,255,255,0))
                .strokeColor(Color.GREEN).addAll(arrLoc)
            googleMap.addPolygon(option2)
        }
    }
}