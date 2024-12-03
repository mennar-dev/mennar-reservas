package com.mennarsas.myapplication.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignUpUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isSignUpEnabled: Boolean = false,
    val errorMessage: String? = null
)

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    // Actualizar el estado del nombre
    fun onFirstNameChange(firstName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                firstName = firstName,
                isSignUpEnabled = isValidForm(
                    firstName,
                    currentState.lastName,
                    currentState.email,
                    currentState.password,
                    currentState.confirmPassword
                ),
                errorMessage = null
            )
        }
    }

    // Actualizar el estado del apellido
    fun onLastNameChange(lastName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                lastName = lastName,
                isSignUpEnabled = isValidForm(
                    currentState.firstName,
                    lastName,
                    currentState.email,
                    currentState.password,
                    currentState.confirmPassword
                ),
                errorMessage = null
            )
        }
    }

    // Actualizar el estado del correo electrónico
    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                isSignUpEnabled = isValidForm(
                    currentState.firstName,
                    currentState.lastName,
                    email,
                    currentState.password,
                    currentState.confirmPassword
                ),
                errorMessage = null
            )
        }
    }

    // Actualizar el estado de la contraseña
    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                isSignUpEnabled = isValidForm(
                    currentState.firstName,
                    currentState.lastName,
                    currentState.email,
                    password,
                    currentState.confirmPassword
                ),
                errorMessage = null
            )
        }
    }

    // Actualizar el estado de confirmación de contraseña
    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { currentState ->
            currentState.copy(
                confirmPassword = confirmPassword,
                isSignUpEnabled = isValidForm(
                    currentState.firstName,
                    currentState.lastName,
                    currentState.email,
                    currentState.password,
                    confirmPassword
                ),
                errorMessage = null
            )
        }
    }

    // Cambiar la visibilidad de la contraseña
    fun onTogglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
        }
    }

    // Cambiar la visibilidad de confirmar contraseña
    fun onToggleConfirmPasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(isConfirmPasswordVisible = !currentState.isConfirmPasswordVisible)
        }
    }

    // Actualizar el estado de error
    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // Registrarse: validar credenciales y actualizar estado
    suspend fun onSignUpClick(): Boolean {
        val firstName = _uiState.value.firstName
        val lastName = _uiState.value.lastName
        val email = _uiState.value.email
        val password = _uiState.value.password
        val confirmPassword = _uiState.value.confirmPassword

        _uiState.update { it.copy(isLoading = true) }
        delay(2500)

        return if (isValidForm(firstName, lastName, email, password, confirmPassword)) {
            // TODO: Implement actual signup logic (e.g., API call)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    firstName = "",
                    lastName = "",
                    email = "",
                    password = "",
                    confirmPassword = "",
                    isPasswordVisible = false,
                    isConfirmPasswordVisible = false
                )
            }
            true
        } else {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Signup failed. Please check your information."
                )
            }
            false
        }
    }

    // Validar los campos del formulario
    private fun isValidForm(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return firstName.isNotEmpty() &&
                lastName.isNotEmpty() &&
                email.isNotEmpty() &&
                email.contains("@") &&
                password.length >= 8 &&
                password == confirmPassword
    }
}