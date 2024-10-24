package com.example.prctica05_componenteslistviewyspinner

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleConciertoActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_concierto)

        val concierto = intent.getSerializableExtra("concierto") as? Concierto

        val tvDetalles = findViewById<TextView>(R.id.txtDatos)

        concierto?.let {
            var genero: String? = null
            if(it.generos == 1) genero = "Banda"
            if(it.generos == 2) genero = "Popular"
            if(it.generos == 3) genero = "Rock"

            tvDetalles.text = "Código: ${it.codigo}\n" +
                    "Nombre del concierto: ${it.concierto}\n" +
                    "Auditorio: ${it.auditorio}\n" +
                    "Fecha: ${it.fecha}\n" +
                    "Género musical: ${genero}\n" +
                    "Capacidad máxima: ${it.capacidad}\n"
        }

        val ibtnRegresar = findViewById<ImageButton>(R.id.ibtnRegresar)
        ibtnRegresar.setOnClickListener {
            finish()
        }
    }

}