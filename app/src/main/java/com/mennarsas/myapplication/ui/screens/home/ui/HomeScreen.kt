package com.mennarsas.myapplication.ui.screens.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mennarsas.myapplication.ui.components.NavigationMenu

@Composable
fun HomeScreen() {
    var selectedOption by remember { mutableStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Bienvenido al sistema de turneros",
                modifier = Modifier.padding(16.dp)
            )
            // Aquí puedes agregar la lista de turnos y su lógica
        }
        NavigationMenu(
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it}
        )
    }
}