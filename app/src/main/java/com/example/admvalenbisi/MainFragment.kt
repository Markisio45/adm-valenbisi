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
import com.example.admvalenbisi.databinding.FragmentMainBinding
import com.example.admvalenbisi.getStationsList


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

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
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.stationsList.apply {
//            layoutManager = LinearLayoutManager(context) // Use context instead of no-parameter constructor
//            // 5. Pass the fragment's context to getStationsList (MEDIUM PRIORITY)
//            adapter = StationAdapter(getStationsList(requireContext()))
//        }
    }

}