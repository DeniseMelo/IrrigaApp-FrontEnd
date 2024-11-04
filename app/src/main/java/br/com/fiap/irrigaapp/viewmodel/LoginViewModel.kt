package br.com.fiap.irrigaapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fiap.irrigaapp.MyApplication
import br.com.fiap.irrigaapp.data.model.LoginRequest
import br.com.fiap.irrigaapp.data.remote.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "LoginViewModel"

    // Acessa o TokenProvider através do Application
    private val tokenProvider = (application as MyApplication).tokenProvider

    // Obtém authApiService usando RetrofitClient com applicationContext
    private val authApiService = RetrofitClient.getAuthApiService(application)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _loginSuccess = MutableLiveData<String?>()
    val loginSuccess: LiveData<String?> = _loginSuccess

    fun login(email: String, senha: String) {
        Log.d(TAG, "Iniciando login com email: $email e senha: $senha")
        _isLoading.value = true
        _isError.value = false

        val loginRequest = LoginRequest(email.trim(), senha)

        authApiService.login(loginRequest).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _isLoading.value = false
                Log.d(TAG, "Resposta da API recebida: Código=${response.code()}, Body=${response.body()}")

                if (response.isSuccessful) {
                    val token = response.body()  // O token agora é recebido diretamente como texto
                    Log.d(TAG, "Token recebido da API: $token")
                    if (token != null) {
                        Log.d("LoginViewModel", "Salvando token: $token")
                        tokenProvider.saveToken(token)
                        _loginSuccess.value = token
                    } else {
                        _isError.value = true
                    }
                } else {
                    _isError.value = true
                    Log.d(TAG, "Erro na resposta da API: Código=${response.code()}, Erro=${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.d(TAG, "Falha na requisição de login: ${t.message}")
            }
        })
    }
}
