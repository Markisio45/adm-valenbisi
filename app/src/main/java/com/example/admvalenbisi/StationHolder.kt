package com.example.admvalenbisi

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import kotlin.math.log10

class StationHolder( view : View) : RecyclerView.ViewHolder( view) {

    val nameTV = view.findViewById<TextView>(R.id.station_name)
    val idTV = view.findViewById<TextView>(R.id.station_id)
    val bikesLabelTV = view.findViewById<TextView>(R.id.bikesLabel)

    fun render(station: Station, context: Context) {
        var digitsFree = if( station.availableBikes == 0) 1 else log10(station.availableBikes.toDouble()).toInt() + 1
        nameTV.text = station.name
        idTV.text = station.id.toString()
        bikesLabelTV.text = context.getString(R.string.tag_station_info, station.availableBikes, station.totalSpaces)
        var text = bikesLabelTV.text.toString()
        val spannable = SpannableString(text).apply {
            setSpan( StyleSpan(Typeface.BOLD), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setSpan(android.text.style.ForegroundColorSpan(if ( station.availableBikes > 10) Color.GREEN else if( station.availableBikes > 5) Color.YELLOW else Color.RED), 19, 19 + digitsFree, 0)
        }
        bikesLabelTV.text = spannable
    }
}