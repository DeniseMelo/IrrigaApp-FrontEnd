package br.com.fiap.irrigaapp.ui.components.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun LoginLink(onLoginClick: () -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Já é cadastrado? Faça login",
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable { onLoginClick() }
    )
}
