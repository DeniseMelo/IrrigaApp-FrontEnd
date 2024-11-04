package br.com.fiap.irrigaapp.ui.components.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun PasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Senha") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation()
    )
}
