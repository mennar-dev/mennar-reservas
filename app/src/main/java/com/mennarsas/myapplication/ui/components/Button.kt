package com.mennarsas.myapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mennarsas.myapplication.theme.ButtonColorDesabled
import com.mennarsas.myapplication.theme.PrimaryColor

@Composable
fun Button (
    enabled: Boolean,
    isLoading: Boolean ,
    onClick: () -> Unit,
    title: String
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) PrimaryColor else ButtonColorDesabled,
            contentColor = Color.White,
            disabledContainerColor = ButtonColorDesabled,
            disabledContentColor = Color.White
        ),
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
        } else {
            Text(text = title)
        }
    }
}