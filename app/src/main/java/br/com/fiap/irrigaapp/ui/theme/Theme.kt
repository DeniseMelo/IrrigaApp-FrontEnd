package br.com.fiap.irrigaapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Configurando o esquema de cores para o app com um esquema de cores claro
private val AppColorScheme: ColorScheme = lightColorScheme(
    primary = Background,  // Cor de fundo azul para o menu e barra superior
    onPrimary = Branco,  // Cor branca para ícones e textos
)

@Composable
fun IrrigaAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,  // Aplicando o esquema de cores personalizado
        typography = Typography,
        content = content
    )
}
