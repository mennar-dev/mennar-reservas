package com.mennarsas.myapplication.models

import java.util.Calendar
import java.util.Date
import java.util.UUID

data class Ticket(
    val id: String = UUID.randomUUID().toString(),
    val eps: String,
    val office: String,
    val row: String,
    val dateGeneration: Date = Calendar.getInstance().time,
    val shift: String = generarNumeroTurno(row)
) {
    companion object {
        private fun generarNumeroTurno(row: String): String {
            val rowLetter = when (row) {
                "Pendientes" -> "P"
                "Atención General" -> "AG"
                "Atención Preferencial" -> "AP"
                else -> ""
            }
            val number = (1..999).random().toString().padStart(3, '0')
            return "$rowLetter - $number"
        }
    }
}
