package com.mennarsas.myapplication.ui.screens.my_reservations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MyReservationsScreen() {
        Box(modifier = Modifier.background(Color.White).fillMaxWidth().fillMaxHeight()) {
                Text(text = "Lista de mis reservas aquí")
        }
}