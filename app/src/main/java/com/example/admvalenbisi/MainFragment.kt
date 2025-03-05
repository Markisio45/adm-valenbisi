package com.example.admvalenbisi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admvalenbisi.R //replace it with your package
import com.example.admvalenbisi.StationAdapter

class MainFragment : Fragment() {

    // 2. Use a constant for clarity and maintainability (MEDIUM PRIORITY)
    companion object {
        const val TAG = "MainFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    fun inflateRecyclerView(view: View){
        val rv : RecyclerView = view.findViewById<RecyclerView>(R.id.stationsList)
        rv.layoutManager = LinearLayoutManager( requireContext())

        var adapter: StationAdapter = StationAdapter( getStationsList( requireContext())){
            station ->
                val fragment = StationDetailsFragment.newInstance( station)
                parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }

        rv.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inflateRecyclerView( view)
    }

}