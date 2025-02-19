package com.example.admvalenbisi

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color

class StationHolder( view : View) : RecyclerView.ViewHolder( view) {

    val nameTV = view.findViewById<TextView>(R.id.station_name)
    val idTV = view.findViewById<TextView>(R.id.station_id)
    val freeTV = view.findViewById<TextView>(R.id.freebikes)
    val totalTV = view.findViewById<TextView>(R.id.totalBikes)



    fun render(station: Station) {
        nameTV.text = station.name
        idTV.text = station.id.toString()
        freeTV.text = station.freeBikes.toString()
        freeTV.setTextColor(if ( station.freeBikes > 10) Color.GREEN else if( station.freeBikes > 5) Color.YELLOW else Color.RED)
        totalTV.text = station.totalBikes.toString()
    }
}