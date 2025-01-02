package com.mennarsas.myapplication.presentation.screens.auth.signup

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.mennarsas.myapplication.data.RetrofitClient
import com.mennarsas.myapplication.data.SignUpRequest
import com.mennarsas.myapplication.models.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

data class SignUpUiState(
    val fullName: String = "",
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
    fun onFullNameChange(fullName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                fullName = fullName,
                isSignUpEnabled = isValidForm(
                    fullName,
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
                    currentState.fullName,
                    // currentState.lastName,
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
                    currentState.fullName,
                    //  currentState.lastName,
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
                    currentState.fullName,
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
//    suspend fun onSignUpClick(): Boolean {
//        val fullName = _uiState.value.fullName
//        val email = _uiState.value.email
//        val password = _uiState.value.password
//        val confirmPassword = _uiState.value.confirmPassword
//
//        _uiState.update { it.copy(isLoading = true) }
//        delay(2500)
//
//        return if (isValidForm(fullName, email, password, confirmPassword)) {
//            // TODO: Implement actual signup logic (e.g., API call)
//            _uiState.update {
//                it.copy(
//                    isLoading = false,
//                    fullName = "",
//                  //  lastName = "",
//                    email = "",
//                    password = "",
//                    confirmPassword = "",
//                    isPasswordVisible = false,
//                    isConfirmPasswordVisible = false
//                )
//            }
//            true
//        } else {
//            _uiState.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = "Signup failed. Please check your information."
//                )
//            }
//            false
//        }
//    }

    suspend fun onSignUpClick(): Boolean {
        val fullName = _uiState.value.fullName
        val email = _uiState.value.email
        val password = _uiState.value.password
        val confirmPassword = _uiState.value.confirmPassword

        // Validar formulario
        if (!isValidForm(fullName, email, password, confirmPassword)) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Por favor, verifica la información"
                )
            }
            return false
        }

        // Iniciar estado de carga
        _uiState.update { it.copy(isLoading = true) }

        return try {
            // Realizar llamada de red
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.instance.signUp(
                    SignUpRequest(
                        fullname = fullName,
                        email = email,
                        password = confirmPassword
                    )
                )
            }

            // Manejar respuesta exitosa
            _uiState.update {
                it.copy(
                    isLoading = false,
                    fullName = "",
                    email = "",
                    password = "",
                    confirmPassword = ""
                )
            }
            true
        } catch (e: HttpException) {
            // Manejar errores de red
//            _uiState.update {
//                it.copy(
//                    isLoading = false,
//                    errorMessage = "Error de registro: ${e.message()}"
//                )
//            }
//            false

            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Intentar parsear el mensaje de error de la API
                val errorResponse = Gson().fromJson(
                    errorBody,
                    ErrorResponse::class.java
                )
                errorResponse.message
            } catch (parseEx: Exception) {
                // Si no se puede parsear, usar un mensaje genérico
                "Error de registro: ${e.message()}"
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = errorMessage
                )
            }
            false
        } catch (e: IOException) {
            // Manejar errores de conexión
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Error de conexión. Verifica tu red."
                )
            }
            false
        }
    }

    // Validar los campos del formulario
    private fun isValidForm(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return fullName.isNotEmpty() &&
                email.isNotEmpty() &&
                email.contains("@") &&
                password.length >= 8 &&
                password == confirmPassword
    }
}