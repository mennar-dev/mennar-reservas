package com.mennarsas.myapplication.data.network

import com.mennarsas.myapplication.data.models.auth.signup.SignUpResponse
import com.mennarsas.myapplication.data.models.auth.signup.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    suspend fun registerUser(@Body request: SignUpRequest): Response<SignUpResponse>
}