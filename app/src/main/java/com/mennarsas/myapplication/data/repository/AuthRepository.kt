package com.mennarsas.myapplication.data.repository

import com.google.gson.Gson
import com.mennarsas.myapplication.data.models.auth.signup.ErrorResponse
import com.mennarsas.myapplication.data.models.auth.signup.SignUpRequest
import com.mennarsas.myapplication.data.models.auth.signup.SignUpResponse
import com.mennarsas.myapplication.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthRepository {
    private  val apiService = RetrofitClient.apiService

    sealed class Result<out T> {
        data class Success<T>(val data: T) : Result<T>()
        data class Error(val message: String) : Result<Nothing>()
    }

    // Registrar un nuevo usuario
    suspend fun registerUser(request: SignUpRequest): Result<SignUpResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.registerUser(request)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.Success(it)
                    } ?: Result.Error("El cuerpo de la respuesta está vacido.")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    Result.Error(errorResponse.message)
                }
            } catch (e: HttpException) {
                Result.Error("Error de red: ${e.message}")
            } catch (e: Exception) {
                Result.Error("Error: ${e.message}")
            }
        }
    }
}