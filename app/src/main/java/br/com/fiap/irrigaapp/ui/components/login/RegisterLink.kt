package br.com.fiap.irrigaapp.ui.components.login

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun RegisterLink(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            "Não tem uma conta? Cadastre-se",
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        )
    }
}
