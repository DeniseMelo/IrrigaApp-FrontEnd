package br.com.fiap.irrigaapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FundoOpaco(onClick: () -> Unit) {
    // Aplica a opacidade apenas no conteúdo da tela principal
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))  // Fundo opaco com 60% de opacidade
            .clickable(onClick = onClick)  // Fecha o menu ao clicar no fundo
    )
}
