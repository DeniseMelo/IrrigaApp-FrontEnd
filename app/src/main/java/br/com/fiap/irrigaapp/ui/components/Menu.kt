package br.com.fiap.irrigaapp.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController

import br.com.fiap.irrigaapp.R
import kotlinx.coroutines.launch


@Composable
fun Menu(navController: NavHostController) {

    var menuExpandido by remember { mutableStateOf(false) }  // Controla se o menu está expandido ou retraído
    val larguraMenu by animateDpAsState(
        targetValue = if (menuExpandido) 0.75f * 360.dp else 60.dp,
        label = ""
    )

    val escopo = rememberCoroutineScope()

    // Box para o menu lateral retrátil
    Box(modifier = Modifier.fillMaxSize()) {
        // Menu lateral retrátil sobreposto ao conteúdo
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(larguraMenu)
                .background(
                    Color(0xFF6FAFDD),
                    shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
                )  // Menu com cantos arredondados à direita
                .clickable {
                    escopo.launch { menuExpandido = !menuExpandido }  // Expande ou retrai ao clicar
                }
                .padding(16.dp)
                .align(Alignment.CenterStart),  // Alinha o menu à esquerda sobrepondo o conteúdo
            verticalArrangement = Arrangement.Top
        ) {
            LogoENome(menuExpandido)
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.White.copy(alpha = 0.7f), thickness = 1.dp)

            // Sessão "Página Inicial"
            Text(
                text = if (menuExpandido) "Página Inicial" else "",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            IconAgua(menuExpandido, navController) {
                menuExpandido = false  // Retrai o menu ao clicar no ícone
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.White.copy(alpha = 0.7f), thickness = 1.dp)

            // Sessão "Canteiros" (futuras telas podem ser adicionadas aqui)
            Text(
                text = if (menuExpandido) "Canteiros" else "",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.White.copy(alpha = 0.7f), thickness = 1.dp)

            // Sessão "Meteorologia"
            Text(
                text = if (menuExpandido) "Meteorologia" else "",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            IconClima(menuExpandido, navController) {
                menuExpandido = false  // Retrai o menu ao clicar no ícone
            }

            Spacer(modifier = Modifier.height(16.dp))
            IconPrevisaoProximosDias(menuExpandido, navController) {
                menuExpandido = false  // Retrai o menu ao clicar no ícone
            }
            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = Color.White.copy(alpha = 0.7f), thickness = 1.dp)

            // Sessão "Configurações"
            Text(
                text = if (menuExpandido) "Configurações" else "",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            IconeWiFi(menuExpandido, navController) {
                menuExpandido = false  // Retrai o menu ao clicar no ícone
            }
        }
    }
}

@Composable
fun LogoENome(menuExpandido: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Sempre exibe o logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo"
        )

        // Exibe o nome apenas se o menu estiver expandido
        if (menuExpandido) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "IrrigaApp",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun IconAgua(
    menuExpandido: Boolean,
    navController: NavHostController,
    onIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("main")  // Navega para a tela "main" definida no AppNavigation
                onIconClick()  // Retrai o menu ao clicar no ícone
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.WaterDrop, contentDescription = "Home", tint = Color.White)
        if (menuExpandido) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sensores", color = Color.White)
        }
    }
}

@Composable
fun IconClima(
    menuExpandido: Boolean,
    navController: NavHostController,
    onIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("PrevisaoTempo")
                onIconClick()  // Retrai o menu ao clicar no ícone
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Cloud, contentDescription = "Previsão do Tempo", tint = Color.White)
        if (menuExpandido) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Previsão do Tempo", color = Color.White)
        }
    }
}

@Composable
fun IconPrevisaoProximosDias(
    menuExpandido: Boolean,
    navController: NavHostController,
    onIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("PrevisaoProximosDias")
                onIconClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.WbTwilight, contentDescription = "PrevisãoProximosDias", tint = Color.White)
        if (menuExpandido) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Previsão para os próximos dias", color = Color.White)
        }
    }
}

@Composable
fun IconeWiFi(
    menuExpandido: Boolean,
    navController: NavHostController,
    onIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("wifiScreen")  // Navega para a tela "wifiScreen"
                onIconClick()  // Retrai o menu ao clicar no ícone
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Wifi, contentDescription = "Wi-Fi", tint = Color.White)
        if (menuExpandido) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Conectar com Wi-Fi", color = Color.White)
        }
    }
}
