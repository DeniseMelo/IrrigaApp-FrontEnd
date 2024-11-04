package br.com.fiap.irrigaapp.data.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size


data class Usuario(
    @field:NotEmpty(message = "Nome não pode estar vazio.")
    val nome: String,

    @field:NotEmpty(message = "Email não pode estar vazio.")
    @field:Email(message = "Email deve ser válido.")
    val email: String,

    @field:Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres.")
    val senha: String,

    val role: String = "user" // "user" como padrão
)

