package com.example.admvalenbisi

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
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
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

        Log.d( "Hola Mundo", "Hola Mundo")

        if (savedInstanceState == null) {
            // Use commit to add the fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment())
                .commit()
        }

        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//    }

//    fun inflateRecyclerView(){
//        val rv : RecyclerView = findViewById<RecyclerView>(R.id.stationsList)
//        rv.layoutManager = LinearLayoutManager( this)
//        rv.adapter = StationAdapter( getStationsList( this))
//    }

    fun navigateToDetail(){
//        navHost.navigate(R.id.action_listFragment_to_stationFragment)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//    }
}

// TODO: Filtering functions

//fun getStationsList(context: Context): List<Station> {
//    val stationsList = mutableListOf<Station>()
//    val jsonString = readJsonFromRaw(context, R.raw.valenbisi)
//    try {
//        val jsonArray = JSONArray(jsonString)
//        for (i in 0 until jsonArray.length()) {
//            //TODO read each item and create a Holiday instance
//            var item = jsonArray.getJSONObject(i)
//            var id: Int = item.getInt("number")
//            var name: String = item.getString("address")
//            var freeSpaces: Int = item.getInt("free")
//            var totalSpaces: Int = item.getInt("total")
//            var availableBikes: Int = item.getInt("available")
//            stationsList.add(Station(id, name, availableBikes, freeSpaces, totalSpaces))
//        }
//    } catch (e: JSONException) {
//        e.printStackTrace()
//    }
//    return stationsList
//}

suspend fun getStationsList(context: Context): List<Station> {
    val stationsList = mutableListOf<Station>()
    val client = OkHttpClient()

    // URL del JSON en internet
    val url = "https://valencia.opendatasoft.com/api/explore/v2.1/catalog/datasets/valenbisi-disponibilitat-valenbisi-dsiponibilidad/exports/json?lang=es&timezone=Europe%2FBerlin"
    Log.i("getStationsList", "URL: $url")
    val request = Request.Builder()
        .url(url)
        .build()

    try {
        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }
        if (response.isSuccessful) {
            val jsonString = response.body?.string()
            if (!jsonString.isNullOrEmpty()) {
                val jsonArray = JSONArray(jsonString)
                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    val id: Int = item.getInt("number")
                    val name: String = item.getString("address")
                    val freeSpaces: Int = item.getInt("free")
                    val totalSpaces: Int = item.getInt("total")
                    val availableBikes: Int = item.getInt("available")
                    stationsList.add(Station(id, name, availableBikes, freeSpaces, totalSpaces))
                }
            }
            Log.i( "getStationsList", "Response OK:: stationsList: $stationsList")
        } else {
            Log.e("getStationsList", "Error en la respuesta HTTP: ${response.code}")
        }
    } catch (e: JSONException) {
        Log.e("getStationsList", "Error al parsear JSON: ${e.message}")
    } catch (e: Exception) {
        Log.e("getStationsList", "Error en la solicitud HTTP: ${e}")
    }

    return stationsList
}

fun readJsonFromRaw(context: Context, resId: Int): String {
    val inputStream = context.resources.openRawResource(resId)
    val reader = BufferedReader(InputStreamReader(inputStream))
    return reader.use { it.readText() }
}