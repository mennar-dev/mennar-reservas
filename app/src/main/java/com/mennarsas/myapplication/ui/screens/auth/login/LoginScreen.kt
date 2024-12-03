package com.mennarsas.myapplication.ui.screens.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mennarsas.myapplication.R
import com.mennarsas.myapplication.theme.PrimaryColor
import com.mennarsas.myapplication.ui.components.CustomTextField
import kotlinx.coroutines.launch
import com.mennarsas.myapplication.ui.components.OutlinedButton
import com.mennarsas.myapplication.ui.components.Button

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel(),
    onSignUpClick: () -> Unit,
    onRecoverPasswordClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Efecto para mostrar el Snackbar cuando hay un error
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long,
                withDismissAction = true
            )
            viewModel.dismissError()
        }
    }

    Box(Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Login(
            Modifier.align(Alignment.Center),
            uiState = uiState,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            onLoginClick = {
                scope.launch {
                    if (viewModel.onLoginClick()) {
                        onLoginSuccess()
                    }
                }
            },
            onSignUpClick = onSignUpClick,
            onRecoverPasswordClick = onRecoverPasswordClick
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
                dismissAction = {
                    IconButton(onClick = { data.dismiss() }) {
                        Text(
                            text = "x",
                            color = Color.White
                        )
                    }
                }
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
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onRecoverPasswordClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            color = PrimaryColor,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 50.sp,
            fontWeight = FontWeight.W500
        )
        CustomTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            enabled = !uiState.isLoading,
            placeholder = "Correo electrónico"
        )
        CustomTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            placeholder = "Contraseña",
            keyboardType = KeyboardType.Password,
            visualTransformation = if (uiState.isPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            enabled = !uiState.isLoading,
            trailingIcon = {
                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        painter = painterResource(
                            id = if (uiState.isPasswordVisible)
                                R.drawable.ic_visibility_off
                            else
                                R.drawable.ic_visibility
                        ),
                        contentDescription = if (uiState.isPasswordVisible)
                            "Ocultar contraseña"
                        else
                            "Mostrar contraseña"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        RecoverPassword(onClick = onRecoverPasswordClick)
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            enabled = uiState.isLoginEnabled && !uiState.isLoading,
            isLoading = uiState.isLoading,
            onClick = onLoginClick,
            title = "Iniciar Sesión"
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = Dp.Hairline,
                color = Color.Gray
            )
            Text(
                text = "¿Ya tienes una cuenta?",
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = Dp.Hairline,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedButton(
            onClick = onSignUpClick,
            text = "Crear mi cuenta"
        )
    }
}

@Composable
fun RecoverPassword(onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        TextButton(
            onClick = onClick,
        ) {
            Text(
                text = "Recuperar contraseña",
                color = Color.Gray
            )
        }
    }
}