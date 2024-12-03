package com.mennarsas.myapplication.ui.screens.auth.recoverypassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mennarsas.myapplication.theme.PrimaryColor
import com.mennarsas.myapplication.ui.components.CustomTextField
import com.mennarsas.myapplication.ui.components.Button

@Composable
fun RecoverPasswordScreen(onBackToLogin: () -> Boolean) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)
    ) {
        RecoverPasswordContent(
            onBackToLogin = onBackToLogin,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun RecoverPasswordContent(onBackToLogin: () -> Boolean, modifier: Modifier) {
    val email: MutableState<String> = remember { mutableStateOf("") }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Recuperar contraseña",
            color = PrimaryColor,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 46.sp,
            fontWeight = FontWeight.W500
        )
        CustomTextField(  // Nombres
            value = email.value,
            onValueChange = { email.value = it },
            enabled = true,
            keyboardType = KeyboardType.Email,
            placeholder = "Correo electrónico"
        )
        Spacer(Modifier.height(10.dp))
        Button(
            enabled = true,
            isLoading = false,
            onClick = {onBackToLogin()},
            title = "Enviar código"
        )
    }
}