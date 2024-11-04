package br.com.fiap.irrigaapp.data.remote.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class TokenProvider(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // Função para salvar o token
    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
        Log.d("TokenProvider", "Token salvo com sucesso: $token")
    }

    // Função para recuperar o token
    fun getToken(): String? {
        val token = prefs.getString("auth_token", null)
        Log.d("TokenProvider", "Token recuperado: $token")
        return token
    }
}
