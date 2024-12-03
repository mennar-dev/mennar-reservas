package com.mennarsas.myapplication.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginEnabled: Boolean = false,
    val errorMessage: String? = null
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Actualizar el estado del campo de correo electrónico
    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                isLoginEnabled = isValidForm(email, currentState.password),
                errorMessage = null
            )
        }
    }

    // Actualizar el estado del campo de contraseña
    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                isLoginEnabled = isValidForm(currentState.email, password),
                errorMessage = null
            )
        }
    }

    // Actualizar el estado de visibilidad de la contraseña
    fun onTogglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
        }
    }

    // Actualizar el estado de error
    fun dismissError() { _uiState.update { it.copy(errorMessage = null) } }

    // Inicio de sesión: validar credenciales y actualizar el estado
    suspend fun onLoginClick(): Boolean {
        val email = _uiState.value.email
        val password = _uiState.value.password

        _uiState.update { it.copy(isLoading = true) }

        delay(2500)
        return if (email == "admin@test.com" && password == "12345") {
            _uiState.update { it.copy(isLoading = false) }
            _uiState.update { it.copy(email = "", password = "", isPasswordVisible = false) }
            true
        } else {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Credenciales incorrectas. Por favor, verifica tu email y contraseña."
                )
            }
            false
        }
    }

    // Validar el contenido de los campos del formulario
    private fun isValidForm(email: String, password: String): Boolean {
        return  email.isNotEmpty() && email.contains("@") && password.length >= 5
    }
}