package com.example.admvalenbisi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admvalenbisi.R //replace it with your package
import com.example.admvalenbisi.StationAdapter
import java.text.Collator
import java.text.Normalizer
import java.util.Locale

class MainFragment : Fragment() {

    var sorting_alphabetical_desc: Boolean = false;
    var sorting_bikes_desc: Boolean = false;

    private lateinit var adapter: StationAdapter
    private var originalStationList: List<Station> = listOf()
    private var filteredStationList: List<Station> = listOf()

    private val collator: Collator = Collator.getInstance(Locale("es", "ES")).apply {
        strength = Collator.PRIMARY // Ignora acentos y mayúsculas/minúsculas
    }

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

    fun inflateRecyclerView(view: View) {
        val rv: RecyclerView = view.findViewById<RecyclerView>(R.id.stationsList)
        rv.layoutManager = LinearLayoutManager(requireContext())

        adapter = StationAdapter (getStationsList(requireContext())) { station ->
            val fragment = StationDetailsFragment.newInstance(station)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
        }

        rv.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        originalStationList = getStationsList(requireContext())

        val searchView: SearchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnClickListener {
            searchView.isIconified = false
            searchView.requestFocus()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                // ✅ Se ejecuta cada vez que el usuario escribe algo en el SearchView
                newText?.let {
                    println("Texto actual: $it") // Aquí puedes hacer operaciones con el texto
                    // TODO: Llamada a función para filtrar por nombre
                    filterStations(it)
                } ?: filterStations("") // Si el texto es nulo, mostramos todo
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // ✅ Se ejecuta cuando el usuario presiona "Enter" o el botón de búsqueda
                query?.let {
                    println("Texto enviado: $it") // Aquí puedes hacer búsquedas o cualquier operación
                    filterStations(query)
                }
                return true
            }
        })

        view.setOnTouchListener { _, _ ->
            view.performClick()
            searchView.clearFocus() // Quitar foco
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0) // Ocultar teclado
            false
        }


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                Log.d("FRAGMENT", "MENU: ${menu}")
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.sort_alphabetical -> {
                        Log.d("MENU", "Pulsado!")
                        sorting_bikes_desc = false
                        sorting_alphabetical_desc = !sorting_alphabetical_desc
                        sortStationsByName()
                        Log.d("DEBUG", "Lista inicial: ${getStationsList(requireContext())}")

                        true
                    }

                    R.id.sort_available_bikes -> {
                        sorting_alphabetical_desc = false
                        sorting_bikes_desc = !sorting_bikes_desc

                        // Llamada a función de sorting
                        sortStationsByAvailableBikes()

                        true
                    }

                    else -> false
                }
            }
        })

        requireActivity().title = "Valenbisi"

        inflateRecyclerView(view)

    }

    private fun filterStations(query: String?) {
        filteredStationList = originalStationList.let {
            if (query.isNullOrEmpty()) {
                it.toList() // Creamos una copia para evitar manipular el original
            } else {
                val normalizedQuery = normalizeText(query)
                Log.d("Filter", "Texto actual: $query")
                it.filter { station ->
                    normalizeText(station.name).contains(normalizedQuery, ignoreCase = true)
                }
            }
        }
        Log.d("Filter", "Texto de búsqueda: $query")
        Log.d("Filter", "Resultados: ${filteredStationList.size}")
        Log.d("Filter", "Contenido de originalStationList: ${originalStationList.joinToString { it.name }}")

        applyCurrentSorting()
    }


    /*private fun sortStationsByName() {
        filteredStationList = if (sorting_alphabetical_desc) {
            filteredStationList.sortedByDescending { it.name }
        } else {
            filteredStationList.sortedBy { it.name }
        }
        adapter.updateList(filteredStationList)
    }*/

    private fun sortStationsByName() {
        Log.d("Sort", "Tamaño de la lista antes de ordenar: ${filteredStationList.size}")

        filteredStationList = if (sorting_alphabetical_desc) {
            filteredStationList.sortedWith { a, b -> collator.compare(b.name, a.name) }
        } else {
            filteredStationList.sortedWith { a, b -> collator.compare(a.name, b.name) }
        }

        Log.d("Sort", "Tamaño de la lista después de ordenar: ${filteredStationList.size}")
        filteredStationList.forEach { Log.d("Sort", "Estación: ${it.name}") }

        adapter.updateList(filteredStationList)
    }


    private fun sortStationsByAvailableBikes() {
        filteredStationList = if (sorting_bikes_desc) {
            filteredStationList.sortedByDescending { it.availableBikes }
        } else {
            filteredStationList.sortedBy { it.availableBikes }
        }
        adapter.updateList(filteredStationList)
    }

    private fun applyCurrentSorting() {
        if (sorting_alphabetical_desc || sorting_bikes_desc) {
            if (sorting_alphabetical_desc) sortStationsByName()
            if (sorting_bikes_desc) sortStationsByAvailableBikes()
        } else {
            adapter.updateList(filteredStationList)
        }
    }

    private fun normalizeText(text: String): String {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "") // Elimina los diacríticos (acentos)
    }
}