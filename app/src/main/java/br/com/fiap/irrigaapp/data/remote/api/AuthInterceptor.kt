package br.com.fiap.irrigaapp.data.remote.api


import okhttp3.Interceptor
import okhttp3.Response

import android.util.Log

class AuthInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Verifica e loga o token
        val token = tokenProvider.getToken()
        Log.d("AuthInterceptor", "Token obtido: $token") // Log para verificar o token

        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}

