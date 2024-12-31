package com.mennarsas.myapplication.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun CustomButton (
    enabled: Boolean,
    isLoading: Boolean ,
    onClick: () -> Unit,
    buttonText: String,
    loadingText: String
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = loadingText, color = Color.White)
        } else {
            Text(text = buttonText)
        }
    }
}