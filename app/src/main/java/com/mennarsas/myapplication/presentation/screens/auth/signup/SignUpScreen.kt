package com.mennarsas.myapplication.presentation.screens.auth.signup

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.mennarsas.myapplication.data.models.auth.signup.SignUpUiState
import com.mennarsas.myapplication.theme.PrimaryColor
import com.mennarsas.myapplication.presentation.components.CustomTextField
import kotlinx.coroutines.launch
import com.mennarsas.myapplication.presentation.components.OutlinedButton
import com.mennarsas.myapplication.presentation.components.CustomButton
import com.mennarsas.myapplication.presentation.components.CustomSnackbarToast
import com.mennarsas.myapplication.presentation.components.SnackbarType

@Composable
fun SignUpScreen(
    // onSignUpSuccess: () -> Unit,
    viewModel: SignUpViewModel = viewModel(),
    onBackToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    // val snackbarHostState = remember { SnackbarHostState() }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)) {
        SignUp(
            modifier = Modifier.align(Alignment.Center),
            uiState = uiState,
            onNamesChange = viewModel::onNamesChange,
            onLastNamesChange = viewModel::onLastNamesChange,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
            onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
            onToggleConfirmPasswordVisibility = viewModel::onToggleConfirmPasswordVisibility,
            onSignUpClick = {
                scope.launch {
                    if (viewModel.onSignUpClick()) {
                        //  onSignUpSuccess()
                    }
                }
            },
            onBackToLogin = onBackToLogin
        )
        println("mensaje de exito en el screen")
        println(uiState.formState.successfullMessage)

        //  SnackbarToast para el mensaje exitoso
        CustomSnackbarToast(
            message = uiState.formState.successfullMessage,
            type = SnackbarType.SUCCESS,
            onDismiss = viewModel::dismissMessage,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        // SnackbarToast para el mensaje de error
        CustomSnackbarToast(
            message = uiState.formState.errorMessage,
            type = SnackbarType.ERROR,
            onDismiss = viewModel::dismissMessage,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun SignUp(
    modifier: Modifier,
    uiState: SignUpUiState,
    onNamesChange: (String) -> Unit,
    onLastNamesChange: (String) -> Unit,
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

        CustomTextField(  // Nombre(s)
            value = uiState.fields.names,
            onValueChange = onNamesChange,
            enabled = !uiState.formState.isLoading,
            placeholder = "Nombre(s)"
        )

        CustomTextField( // Apellidos
            value = uiState.fields.lastNames,
            onValueChange = onLastNamesChange,
            enabled = !uiState.formState.isLoading,
            placeholder = "Apellidos"
        )

        CustomTextField( // Correo electrónico
            value = uiState.fields.email,
            onValueChange = onEmailChange,
            enabled = !uiState.formState.isLoading,
            keyboardType = KeyboardType.Email,
            placeholder = "Correo electrónico",
            isError = uiState.fields.isEmailTouched && uiState.formState.emailErrorMessage != null,
            errorMessage = if (uiState.fields.isEmailTouched) uiState.formState.emailErrorMessage else null
        )

        CustomTextField( // Contraseña
            value = uiState.fields.password,
            onValueChange = onPasswordChange,
            placeholder = "Digita tu contraseña",
            keyboardType = KeyboardType.Password,
            visualTransformation = if (uiState.formState.isPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            enabled = !uiState.formState.isLoading,
            trailingIcon = {
                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        painter = painterResource(
                            id = if (uiState.formState.isPasswordVisible)
                                R.drawable.ic_visibility_off
                            else
                                R.drawable.ic_visibility
                        ),
                        contentDescription = if (uiState.formState.isPasswordVisible)
                            "Ocultar contraseña"
                        else
                            "Mostrar contraseña"
                    )
                }
            },
            isError = uiState.fields.isPasswordTouched && uiState.formState.passwordErrorMessage != null,
            errorMessage = if (uiState.fields.isPasswordTouched) uiState.formState.passwordErrorMessage else null
        )

        CustomTextField( // Confirmar contraseña
            value = uiState.fields.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = "Confirmar contraseña",
            keyboardType = KeyboardType.Password,
            visualTransformation = if (uiState.formState.isConfirmPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            enabled = !uiState.formState.isLoading,
            trailingIcon = {
                IconButton(onClick = onToggleConfirmPasswordVisibility) {
                    Icon(
                        painter = painterResource(
                            id = if (uiState.formState.isConfirmPasswordVisible)
                                R.drawable.ic_visibility_off
                            else
                                R.drawable.ic_visibility
                        ),
                        contentDescription = if (uiState.formState.isConfirmPasswordVisible)
                            "Ocultar contraseña"
                        else
                            "Mostrar contraseña"
                    )
                }
            },
            isError = uiState.fields.isConfirmPasswordTouched && uiState.formState.confirmPasswordErrorMessage != null,
            errorMessage = if (uiState.fields.isConfirmPasswordTouched) uiState.formState.confirmPasswordErrorMessage else null
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(
            enabled = uiState.formState.isFormValid && !uiState.formState.isLoading,
            isLoading = uiState.formState.isLoading,
            onClick = onSignUpClick,
            buttonText = "Registrarme",
            loadingText = "Cargando ..."
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