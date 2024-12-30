package com.mennarsas.myapplication.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mennarsas.myapplication.ui.components.RequestActionButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mennarsas.myapplication.theme.PrimaryColor
import com.mennarsas.myapplication.ui.components.TicketBottomSheet


@Composable
fun HomeScreen( viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Efecto para mostrar el Snackbar cuando hay un mensaje
    LaunchedEffect(state.showSnackbar) {
        if (state.showSnackbar) {
            snackbarHostState.showSnackbar(
                message = state.snackbarMessage,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
            viewModel.dismissSnackbar()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = "Bienvenid@, ¿Qué deseas hacer?",
                fontSize = 24.sp,
            )

            RequestActionButton(
                hasActiveTicket = state.hasActiveTicket,
                activeTicketNumber = state.generatedTicket?.shift,
                onClick = { viewModel.toggleBottomSheet(true) }
            )

            TicketBottomSheet(
                showBottomSheet = state.showBottomSheet,
                isActiveTicket = state.hasActiveTicket,
                activeTicket = state.generatedTicket,
                isGeneratingTicket = state.isGeneratingTicket,
                onDismiss = { viewModel.toggleBottomSheet(false) },
                onOptionSelected = { eps, office, row ->
                    viewModel.updateSelectedEps(eps)
                    viewModel.updateSelectedOffice(office)
                    viewModel.updateSelectedRow(row)
                },
                onGenerateTicket = { viewModel.generateTicket() },
                onClearTicket = { viewModel.clearActiveTicket() }
            )
        }
        // Snackbar para mostrar mensajes
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
        ) { data ->
            Snackbar(
                containerColor = PrimaryColor,
                contentColor = Color.White,
                dismissAction = {
                    IconButton(onClick = { data.dismiss() }) {
                        Text(text = "x", color = Color.White)
                    }
                }
            ) {
                Text(data.visuals.message)
            }
        }
    }
}