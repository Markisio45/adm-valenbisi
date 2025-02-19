package com.example.admvalenbisi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StationAdapter( private val stationList:List<Station>) : RecyclerView.Adapter<StationHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StationHolder {
//        val layout = LayoutInflater.from( parent.context).inflate(R.layout.holiday_item, parent, false)
        val layout = LayoutInflater.from( parent.context).inflate(R.layout.station_item, parent, false)
        return StationHolder( layout)
    }

    override fun onBindViewHolder(
        holder: StationHolder,
        position: Int
    ) {
        val item = stationList[position]
        holder.render( item)
    }

    override fun getItemCount(): Int = stationList.size

}