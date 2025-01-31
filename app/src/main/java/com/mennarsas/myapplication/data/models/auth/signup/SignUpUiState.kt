package com.mennarsas.myapplication.data.models.auth.signup

data class SignUpUiState(
    val fields: SignUpFormFields = SignUpFormFields(),
    val formState: SignUpFormState = SignUpFormState()
)
