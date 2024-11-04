package br.com.fiap.irrigaapp.ui.components.previsaotempo

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalContext
import br.com.fiap.irrigaapp.data.remote.userLocation.getUserLocation

@Composable
fun MapLeaflet(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = object : WebViewClient() {

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    getUserLocation(context) { latitude, longitude ->
                        view?.evaluateJavascript("javascript:setUserLocation($latitude, $longitude);", null)
                    }
                }
            }

            settings.javaScriptEnabled = true


            loadUrl("file:///android_asset/map.html")
        }
    })
}
