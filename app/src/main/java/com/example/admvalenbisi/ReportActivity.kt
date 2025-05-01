package com.example.admvalenbisi

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_report)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val stationId = intent.getIntExtra("stationId", -1)

        var report: Report = Report(
                station = stationId,
                status = ReportStatus.OPEN,
                title = "Título del reporte",
                description = "Descripción del reporte",
                type = ReportType.MECHANICAL
            )

        val spinnerStatus: Spinner = findViewById(R.id.spinner_status)

        // Lista de opciones
        val options = listOf("Abierto", "Procesando", "Cerrado")

        // Configurar el adaptador
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapter

        // Manejar selección
        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = options[position]

                when( selectedOption){
                    "Abierto" -> report.status = ReportStatus.OPEN
                    "Procesando" -> report.status = ReportStatus.PROCESSING
                    "Cerrado" -> report.status = ReportStatus.CLOSED
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acción cuando no se selecciona nada
            }
        }

        val spinnerType: Spinner = findViewById(R.id.spinner_type)

        // Lista de opciones
        val options_type = listOf("Mecánica", "Electrica", "Pintura", "Albañilería")

        // Configurar el adaptador
        val adapter_type = ArrayAdapter(this, android.R.layout.simple_spinner_item, options_type)
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter_type

        // Manejar selección
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = options_type[position]

                when( selectedOption){
                    "Mecánica" -> report.type = ReportType.MECHANICAL
                    "Electrica" -> report.type = ReportType.ELECTRIC
                    "Pintura" -> report.type = ReportType.PAINTING
                    "Albañilería" -> report.type = ReportType.MASONRY
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acción cuando no se selecciona nada
            }
        }

        // Guardar el reporte
        val saveButton: Button = findViewById(R.id.confirmButton)

        saveButton.setOnClickListener {
            lifecycleScope.launch {
                report.title = findViewById<TextView>(R.id.report_edit_title).text.toString()
                report.description = findViewById<TextView>(R.id.report_edit_description).text.toString()
                saveReport(report)
            }

            Toast.makeText(this, "Reporte guardado", Toast.LENGTH_SHORT).show()
        }


        val toolbar: Toolbar = findViewById<Toolbar>(R.id.report_toolbar)
        setSupportActionBar(toolbar)
        val menuHost: MenuHost = this
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.report_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        })

            this.title = "New Report"

    }

    suspend fun saveReport(report: Report) {
        val dao = ReportDatabase.getInstance(this).reportDao()
        lifecycleScope.launch {
            val dao = ReportDatabase.getInstance(this@ReportActivity).reportDao()
            dao.insert(report)
        }
    }
}