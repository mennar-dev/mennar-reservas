package com.mennarsas.myapplication.data.models.auth.signup

data class SignUpFormState(
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
    val errorMessage: String? = null,
    val successfullMessage: String? = null,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null,
    val confirmPasswordErrorMessage: String? = null
)
