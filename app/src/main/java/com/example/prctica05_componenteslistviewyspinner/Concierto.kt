package com.example.prctica05_componenteslistviewyspinner

import java.io.Serializable

data class Concierto(
    var codigo: Int,
    var capacidad: String,
    var concierto: String,
    var fecha: String,
    var generos: Int,
    var auditorio: String
) : Serializable
