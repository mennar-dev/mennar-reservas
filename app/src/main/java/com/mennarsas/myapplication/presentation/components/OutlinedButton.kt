package com.mennarsas.myapplication.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mennarsas.myapplication.theme.PrimaryColor

@Composable
fun OutlinedButton(onClick: () -> Unit, text: String) {
   OutlinedButton(
       modifier = Modifier.fillMaxWidth().height(55.dp),
       colors = ButtonDefaults.buttonColors(
           contentColor = PrimaryColor,
           containerColor = Color.Transparent,
       ),
       onClick = onClick,
       border = BorderStroke(1.dp, PrimaryColor)
   ) {
       Text(text = text)
   }
}