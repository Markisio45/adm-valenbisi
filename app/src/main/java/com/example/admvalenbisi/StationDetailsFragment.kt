package com.example.admvalenbisi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.admvalenbisi.databinding.FragmentStationDetailsBinding

class StationDetailsFragment : Fragment() {

    private var _binding: FragmentStationDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_station_details, container, false)
        _binding = FragmentStationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

        val ARG_STATION = "station"

        val station = arguments?.getParcelable<Station>(ARG_STATION)
        station?.let {
            binding.stationDetailsAddress?.text = it.name
            binding.stationDetailsId?.text = it.id.toString()
            binding.stationDetailsTotal?.text = view.context.getString(R.string.tag_station_total_spaces, it.totalSpaces)
            binding.stationDetailsFree?.text = view.context.getString(R.string.tag_station_free_spaces, it.freeSpaces)
            binding.stationDetailsAvailable?.text = view.context.getString(R.string.tag_station_available_bikes, it.availableBikes)
        }

        val stationId = station?.id ?: return
        val dao = ReportDatabase.getDatabase(requireContext()).reportDao()
        lifecycleScope.launch {
            val reports: Array<Report> = dao.getByStation( stationId)
        }

        Log.d( "STATIONDETAILSFRAGMENT", "STATION: $station")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StationDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters

        private const val ARG_STATION = "station"

        @JvmStatic
        fun newInstance(station: Station): StationDetailsFragment =
            StationDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_STATION, station)
                }
            }
    }
}