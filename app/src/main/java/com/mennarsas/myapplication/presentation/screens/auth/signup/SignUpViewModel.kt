package com.mennarsas.myapplication.presentation.screens.auth.signup

import androidx.lifecycle.ViewModel
import com.mennarsas.myapplication.data.models.auth.signup.SignUpFormFields
import com.mennarsas.myapplication.data.models.auth.signup.SignUpRequest
import com.mennarsas.myapplication.data.models.auth.signup.SignUpUiState
import com.mennarsas.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel(
    private  val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    // Función general para actualizar los campos
    private fun updateField(
        updater: (SignUpFormFields) -> SignUpFormFields
    ) {
        _uiState.update { currentState ->
            val updatedFields = updater(currentState.fields)
            currentState.copy(
                fields = updatedFields,
                formState = currentState.formState.copy(
                    isFormValid = isValidForm(updatedFields),
                    errorMessage = null
                )
            )
        }
    }

    // Onchange de los campos
    fun onNamesChange(names: String) = updateField { it.copy(names = names, isNamesTouched = true) }
    fun onLastNamesChange(lastNames: String) = updateField { it.copy(lastNames = lastNames, isLastNamesTouched = true) }
    fun onEmailChange(email: String) = updateField { it.copy(email = email, isEmailTouched = true) }
    fun onPasswordChange(password: String) = updateField { it.copy(password = password, isPasswordTouched = true) }
    fun onConfirmPasswordChange(confirmPassword: String) =
       updateField { it.copy(confirmPassword = confirmPassword, isConfirmPasswordTouched = true) }



    // Visibilidad del campo contraseña
    fun onTogglePasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    isPasswordVisible = !currentState.formState.isPasswordVisible
                )
            )
        }
    }

    // Visibilidad del campo repetir contraseña
    fun onToggleConfirmPasswordVisibility() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    isConfirmPasswordVisible = !currentState.formState.isConfirmPasswordVisible
                )
            )
        }
    }

    // Click en registrarse
    suspend fun onSignUpClick(): Boolean {
        _uiState.update { currentState ->
            currentState.copy(formState = currentState.formState.copy(isLoading = true))
        }

        val fields = _uiState.value.fields

        // datos del usuario
        val request = SignUpRequest(
            names = fields.names,
            lastNames = fields.lastNames,
            email = fields.email,
            password = fields.password
        )

        return  when (val result = repository.registerUser(request)) {
            is AuthRepository.Result.Success -> {
                _uiState.update { currentState ->
                    currentState.copy(formState = currentState.formState.copy(successfullMessage = result.data.message))
                }
                resetForm()
                true
            }
            is AuthRepository.Result.Error -> {
                _uiState.update { currentState ->
                    currentState.copy(
                        formState = currentState.formState.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    )
                }
                false
            }
        }
    }

    // Limpiar los estados del formulario
    private fun resetForm() {
        _uiState.update { currentState ->
            currentState.copy(
                fields = SignUpFormFields(),
                formState = currentState.formState.copy(
                    isPasswordVisible = false,
                    isConfirmPasswordVisible = false,
                    isLoading = false,
                    isFormValid = false,
                    errorMessage = null
                )
            )
        }
    }

    // Validar el formulario
    private fun isValidForm(fields: SignUpFormFields): Boolean {
        return with(fields) {
            val isEmailValid = email.isNotEmpty() && email.contains("@")
            val isPasswordValid = password.length >= 8
            val isConfirmPasswordValid = password == confirmPassword

            _uiState.update { currentState ->
                currentState.copy(
                    formState = currentState.formState.copy(
                        emailErrorMessage = if (isEmailTouched && !isEmailValid) "Correo electrónico no válido" else null,
                        passwordErrorMessage = if (isPasswordTouched && !isPasswordValid) "La contraseña debe tener mínimo 8 carácteres" else null,
                        confirmPasswordErrorMessage = if (isConfirmPasswordTouched && !isConfirmPasswordValid) "Las contraseñas no coinciden" else null
                    )
                )
            }

            names.isNotEmpty() &&
                    lastNames.isNotEmpty() &&
                    isEmailValid &&
                    isPasswordValid &&
                    isConfirmPasswordValid
        }
    }

    // Limpiar el mensaje
    fun dismissMessage() {
        _uiState.update { currentState ->
            currentState.copy(
                formState = currentState.formState.copy(
                    errorMessage = null,
                    successfullMessage = null
                )
            )
        }
    }
}