package br.com.fiap.irrigaapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.irrigaapp.comunication.wifi.connectToWiFi
import br.com.fiap.irrigaapp.comunication.wifi.scanWiFiNetworks
import br.com.fiap.irrigaapp.ui.components.FundoOpaco
import br.com.fiap.irrigaapp.ui.components.Menu

@Composable
fun WiFiScreen(
    navController: NavHostController,
    menuExpandido: Boolean,
    onBackgroundClick: () -> Unit
) {
    val context = LocalContext.current
    var wifiList by remember { mutableStateOf(listOf<String>()) }
    var selectedSSID by remember { mutableStateOf<String?>(null) }
    var password by remember { mutableStateOf("") }
    var showPasswordDialog by remember { mutableStateOf(false) }

    // Escanear redes Wi-Fi
    LaunchedEffect(Unit) {
        scanWiFiNetworks(context) { scannedNetworks ->
            wifiList = scannedNetworks
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F1F1))  // Fundo mais claro
    ) {
        Menu(navController = navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = if (!menuExpandido) 60.dp else 0.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Redes Wi-Fi Disponíveis",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                color = Color(0xFF6FAFDD),  // Cor azul para o título
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(wifiList) { ssid ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedSSID = ssid
                                showPasswordDialog = true  // Exibe o campo de senha ao selecionar a rede
                            },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = ssid,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Fundo opaco para fechar o menu ao clicar fora dele
        if (menuExpandido) {
            FundoOpaco(onClick = onBackgroundClick)
        }

        // Caixa de diálogo para inserir a senha da rede Wi-Fi
        if (showPasswordDialog) {
            AlertDialog(
                onDismissRequest = {
                    showPasswordDialog = false
                },
                title = {
                    Text(
                        text = "Conectar à $selectedSSID",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF6FAFDD)  // Cor do título da caixa de diálogo
                    )
                },
                text = {
                    Column {
                        Text("Insira a senha:")
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Senha") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF6FAFDD),
                                cursorColor = Color(0xFF6FAFDD),
                                focusedLabelColor = Color(0xFF6FAFDD),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (password.isNotBlank() && selectedSSID != null) {
                                connectToWiFi(context, selectedSSID!!, password)
                                showPasswordDialog = false
                                Toast.makeText(context, "Conectando à rede $selectedSSID", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Insira uma senha válida", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6FAFDD))  // Cor do botão de confirmar
                    ) {
                        Text("Conectar", color = Color.White)
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showPasswordDialog = false },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF6FAFDD))
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
