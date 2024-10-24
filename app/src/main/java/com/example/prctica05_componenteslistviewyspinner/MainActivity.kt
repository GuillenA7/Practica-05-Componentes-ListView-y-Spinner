package com.example.prctica05_componenteslistviewyspinner

import android.content.Intent
import android.widget.*
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

    private var conciertos = arrayOfNulls<Concierto>(5) // Arreglo de 5 conciertos
    private var contador = 0

    private lateinit var edtCodigo: EditText
    private lateinit var edtConcierto: EditText
    private lateinit var edtFecha: EditText

    private lateinit var lvAuditorio: ListView
    private var auditorioSel: String = "TELMEX"

    private lateinit var spCapacidad: Spinner
    private var capacidadSel: String = "1 000"

    private lateinit var chGeneros: ChipGroup
    private lateinit var chGenBa: Chip
    private lateinit var chGenPop: Chip
    private lateinit var chGenRo: Chip


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtCodigo = findViewById(R.id.edtCodigo)
        edtConcierto = findViewById(R.id.edtConcierto)
        edtFecha = findViewById(R.id.edtFecha)
        lvAuditorio = findViewById(R.id.ltvAuditorio)
        spCapacidad = findViewById(R.id.spnCapacidad)
        chGeneros = findViewById(R.id.gpoChServicios)
        chGenBa = findViewById(R.id.chGenBa)
        chGenPop = findViewById(R.id.chGenPop)
        chGenRo = findViewById(R.id.chGenRo)

        val lstAuditorio = listOf("TELMEX", "Benito Juarez", "Degollado", "CETI", "Tecate")

        val adaptAuditorio = ArrayAdapter(this, android.R.layout.simple_list_item_1, lstAuditorio)
        lvAuditorio.adapter = adaptAuditorio

        lvAuditorio.setOnItemClickListener{ parent, view, position, id ->
            auditorioSel = parent.getItemAtPosition(position).toString()
            parent.getItemAtPosition(position)
            Toast.makeText(this, "Auditorio: ${auditorioSel}", Toast.LENGTH_SHORT).show()
        }

        val lstCapacidad = resources.getStringArray(R.array.listaCapacidad)
        val adaptCapacidad = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, lstCapacidad)
        adaptCapacidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCapacidad.adapter = adaptCapacidad
        spCapacidad.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, p2:
                Int, p3: Long
            ) {
                capacidadSel = lstCapacidad[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    } //onCreate

    fun onClick(v: View?){
        when(v?.id){
            R.id.ibtnAgregar -> agregar()
            R.id.ibtnBuscar -> buscar()
            R.id.ibtnLimpiar -> limpiar()
            R.id.ibtnEliminar -> eliminar()
        }
    }

    private fun agregar() {
        if (contador < conciertos.size) {
            if(edtCodigo.text.isNotBlank() && edtCodigo.text.isNotEmpty()){
                val codigo = edtCodigo.text.toString().toInt()
                val capacidad = capacidadSel
                val concierto = edtConcierto.text.toString()
                val fecha = edtFecha.text.toString()
                val auditorio = auditorioSel

                var generos = 2
                if(chGenBa.isChecked) generos = 1
                if(chGenPop.isChecked) generos = 2
                if(chGenRo.isChecked) generos = 3

                conciertos[contador] = Concierto(codigo, capacidad, concierto, fecha, generos, auditorio)
                contador++

                limpiar()
                Toast.makeText(this, "Concierto registrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Ingresa el código", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No hay más espacio para conciertos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buscar() {
        if(edtCodigo.text.isNotBlank() && edtCodigo.text.isNotEmpty()){
            val codigo = edtCodigo.text.toString().toInt()
            val conciertoEncontrado = conciertos.find { it?.codigo == codigo }

            if (conciertoEncontrado != null) {
                val intent = Intent(this, DetalleConciertoActivity::class.java)
                intent.putExtra("concierto", conciertoEncontrado)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Concierto no encontrado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingresa el código", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiar() {
        edtCodigo.text = null
        edtConcierto.text = null
        edtFecha.text = null
        chGeneros.clearCheck()

        edtCodigo.requestFocus()
    }

    private fun eliminar() {
        if(edtCodigo.text.isNotBlank() && edtCodigo.text.isNotEmpty()){
            val codigo = edtCodigo.text.toString().toInt()
            var eliminado = false

            for (i in conciertos.indices) {
                if (conciertos[i]?.codigo == codigo) {
                    conciertos[i] = null
                    eliminado = true
                    break
                }
            }

            if (eliminado) {
                Toast.makeText(this, "Concierto eliminado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Concierto no encontrado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingresa el código", Toast.LENGTH_SHORT).show()
        }
    }

}