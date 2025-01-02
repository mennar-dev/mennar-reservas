package com.mennarsas.myapplication.data

import com.mennarsas.myapplication.models.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse
}

data class SignUpRequest(
    val fullname: String,
    val email: String,
    val password: String
)