package br.com.fiap.irrigaapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.irrigaapp.data.model.Usuario
import br.com.fiap.irrigaapp.data.remote.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "RegisterViewModel"
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun cadastrarUsuario(usuario: Usuario, onRegisterSuccess: () -> Unit) {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d(TAG, "Enviando solicitação de cadastro para: Nome=${usuario.nome}, Email=${usuario.email}")
                val response = RetrofitClient.getUsuarioApiService(context).cadastrarUsuario(usuario)

                if (response.isSuccessful) {
                    Log.d(TAG, "Usuário cadastrado com sucesso")
                    _errorMessage.value = null
                    onRegisterSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d(TAG, "Erro na resposta da API: Código=${response.code()}, Corpo do erro=$errorBody")

                    if (response.code() == 403) {
                        _errorMessage.value = "Usuário já cadastrado."
                    } else {
                        _errorMessage.value = "Erro ao cadastrar usuário. Tente novamente."
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro de conexão ao cadastrar usuário: ${e.message}")
                _errorMessage.value = "Erro de conexão. Tente novamente."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
