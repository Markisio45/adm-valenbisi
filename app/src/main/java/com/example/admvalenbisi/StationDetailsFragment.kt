package com.example.admvalenbisi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.admvalenbisi.databinding.FragmentStationDetailsBinding

class StationDetailsFragment : Fragment() {

    private var _binding: FragmentStationDetailsBinding? = null
    private val binding get() = _binding!!
    private var reports: List<Report> = emptyList()
    private var stationId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        stationId = station?.id ?: return

        lifecycleScope.launch {
            updateReports()
        }



        binding.fabReport.setOnClickListener {
            val intent = Intent(requireContext(), ReportActivity::class.java)
            intent.putExtra("stationId", stationId)
            startActivity(intent)
        }

        Log.d( "STATIONDETAILSFRAGMENT", "STATION: $station")
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            updateReports()
        }
    }

    suspend fun updateReports(){
        val dao = ReportDatabase.getInstance(requireContext()).reportDao()
        reports = dao.getByStation( stationId)
        val reportAdapter = ReportAdapter( reports, onItemClick = { report ->
            val intent = Intent(requireContext(), ReportActivity::class.java)
            intent.putExtra("report", report)
            startActivity(intent)
        })
        binding.stationReportsList?.adapter = reportAdapter
        binding.stationReportsList?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager( requireContext())

        if( reports.isEmpty()) {
            Log.d( "STATIONDETAILSFRAGMENT", "NO REPORTS")
            binding.noReports?.visibility = View.VISIBLE
            binding.stationReportsList?.visibility = View.GONE
        } else {
            Log.d( "STATIONDETAILSFRAGMENT", "SI REPORTS")
            binding.noReports?.visibility = View.GONE
            binding.stationReportsList?.visibility = View.VISIBLE
        }

        Log.d( "STATIONDETAILSFRAGMENT", "REPORTS: $reports")
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