package com.mennarsas.myapplication.ui.screens.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mennarsas.myapplication.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Efecto para mostrar el Snackbar cuando hay un error
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Long, withDismissAction = true)
            viewModel.dismissError() } }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Login(Modifier.align(
            Alignment.Center),
            uiState = uiState,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            onLoginClick = {
                scope.launch { if (viewModel.onLoginClick()) { onLoginSuccess() } }
            }
        )
        // Snackbar para mostrar errores
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
        ) { data ->
            Snackbar(
                containerColor = Color(0xFFB00020),
                contentColor = Color.White,
                dismissAction = { IconButton(onClick = { data.dismiss() }) { Text( text = "x", color = Color.White ) } }
            ) {
                Text(data.visuals.message)
            }
        }
    }
}

@Composable
fun Login(
    modifier: Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderImage()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Inicio de sesión",
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        EmailFiled(
            value = uiState.email,
            onValueChange = onEmailChange,
            enabled = !uiState.isLoading
        )
        Spacer(modifier = Modifier.height(8.dp))
        PasswordFiled(
            value = uiState.password,
            onValueChange = onPasswordChange,
            isPasswordVisible = uiState.isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            enabled = !uiState.isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton(
            enabled = uiState.isLoginEnabled && !uiState.isLoading,
            isLoading = uiState.isLoading,
            onClick = onLoginClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¿Has olvidado la contraseña? Aqui",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "No tienes una cuenta?",
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Registrarme",
            color = Color(0xFF00796B), // Usa un color similar al botón de inicio de sesión
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "o",
            color = Color.Gray
        )
        Text(
            text = "Ingresar como usuario temporal",
            color = Color.Gray
        )
    }
}

@Composable
fun LoginButton(
    enabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) Color(0xFF00796B) else Color(0xFFB2DFDB),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFB2DFDB)
        ),
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
        } else {
            Text(text = "Iniciar Sesión")
        }
    }
}

@Composable
fun PasswordFiled(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    enabled: Boolean
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .border( width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(30.dp)),
        placeholder = { Text(text="Digita tu contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            disabledIndicatorColor = Color.Transparent),
        trailingIcon = {
            IconButton(onClick = onTogglePasswordVisibility) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Close else Icons.Default.Check,
                    contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        }
    )
}

@Composable
fun EmailFiled(value: String, onValueChange: (String) -> Unit, enabled: Boolean) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(30.dp)),
        placeholder = { Text(text="Digita tu email") },
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            disabledIndicatorColor = Color.Transparent
            ),
        shape = RoundedCornerShape(30.dp)
    )
}

@Composable
fun HeaderImage() {
    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo mennar sas")
}