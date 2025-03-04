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
//import com.example.admvalenbisi.databinding.FragmentMainBinding
import com.example.admvalenbisi.getStationsList


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
        rv.layoutManager = LinearLayoutManager( context)
        rv.adapter = StationAdapter( getStationsList( requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = requireContext()
        super.onViewCreated(view, savedInstanceState)


        val rv : RecyclerView = view.findViewById<RecyclerView>(R.id.stationsList)
        rv.layoutManager = LinearLayoutManager(requireContext())
        val adapter : StationAdapter = StationAdapter( getStationsList(context))
        rv.adapter = adapter
    }

}