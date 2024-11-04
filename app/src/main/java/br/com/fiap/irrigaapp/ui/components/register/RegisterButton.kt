package br.com.fiap.irrigaapp.ui.components.register


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RegisterButton(
    isLoading: Boolean,
    nome: String,
    email: String,
    senha: String,
    onRegisterClick: () -> Unit
) {
    // Condição para habilitar o botão: todos os campos preenchidos corretamente
    val isFormValid = nome.length >= 4 &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            senha.length >= 6

    Button(
        onClick = onRegisterClick,
        enabled = !isLoading && isFormValid,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6FAFDD))

    ) {
        Text(text = if (isLoading) "Cadastrando..." else "Cadastrar")
    }
}

