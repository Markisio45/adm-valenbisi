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

class MainFragment : Fragment() {

    var sorting_alphabetical_desc : Boolean = false;
    var sorting_bikes_desc : Boolean = false;

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
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // ✅ Se ejecuta cuando el usuario presiona "Enter" o el botón de búsqueda
                query?.let {
                    println("Texto enviado: $it") // Aquí puedes hacer búsquedas o cualquier operación
                }
                return true
            }
        })

        view.setOnTouchListener { _, _ ->
            view.performClick()
            searchView.clearFocus() // Quitar foco
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0) // Ocultar teclado
            false
        }


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                Log.d( "FRAGMENT", "MENU: ${menu}")
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.sort_alphabetical -> {
                        Log.d("MENU", "Pulsado!")
                        sorting_bikes_desc = false
                        sorting_alphabetical_desc = !sorting_alphabetical_desc

                        true
                    }
                    R.id.sort_available_bikes -> {
                        sorting_alphabetical_desc = false
                        sorting_bikes_desc = !sorting_bikes_desc

                        // Llamada a función de sorting

                        true
                    }
                    else -> false
                }
            }
        })

        requireActivity().title = "Valenbisi"

        inflateRecyclerView( view)

    }

}