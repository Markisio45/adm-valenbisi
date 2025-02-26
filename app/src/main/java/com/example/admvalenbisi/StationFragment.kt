package com.example.admvalenbisi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 * Use the [StationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StationFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var station: Station? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
        arguments?.let {
//            station = it.getParcelable(STATION_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_station, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment StationFragment.
         */
        // TODO: Rename and change types and number of parameters

        const val STATION_BUNDLE = "station"

        @JvmStatic
        fun newInstance(station: Station) =
            StationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(STATION_BUNDLE, station)
                }
            }
    }
}