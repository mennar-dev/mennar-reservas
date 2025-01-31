package com.mennarsas.myapplication.data.models.auth.signup

data class SignUpFormFields(
    val names: String = "",
    val lastNames: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isNamesTouched: Boolean = false,
    val isLastNamesTouched: Boolean = false,
    val isEmailTouched: Boolean = false,
    val isPasswordTouched: Boolean = false,
    val isConfirmPasswordTouched: Boolean = false
)
