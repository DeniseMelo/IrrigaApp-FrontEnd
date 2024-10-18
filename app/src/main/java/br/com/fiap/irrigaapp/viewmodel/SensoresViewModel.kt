package br.com.fiap.irrigaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.irrigaapp.data.model.DadosSensor
import br.com.fiap.irrigaapp.data.remote.sensores.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SensoresViewModel : ViewModel() {

    private val _listaSensores = MutableStateFlow<List<DadosSensor>>(emptyList())
    val listaSensores: StateFlow<List<DadosSensor>> = _listaSensores

    // Função para carregar os sensores via API
    fun carregarDadosSensores() {
        viewModelScope.launch {
            try {
                val sensores = RetrofitClient.apiService.buscarDadosSensores()
                _listaSensores.value = sensores
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}