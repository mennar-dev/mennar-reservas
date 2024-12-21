package com.mennarsas.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mennarsas.myapplication.models.Ticket

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBottomSheet(
    showBottomSheet: Boolean,
    isActiveTicket: Boolean,
    activeTicket: Ticket? = null,
    onDismiss: () -> Unit,
    isGeneratingTicket: Boolean = false,
    onOptionSelected: (String, String, String) -> Unit = { _, _, _ -> },
    onGenerateTicket: () -> Unit,
    onClearTicket: () -> Unit = {},
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
            sheetMaxWidth = 100.dp,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isActiveTicket && activeTicket != null) {
                    TicketCard(ticket = activeTicket)
                    Button(
                        onClick = {
                            onClearTicket()
                            onDismiss()
                        }
                    ) {
                        Text("Cancelar Ticket")
                    }
                } else {
                    Text(text = "Generar Ticket Virtual", fontSize = 24.sp)

                    var selectedOption by remember { mutableStateOf("") }
                    var selectedOptionSucursal by remember { mutableStateOf("") }
                    var selectedOptionFila by remember { mutableStateOf("") }

                    CustomDropdown(
                        options = listOf(
                            "Nueva Eps",
                            "Fomag",
                            "Asociación indigena del cauca - AIC"
                        ),
                        label = "Selecciona tu EPS",
                        onOptionSelected = { option ->
                            selectedOption = option
                        }
                    )

                    CustomDropdown(
                        options = listOf(
                            "Dispencación Mennar AIC",
                            "Dispensación Mennar Fomag",
                            "Dispencación Mennar Nueva Eps ",
                            "Dispencación Mennar Nueva Eps 2",
                            "Dispencación Mennar Santander"
                        ),
                        label = "Selecciona la sucursal",
                        onOptionSelected = { option ->
                            selectedOptionSucursal = option
                        }
                    )

                    CustomDropdown(
                        options = listOf("Atención General", "Atención Preferencial", "Pendientes"),
                        label = "Selecciona una fila",
                        onOptionSelected = { option ->
                            selectedOptionFila = option
                        }
                    )

                    CustomButton(
                        enabled = selectedOption.isNotEmpty() && selectedOptionSucursal.isNotEmpty() && selectedOptionFila.isNotEmpty(),
                        isLoading = isGeneratingTicket,
                        onClick = {
                            onOptionSelected(
                                selectedOption,
                                selectedOptionSucursal,
                                selectedOptionFila
                            )
                            onGenerateTicket()
                        },
                        buttonText = "Generar Ticket",
                        loadingText = "Generando ..."
                    )

                    OutlinedButton(
                        onClick = onDismiss,
                        text = "volver al menú principal"
                    )
                }
            }
        }
    }
}