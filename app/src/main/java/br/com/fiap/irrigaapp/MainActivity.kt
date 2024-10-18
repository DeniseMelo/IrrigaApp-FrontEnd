package br.com.fiap.irrigaapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.fiap.irrigaapp.data.repository.WeatherRepository
import br.com.fiap.irrigaapp.ui.navigation.AppNavigation
import com.mapbox.common.MapboxOptions
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : ComponentActivity() {

    private lateinit var weatherRepository: WeatherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar o token público do Mapbox aqui
        MapboxOptions.accessToken = "pk.eyJ1IjoiZGVuaXNlY21mIiwiYSI6ImNtMXozcXJtNjAyc2EybHBxNDF3cGV6cTcifQ.UyZB8Swr4DngD0GCB_Mt2g"

        setContent {
            weatherRepository = WeatherRepository(this)
            // Controle do estado para menu expandido
            var menuExpandido by remember { mutableStateOf(false) }

            // Navegação do app
            AppNavigation(
                menuExpandido = menuExpandido,
                onBackgroundClick = { menuExpandido = false } // Ação ao clicar no fundo quando o menu estiver expandido
            )
        }

    }
}
