package br.com.fiap.irrigaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.fiap.irrigaapp.data.remote.api.TokenProvider
import br.com.fiap.irrigaapp.ui.components.login.EmailInput
import br.com.fiap.irrigaapp.ui.components.login.LoginButton
import br.com.fiap.irrigaapp.ui.components.login.PasswordInput
import br.com.fiap.irrigaapp.ui.components.login.RegisterLink
import br.com.fiap.irrigaapp.viewmodel.LoginViewModel
import br.com.fiap.irrigaapp.viewmodel.LoginViewModelFactory

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val context = LocalContext.current
    val tokenProvider = remember { TokenProvider(context) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.observeAsState(false)
    val isError by viewModel.isError.observeAsState(false)
    val loginSuccess by viewModel.loginSuccess.observeAsState()

    loginSuccess?.let { token ->
        tokenProvider.saveToken(token)
        onLoginSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(16.dp))

        EmailInput(email = email, onEmailChange = { email = it })
        Spacer(modifier = Modifier.height(8.dp))

        PasswordInput(password = password, onPasswordChange = { password = it })
        Spacer(modifier = Modifier.height(16.dp))

        LoginButton(
            onClick = { viewModel.login(email, password) },
            isLoading = isLoading,
            isError = isError
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegisterLink(onClick = onRegisterClick)
    }
}
