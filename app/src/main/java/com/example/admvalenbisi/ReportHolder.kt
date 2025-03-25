package com.example.admvalenbisi

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import kotlin.math.log10

class ReportHolder(view : View) : RecyclerView.ViewHolder( view) {

    val titleTV = view.findViewById<TextView>(R.id.report_title)
    val idTV = view.findViewById<TextView>(R.id.id)
    val statusTV = view.findViewById<TextView>(R.id.report_status)
    val typeTV = view.findViewById<TextView>(R.id.report_type)

    fun render(report: Report, context: Context) {
        titleTV.text = report.title
        idTV.text = report.id.toString()
        statusTV.text = report.status.toString()
        typeTV.text = report.type.toString()
    }
}