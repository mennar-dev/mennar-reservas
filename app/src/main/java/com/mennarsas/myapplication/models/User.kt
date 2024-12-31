package com.mennarsas.myapplication.models

data class SignUpResponse(
    val message: String,
    val data: UserData
)

data class UserData(
    val fullname: String,
    val email: String,
    val _id: String,
    val createdAt: String,
    val updatedAt: String
)

data class ErrorResponse(
    val message: String
)