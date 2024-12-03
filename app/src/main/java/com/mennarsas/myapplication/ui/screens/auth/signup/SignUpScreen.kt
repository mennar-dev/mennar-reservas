package com.mennarsas.myapplication.ui.screens.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    viewModel: SignUpViewModel = viewModel(),
    onBackToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Efecto de mostrar Snackbar cuando hay un error
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
        SignUp(
            modifier = Modifier.align(Alignment.Center),
            uiState = uiState,
            onFirstNameChange = viewModel::onFirstNameChange,
            onLastNameChange = viewModel::onLastNameChange,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            onToggleConfirmPasswordVisibility = viewModel::onToggleConfirmPasswordVisibility,
            onSignUpClick = {
                scope.launch {
                    if (viewModel.onSignUpClick()) {
                        onSignUpSuccess()
                    }
                }
            },
            onBackToLogin = onBackToLogin
        )

        // Snackbar para mostrar lo errores
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
        ) { data ->
            Snackbar(
                containerColor = Color(0xFFB00020),
                contentColor = Color.White,
                dismissAction = {
                    IconButton(onClick = { data.dismiss() }) {
                        Text(text = "x", color = Color.White)
                    }
                }
            ) {
                Text(data.visuals.message)
            }
        }
    }
}

@Composable
fun SignUp(
    modifier: Modifier,
    uiState: SignUpUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit,
    onSignUpClick: () -> Unit,
    onBackToLogin: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear cuenta",
            color = PrimaryColor,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 50.sp,
            fontWeight = FontWeight.W500
        )

        CustomTextField(  // Nombres
            value = uiState.firstName,
            onValueChange = onFirstNameChange,
            enabled = !uiState.isLoading,
            placeholder = "Nombres"
        )

        CustomTextField( // Apellidos
            value = uiState.lastName,
            onValueChange = onLastNameChange,
            enabled = !uiState.isLoading,
            placeholder = "Apellidos"
        )

        CustomTextField(
            // Correo electrónico
            value = uiState.email,
            onValueChange = onEmailChange,
            enabled = !uiState.isLoading,
            keyboardType = KeyboardType.Email,
            placeholder = "Correo electrónico",
        )

        CustomTextField( // Contraseña
            value = uiState.password,
            onValueChange = onPasswordChange,
            placeholder = "Digita tu contraseña",
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

        CustomTextField( // Confirmar contraseña
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = "Confirmar contraseña",
            keyboardType = KeyboardType.Password,
            visualTransformation = if (uiState.isConfirmPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            enabled = !uiState.isLoading,
            trailingIcon = {
                IconButton(onClick = onToggleConfirmPasswordVisibility) {
                    Icon(
                        painter = painterResource(
                            id = if (uiState.isConfirmPasswordVisible)
                                R.drawable.ic_visibility_off
                            else
                                R.drawable.ic_visibility
                        ),
                        contentDescription = if (uiState.isConfirmPasswordVisible)
                            "Ocultar contraseña"
                        else
                            "Mostrar contraseña"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            enabled = uiState.isSignUpEnabled && !uiState.isLoading,
            isLoading = uiState.isLoading,
            onClick = onSignUpClick,
            title = "Registrarme"
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
        OutlinedButton(onClick = onBackToLogin, text = "Iniciar Sesión")
    }
}