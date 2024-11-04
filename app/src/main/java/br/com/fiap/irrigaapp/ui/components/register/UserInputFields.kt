package br.com.fiap.irrigaapp.ui.components.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun UserInputFields(
    nome: MutableState<String>,
    email: MutableState<String>,
    senha: MutableState<String>
) {
    var nomeError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var senhaError by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Campo Nome com Placeholder
        TextField(
            value = nome.value,
            onValueChange = {
                nome.value = it
                nomeError = if (it.isBlank()) "Nome não pode estar em branco"
                else if (it.length < 4) "Nome precisa ter pelo menos 4 caracteres"
                else null
            },
            label = { Text("Nome") },
            placeholder = { Text("Digite seu nome completo") },
            modifier = Modifier.fillMaxWidth(),
            isError = nomeError != null
        )
        if (nomeError != null) {
            Text(
                text = nomeError ?: "",
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Email com Placeholder
        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError = if (it.isBlank()) "Email não pode estar em branco"
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Formato de email inválido"
                else null
            },
            label = { Text("Email") },
            placeholder = { Text("exemplo@dominio.com") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null
        )
        if (emailError != null) {
            Text(
                text = emailError ?: "",
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Senha com Placeholder
        TextField(
            value = senha.value,
            onValueChange = {
                senha.value = it
                senhaError = if (it.isBlank()) "Senha não pode estar em branco"
                else if (it.length < 6) "Senha precisa ter pelo menos 6 caracteres"
                else null
            },
            label = { Text("Senha") },
            placeholder = { Text("Mínimo de 6 caracteres") }, // Placeholder indicando o requisito
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            isError = senhaError != null
        )
        if (senhaError != null) {
            Text(
                text = senhaError ?: "",
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
