package com.example.admvalenbisi

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.InputStreamReader
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        inflateRecyclerView()

//        val navHost = findNavController(R.id.fragmentContainerView)

        if (savedInstanceState == null) {
            // Use commit to add the fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//    }

    fun inflateRecyclerView(){
        val rv : RecyclerView = findViewById<RecyclerView>(R.id.stationsList)
        rv.layoutManager = LinearLayoutManager( this)
        rv.adapter = StationAdapter( getStationsList( this))
    }

    fun navigateToDetail(){
//        navHost.navigate(R.id.action_listFragment_to_stationFragment)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//    }
}

//fun sortListAscById( list: List<Station>): List<Station> {
//
//}
//
//fun sortListDescById( list: List<Station>): List<Station> {
//}
//
//fun sortListAscByName( list: List<Station>): List<Station> {
//}
//
//fun sortListDescByName( list: List<Station>): List<Station> {
//}


fun getStationsList(context: Context): List<Station> {
    val stationsList = mutableListOf<Station>()
    val jsonString = readJsonFromRaw(context, R.raw.valenbisi)
    try {
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            //TODO read each item and create a Holiday instance
            var item = jsonArray.getJSONObject(i)
            var id: Int = item.getInt("number")
            var name: String = item.getString("address")
            var freeSpaces: Int = item.getInt("free")
            var totalSpaces: Int = item.getInt("total")
            var availableBikes: Int = item.getInt("available")
            stationsList.add(Station(id, name, availableBikes, freeSpaces, totalSpaces))
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return stationsList
}

fun readJsonFromRaw(context: Context, resId: Int): String {
    val inputStream = context.resources.openRawResource(resId)
    val reader = BufferedReader(InputStreamReader(inputStream))
    return reader.use { it.readText() }
}