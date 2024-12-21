package com.mennarsas.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mennarsas.myapplication.R
import com.mennarsas.myapplication.theme.PrimaryColor

@Composable
fun RequestActionButton(
    hasActiveTicket: Boolean = false,
    activeTicketNumber: String? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(120.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = if (hasActiveTicket) "Tienes un ticket activo: $activeTicketNumber" else "Generar Ticket Virtual",
                    fontSize = 22.sp
                )
                Text(
                    text = if (hasActiveTicket)
                        "Haz clic para ver los detalles de tu ticket"
                    else
                        "Texto descriptivo (opcional) para esta acción aquí",
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column { Icon(painter = painterResource(R.drawable.ticket2), contentDescription = "ic_ticket") }
        }
    }
}