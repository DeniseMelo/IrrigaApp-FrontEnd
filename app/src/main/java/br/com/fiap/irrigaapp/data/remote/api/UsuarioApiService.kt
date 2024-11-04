package br.com.fiap.irrigaapp.data.remote.api

import br.com.fiap.irrigaapp.data.model.LoginRequest
import br.com.fiap.irrigaapp.data.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface UsuarioApiService {

    @POST("usuarios/cadastrar")
    suspend fun cadastrarUsuario(@Body usuario: Usuario): Response<Usuario>

}