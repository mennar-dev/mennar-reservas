package com.mennarsas.myapplication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mennarsas.myapplication.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// SnackbarType.kt
enum class SnackbarType {
    SUCCESS,
    ERROR
}

// CustomSnackbarToast.kt
@Composable
fun CustomSnackbarToast(
    message: String?,
    type: SnackbarType,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    message?.let {
        val snackbarScope = rememberCoroutineScope()
        Snackbar(
            modifier = modifier.padding(16.dp),
            containerColor = when (type) {
                SnackbarType.SUCCESS -> Color(0xFF4CAF50) // Green
                SnackbarType.ERROR -> Color(0xFFE53935)   // Red
            },
            dismissAction = {
                IconButton(onClick = onDismiss) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Cerrar",
                        tint = Color.White
                    )
                }
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = when (type) {
                            SnackbarType.SUCCESS -> R.drawable.ic_check_circle
                            SnackbarType.ERROR -> R.drawable.ic_error
                        }
                    ),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = message,
                    color = Color.White
                )
            }
        }
        LaunchedEffect(key1 = message) {
            snackbarScope.launch {
                delay(3000) // Espera 3 segundos
                onDismiss() // Llama a la función onDismiss para cerrar el Snackbar
            }
        }
    }
}