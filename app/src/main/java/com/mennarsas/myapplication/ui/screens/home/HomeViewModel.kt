package com.mennarsas.myapplication.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mennarsas.myapplication.models.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

data class HomeScreenState(
    val selectedEps: String = "",
    val selectedOffice: String = "",
    val selectedRow: String = "",
    val showBottomSheet: Boolean = false,
    val isGeneratingTicket: Boolean = false,
    val generatedTicket: Ticket? = null,
    val showSnackbar: Boolean = false,
    val snackbarMessage: String = ""
) {
    val hasActiveTicket: Boolean = generatedTicket != null
}

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    fun updateSelectedEps(option: String) {
        _state.update { it.copy(selectedEps = option) }
    }

    fun updateSelectedOffice(option: String) {
        _state.update { it.copy(selectedOffice = option) }
    }

    fun updateSelectedRow(option: String) {
        _state.update { it.copy(selectedRow = option) }
    }

    fun toggleBottomSheet(show: Boolean) {
        _state.update { it.copy(showBottomSheet = show) }
    }

    fun generateTicket() {
        viewModelScope.launch {
            // Simular proceso de generación de ticket
            _state.update { it.copy(isGeneratingTicket = true) }

            // Simular un retraso de generación
            delay(2000) // 2 segundos de espera simulada

            // Crear ticket con los datos seleccionados
            val ticket = Ticket(
                eps = _state.value.selectedEps,
                office = _state.value.selectedOffice,
                row = _state.value.selectedRow
            )

            // Actualizar estado
            _state.update {
                it.copy(
                    isGeneratingTicket = false,
                    generatedTicket = ticket,
                    showBottomSheet = false,
                    showSnackbar = true,
                    snackbarMessage = "Ticket generado exitosamente"
                )
            }
        }
    }

    fun dismissSnackbar() {
        _state.update { it.copy(showSnackbar = false, snackbarMessage = "") }
    }

    fun clearActiveTicket() {
        _state.update { it.copy(generatedTicket = null) }
    }
}