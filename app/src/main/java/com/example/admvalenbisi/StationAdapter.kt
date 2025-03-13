package com.example.admvalenbisi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StationAdapter(
    private var stationList:List<Station>,
    private val onItemClick: (Station) -> Unit = {}) : RecyclerView.Adapter<StationHolder>() {
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
        val item: Station = stationList[position]
        holder.render( item, holder.itemView.context)
        holder.itemView.setOnClickListener {
            onItemClick( item)
        }
    }

    override fun getItemCount(): Int = stationList.size

    fun updateList(newList: List<Station>) {
        stationList = newList
        notifyDataSetChanged() // Notifica que los datos han cambiado
    }
}