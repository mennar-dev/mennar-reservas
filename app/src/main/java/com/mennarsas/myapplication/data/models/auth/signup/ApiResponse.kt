package com.mennarsas.myapplication.data.models.auth.signup

data class SignUpResponse(
    val message: String,
    val user: User? = null
)

data class User(
    val id: Int,
    val email: String,
    val names: String,
    val lastNames: String
)

data class ErrorResponse(
    val message: String,
    val error: String,
    val statusCode: Int
)
