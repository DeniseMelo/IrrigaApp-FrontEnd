package br.com.fiap.irrigaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.fiap.irrigaapp.data.model.Usuario
import br.com.fiap.irrigaapp.ui.components.register.RegisterButton
import br.com.fiap.irrigaapp.viewmodel.RegisterViewModel
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import br.com.fiap.irrigaapp.ui.components.register.UserInputFields


@Composable
fun RegisterScreen(
    navController: NavHostController,
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val nome = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }
    val isLoading by registerViewModel.isLoading.collectAsState()
    val errorMessage by registerViewModel.errorMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center // Centraliza o conteúdo na tela inteira
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp) // Espaçamento entre os elementos
                ) {
                    // Exibe o Snackbar alinhado ao topo
                    SnackbarHost(hostState = snackbarHostState) { data ->
                        Snackbar(
                            snackbarData = data,
                            containerColor = Color(0xFF6FAFDD),
                            contentColor = Color.White
                        )
                    }

                    // Campos de entrada para nome, email e senha
                    UserInputFields(
                        nome = nome,
                        email = email,
                        senha = senha
                    )

                    // Botão de registro
                    RegisterButton(
                        isLoading = isLoading,
                        nome = nome.value,
                        email = email.value,
                        senha = senha.value,
                        onRegisterClick = {
                            registerViewModel.cadastrarUsuario(
                                Usuario(nome = nome.value, email = email.value, senha = senha.value),
                                onRegisterSuccess = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Usuário cadastrado com sucesso!")
                                        delay(100) // Exibe o Snackbar rapidamente
                                        onLoginClick() // Redireciona para a tela de login
                                    }
                                }
                            )
                        }
                    )

                    // Link para tela de login
                    TextButton(onClick = onLoginClick) {
                        Text("Já tem uma conta? Faça login")
                    }

                    // Exibe a mensagem de erro, se houver
                    errorMessage?.let { message ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                }
            }
        }
    )
}
