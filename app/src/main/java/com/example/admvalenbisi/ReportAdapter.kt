package com.example.admvalenbisi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ReportAdapter(
    private val reportList: List<Report>,
    private val onItemClick: (Report) -> Unit = {}) : RecyclerView.Adapter<ReportHolder>() {

    override fun getItemCount(): Int = reportList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportHolder {
//        val layout = LayoutInflater.from( parent.context).inflate(R.layout.holiday_item, parent, false)
        val layout = LayoutInflater.from( parent.context).inflate(R.layout.report_item, parent, false)
        return ReportHolder( layout)
    }

    override fun onBindViewHolder(
        holder: ReportHolder,
        position: Int
    ) {
        val item: Report = reportList[position]
        holder.render( item, holder.itemView.context)
        holder.itemView.setOnClickListener {
            onItemClick( item)
        }
    }
}