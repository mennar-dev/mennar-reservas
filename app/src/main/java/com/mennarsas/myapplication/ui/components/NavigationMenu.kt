package com.mennarsas.myapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mennarsas.myapplication.ui.theme.PrimaryColor

@Composable
fun NavigationMenu(selectedOption: Int, onOptionSelected: (Int) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
       // elevation = 4.dp
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MenuOption(
                icon = Icons.Default.DateRange,
                title = "Reservar",
                isSelected = selectedOption == 0,
                onClick = { onOptionSelected(0) }
            )
            MenuOption(
                icon = Icons.Default.AccountBox,
                title = "Mis reservas",
                isSelected = selectedOption == 1,
                onClick = { onOptionSelected(1) }
            )
        }
    }

}

@Composable
fun MenuOption(icon: ImageVector, title: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Color.Gray.copy(alpha = 0.2f) else Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                tint = if (isSelected) PrimaryColor else Color.Gray,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = TextStyle(
                    color = if (isSelected) PrimaryColor else Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}