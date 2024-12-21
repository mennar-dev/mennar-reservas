package com.mennarsas.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mennarsas.myapplication.models.Ticket
import com.mennarsas.myapplication.theme.PrimaryColor
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TicketCard(ticket: Ticket) {
    val dateFormat = SimpleDateFormat("EEE d MMMM yyyy 'a las' hh:mm a", Locale("es", "CO"))
    val formattedDate = dateFormat.format(ticket.dateGeneration)

//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp))
//            .clip(RoundedCornerShape(12.dp)),
//        shadowElevation = 8.dp
//    ) {}
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Número de Ticket",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = ticket.shift,
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Información del ticket
            TicketInfoRow("EPS:", ticket.eps)
            TicketInfoRow("Oficina:", ticket.office)
            TicketInfoRow("Fila:", ticket.row)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
                color = Color.LightGray
            )

            Text(
                text = "Fecha de la emisión",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = formattedDate,
               modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "ID: ${ticket.id}",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
}

@Composable
private fun TicketInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}