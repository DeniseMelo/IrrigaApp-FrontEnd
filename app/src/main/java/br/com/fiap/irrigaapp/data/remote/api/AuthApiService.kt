package br.com.fiap.irrigaapp.data.remote.api

import br.com.fiap.irrigaapp.data.model.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

typealias LoginResponse = String

interface AuthApiService {
    @POST("/usuarios/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}

