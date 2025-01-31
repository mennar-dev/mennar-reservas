package com.mennarsas.myapplication.data.models.auth.signup

data class SignUpRequest(
    val names: String,
    val lastNames: String,
    val email: String,
    val password: String
)
