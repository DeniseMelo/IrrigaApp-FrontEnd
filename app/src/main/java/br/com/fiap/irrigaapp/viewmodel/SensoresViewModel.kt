package br.com.fiap.irrigaapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.irrigaapp.data.model.DadosSensor
import br.com.fiap.irrigaapp.data.remote.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SensoresViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _listaSensores = MutableStateFlow<List<DadosSensor>>(emptyList())
    val listaSensores: StateFlow<List<DadosSensor>> = _listaSensores

    // Função para carregar os sensores via API
    fun carregarDadosSensores() {
        viewModelScope.launch {
            try {
                // Passa o context ao chamar getApiService para obter os dados dos sensores
                val sensores = RetrofitClient.getSensorApiService(context).buscarDadosSensores()
                _listaSensores.value = sensores
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
